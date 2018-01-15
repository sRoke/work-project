package net.kingsilk.qh.shop.service.service;

import com.google.gson.Gson;
import net.kingsilk.qh.shop.api.ErrStatus;
import net.kingsilk.qh.shop.api.ErrStatusException;
import net.kingsilk.qh.shop.domain.Sms;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 阿里大于短信服务
 *
 * @deprecated 挪到 qh-common 中
 */

@Service
public class AliSmsService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 发送短信(群发短信大于不会校验号码)
     *
     * @param sms      ;信息对象
     * @param mobile   手机号，群发短信需传入多个号码，以英文逗号分隔，一次调用最多传入200个号码
     * @param template 模板号
     * @param params   参数，可为空，需拼接成json格式
     * @param extent   扩展号，可为空
     * @return
     */
    public Sms sendSms(Sms sms, String mobile, String template, String params, String extent, String freeSign) {
        sms.setSendTime(new Date());
        //去除空格
        if (Pattern.matches(mobile, "^((1[3,5,8][0-9])|(14[5,7])|(17[0,6,7,8])|(19[7]))\\d{8}$")) {
            throw new ErrStatusException(ErrStatus.PARAMNUll, "手机号码非法");
        }

        String baseUrl = "https://eco.taobao.com/router/rest";
        String appkey = "23377230";
        String secret = "856241097193e5235f188bdfe49c8c41";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(new Date());

        Map map = new HashMap();
        map.put("sign_method", "md5");
        map.put("timestamp", date.toString());
        map.put("sms_type", "normal");
        map.put("partner_id", "top-sdk-java-20160607");
        map.put("v", "2.0");
        map.put("sms_free_sign_name", freeSign);
        map.put("method", "alibaba.aliqin.fc.sms.num.send");
        map.put("app_key", appkey);
        map.put("sms_template_code", template);             ////发货，SMS_13021551   // 下单,SMS_12981789////
        map.put("format", "json");
        map.put("rec_num", mobile);
        if (StringUtils.hasText(extent)) {
            map.put("extend", extent);
        }
        if (StringUtils.hasText(params)) {
            map.put("sms_param", params);
        }
        String sign = null;
        try {
            sign = signTop(map, secret, "md5");

        } catch (Exception e) {

        }
        map.put("sign", sign);

        URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .build()
                .encode()
                .toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> reqBody = new LinkedMultiValueMap<>();
        reqBody.setAll(map);
        HttpEntity<MultiValueMap<String, String>> reqEntity = new HttpEntity<>(reqBody, headers);

        final ResponseEntity<String> respEntity = restTemplate.exchange(
                uri,
                HttpMethod.POST,
                reqEntity,
                String.class
        );
        Gson gson = new Gson();
        Map json = gson.fromJson(respEntity.getBody(), Map.class);
        Sms.ALiSendStatus aliSendStatus = sms.getaLiSendStatus();
        if (json.get("alibaba_aliqin_fc_sms_num_send_response") != null) {
            aliSendStatus.setNo(new ObjectId().toString());
            aliSendStatus.setErrorCode("0");
            aliSendStatus.setSuccess(true);
            sms.setaLiSendStatus(aliSendStatus);
            return sms;
        } else {
            aliSendStatus.setNo(new ObjectId().toString());
            aliSendStatus.setModel(json.get("error_response").toString());
            aliSendStatus.setSuccess(false);
            sms.setaLiSendStatus(aliSendStatus);
            return sms;
        }
    }

    /**
     * 将map转为json字符串
     *
     * @param map
     * @return
     */
    public String convertMap2Json(Map<String, String> map) {
        if (map == null) {
            return "";
        }
        String[] keys = map.keySet().toArray(new String[0]);
        StringBuilder query = new StringBuilder();
        query.append('{');
        for (String key : keys) {
            String value = map.get(key);
            query.append("\"").append(key).append("\"").append(':').append("\"").append(value).append("\"").append(",");
        }
        String substring = query.substring(0, query.length() - 1);
        substring = substring + '}';
        return substring;
    }

    /**
     * 仿造淘宝阿里大于签名
     *
     * @param params
     * @param secret
     * @param signMethod 暂时先用md5
     * @return
     */
    public static String signTop(Map<String, String> params, String secret,
                                 String signMethod) throws IOException {
        signMethod = "md5";

        // 第一步：检查参数是否已经排序
        String[] keys = params.keySet().toArray(new String[0]);
        Arrays.sort(keys);

        // 第二步：把所有参数名和参数值串在一起
        StringBuilder query = new StringBuilder();
        if (signMethod.equals("md5")) {
            query.append(secret);
        }
        for (String key : keys) {
            String value = params.get(key);
            query.append(key).append(value);
        }
        // 第三步不需要
        // 第四步：使用MD5/HMAC加密
        byte[] bytes = null;
        if (signMethod.equals("md5")) {
            query.append(secret);
            bytes = encryptMD5(query.toString());
        } else {
            // bytes = encryptHMAC(query.toString(), secret);
        }
        // 第五步：把二进制转化为大写的十六进制
        String result = byte2hex(bytes);
        return result;
    }

    /**
     * 对字符串采用UTF-8编码后，用MD5进行摘要。
     */
    private static byte[] encryptMD5(String data) throws IOException {
        return encryptMD5(data.getBytes("UTF-8"));
    }

    /**
     * 对字节流进行MD5摘要。
     */
    private static byte[] encryptMD5(byte[] data) throws IOException {
        byte[] bytes = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            bytes = md.digest(data);
        } catch (GeneralSecurityException gse) {
            throw new IOException(gse.toString());
        }
        return bytes;
    }

    /**
     * 把字节流转换为十六进制表示方式。
     */
    private static String byte2hex(byte[] bytes) {
        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex.toUpperCase());
        }
        return sign.toString();
    }

    /**
     * 拼接url，把参数拼接到url后面
     *
     * @param baseUrl
     * @param params
     * @return
     */
    @Deprecated
    public static String buildRequestUrl(String baseUrl, Map<String, String> params) {
        String url = baseUrl + "?";
        for (Map.Entry<String, String> map : params.entrySet()) {
            // System.err.println("map:" + map.getKey() + ":" + map.getValue());
            url += map.getKey() + "=" + map.getValue() + "&";
        }
        return url.substring(0, url.length() - 1);
    }

    /**
     * 将map转为LinkedMultiValueMap
     *
     * @param map
     * @return
     */
    public static MultiValueMap convertMap2LinkedMultiValueMap(Map<String, String> map) {
        String[] keys = map.keySet().toArray(new String[0]);     //取得所有key的集合
        MultiValueMap parameterSet = new LinkedMultiValueMap();
        for (String key : keys) {
            parameterSet.set(key, map.get(key));
        }
        return parameterSet;
    }
}
