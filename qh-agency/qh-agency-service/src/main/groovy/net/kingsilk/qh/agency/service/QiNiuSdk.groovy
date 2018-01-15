package net.kingsilk.qh.agency.service


import com.mongodb.util.JSON
import com.qiniu.http.Client
import com.qiniu.util.Auth
import com.qiniu.util.StringUtils
import com.qiniu.util.UrlSafeBase64
import net.kingsilk.qh.agency.core.MsgCodeEnum
import org.bson.BsonDocument
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.ByteArrayResource
import org.springframework.http.*
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.UnknownHttpStatusCodeException
import org.springframework.web.util.UriComponentsBuilder

import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import java.security.GeneralSecurityException

/**
 * 进行七牛上传图片处理，
 * 该类仿造七牛api进行处理
 *
 * @deprecated 挪到 qh-yun 工程中。
 */
@Deprecated
class QiNiuSdk {

    @Autowired
    private RestTemplate wwwRestTemplate

    private String accessKey;
    private String secretKey;
    private String defaultBucket;

    public QiNiuSdk() {

    }

    public QiNiuSdk(String accessKey, String secretKey, String defaultBucket) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.defaultBucket = defaultBucket;
    }
    // 上传文件
    /**
     * 进行上传文件，传入图片和图片的名称
     * @param bytes
     * @param key
     * @return
     */
    public Map put(byte[] bytes, String key) {
        String token = uploadToken();
        String url = "http://upload.qiniu.com"

        MultiValueMap reqMsg = new LinkedMultiValueMap()
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        reqMsg.token = token;
        reqMsg.file = new ByteArrayResource(bytes);
        reqMsg.key = key;
        HttpEntity<MultiValueMap> reqEntity = new HttpEntity<MultiValueMap>(reqMsg, headers);
        // 组装url的参数
        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .build()
                .encode("UTF-8")
                .toUri()
        ResponseEntity respEntity = null;
        try {
            // 发送数据
            respEntity = wwwRestTemplate.exchange(uri, HttpMethod.POST, reqEntity, String);
        } catch (RestClientException e) {
            // 异常处理
            if (e instanceof UnknownHttpStatusCodeException) {
                // 未找到该文件
                return [
                        error: e.getMessage()
                ]
            }
//            log.error(e.message, e)
            // 未知的异常s
            return [
                    error: 0
            ]
        }

        def respStr = respEntity.getBody();
        def jsonObj = JSON.parse(respStr);
        String code = jsonObj.getAt("code");
        if (!code) {
            // 成功上传返回信息
            return [
                    status: MsgCodeEnum.SUCCESS.name(),
                    hash  : jsonObj.getAt("hash"),
                    key   : jsonObj.getAt("key"),
            ];
        }
        // 上传失败返回code 和error信息
        return [
                code : jsonObj.getAt("code"),
                error: jsonObj.getAt("error"),
        ];
    }

    /**
     * 查看七牛云上是否有对应的文件
     * @param key
     * @return
     */
    public Map stat(String key) {
        String encodedEntryURI = entry(defaultBucket, key);
        // 验证url
        String url = "http://rs.qiniu.com/stat/" + encodedEntryURI
//        String url = "http://wap-dev.kingsilk.xyz/qh/mall/local/16500/api/testSwx/testHeder"
        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .build()
                .encode("UTF-8")
                .toUri();
        // 生成签名串
        String authorization = "QBox " + signRequest("/stat/" + encodedEntryURI, null, null);
        // 生成url参数
        MultiValueMap reqMsg = new LinkedMultiValueMap()
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authorization);

        HttpEntity<MultiValueMap> reqEntity = new HttpEntity<MultiValueMap>(reqMsg, headers);
        ResponseEntity respEntity = null;
        try {
            respEntity = wwwRestTemplate.exchange(uri, HttpMethod.POST, reqEntity, String);
        } catch (RestClientException e) {
            if (e instanceof UnknownHttpStatusCodeException) {
                // 未找到该文件
                return [
                        error: e.getMessage()
                ]
            }
//            log.error(e.message, e)
            // 未知的异常s
            return [
                    error: 0
            ]
        }

        def respStr = respEntity.getBody();

        def jsonObj = JSON.parse(respStr);

        return [
                status  : MsgCodeEnum.SUCCESS.name(),
                fsize   : jsonObj.getAt("fsize"),
                putTime : jsonObj.getAt("putTime"),
                mimeType: jsonObj.getAt("mimeType"),
                hash    : jsonObj.getAt("hash")
        ];
    }
    /**
     * 查看七牛云上是否有对应的文件
     * @param key
     * @return
     */
    def Map fetch(String urls, String name) {
        String encodedURL = entry(urls, null);
        String encodedEntryURI = entry(defaultBucket, name);
        String url = "http://iovip.qbox.me/fetch/" + encodedURL + "/to/" + encodedEntryURI;
        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .build()
                .encode("UTF-8")
                .toUri();
        // 生成签名串
        String authorization = "QBox " + signRequest("/fetch/" + encodedURL + "/to/" + encodedEntryURI, null, null);

        MultiValueMap reqMsg = new LinkedMultiValueMap()
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authorization);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap> reqEntity = new HttpEntity<MultiValueMap>(reqMsg, headers);
        ResponseEntity respEntity = null;
        try {
            respEntity = wwwRestTemplate.exchange(uri, HttpMethod.POST, reqEntity, String);
        } catch (RestClientException e) {
            if (e instanceof UnknownHttpStatusCodeException) {
                // 未找到该文件
                return [
                        error: e.getMessage()
                ]
            }
            return [
                    error: 0
            ]
        }
        def respStr = respEntity.getBody();
        def jsonObj = JSON.parse(respStr);
        return [
                error   : jsonObj.getAt("error"),
                fsize   : jsonObj.getAt("fsize"),
                hash    : jsonObj.getAt("hash"),
                mimeType: jsonObj.getAt("mimeType"),
                key     : jsonObj.getAt("key")
        ];
    }

    public String uploadToken() {
//        UploadManager uploadManager = new UploadManager()
        Auth auth = Auth.create(accessKey, secretKey);
        return auth.uploadToken(defaultBucket)
    }
    /**
     * EncodedEntryURI格式
     * 对url进行编码
     * @param bucket
     * @param key
     * @return urlsafe_base64_encode ( Bucket : Key )
     */
    private String entry(String bucket, String key) {
        String en = bucket;
        if (key != null) {
            en = bucket + ":" + key;
        }
        return UrlSafeBase64.encodeToString(en);
    }
    /**
     * 生成HTTP请求签名字符串
     *
     * @param urlString
     * @param body
     * @param contentType
     * @return
     */
    private String signRequest(String urlString, byte[] body, String contentType) {
        URI uri = URI.create(urlString);
        String path = uri.getRawPath();
        String query = uri.getRawQuery();

        // 生成本机的密钥
        Mac mac = createMac();
        // 更新数据
        mac.update(StringUtils.utf8Bytes(path));
        // 组装数据
        if (query != null && query.length() != 0) {
            mac.update((byte) ('?').getBytes()[0]);
            mac.update(StringUtils.utf8Bytes(query));
        }
        mac.update((byte) '\n'.getBytes()[0]);
        if (body != null && body.length > 0 && !StringUtils.isNullOrEmpty(contentType)) {
            if (contentType == (Client.FormMime) || contentType == (Client.JsonMime)) {
                mac.update(body);
            }
        }
        // 最后的结果要进行编码
        String digest = UrlSafeBase64.encodeToString(mac.doFinal());

        return this.accessKey + ":" + digest;
    }
    /**
     * 生成本机的密钥地址
     * @return
     */
    private Mac createMac() {

        // 将key进行生成，同时保证存在数据
        if (StringUtils.isNullOrEmpty(accessKey) || StringUtils.isNullOrEmpty(secretKey)) {
            throw new IllegalArgumentException("empty key");
        }
        byte[] sk = StringUtils.utf8Bytes(secretKey);
        // 生成数据
        SecretKeySpec secretKeySpec = new SecretKeySpec(sk, "HmacSHA1");
        Mac mac;
        try {
            // 保证数据
            mac = Mac.getInstance("HmacSHA1");
            mac.init(secretKeySpec);
        } catch (GeneralSecurityException e) {
            throw new IllegalArgumentException(e);
        }
        return mac;
    }
}
