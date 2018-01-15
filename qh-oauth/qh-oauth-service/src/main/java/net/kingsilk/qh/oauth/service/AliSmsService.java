package net.kingsilk.qh.oauth.service;

import com.mongodb.*;
import com.mongodb.util.*;
import net.kingsilk.qh.oauth.domain.*;
import net.kingsilk.qh.oauth.repo.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.util.*;
import org.springframework.web.client.*;
import org.springframework.web.util.*;

import java.io.*;
import java.net.*;
import java.security.*;
import java.text.*;
import java.util.*;

@Service
public class AliSmsService {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private SmsRepo smsRepo;

    /**
     * 发送验证码，由这边生成验证码
     * @param phone
     */
    public void sendSms(String phone) {
        String code = getRandomString(6);

        long count = smsRepo.countByPhone(phone);
        Assert.isTrue(count < 5, "短信发送过于频繁");

        String resp = this.realSendSms(phone, code);
        if (resp == "SUCCESS") {
            Sms sms = new Sms();
            sms.setPhone(phone);
            sms.setVerifyCode(code);
            sms.setValid(true);
            sms.setCodeExpiredTime(new Date());
            smsRepo.save(sms);
        }
    }

    //////////////////////////////////////////////////////

    /**
     * 发送短信(群发短信大于不会校验号码)
     * @param mobile 手机号，群发短信需传入多个号码，以英文逗号分隔，一次调用最多传入200个号码
     * @param code  手机验证码
     */
    private String realSendSms(String mobile, String code) {
        Assert.notNull(mobile, "手机号码不能为空");
        Assert.notNull(code, "验证码不能为空");

        Assert.isTrue(mobile.matches("^1[3|4|5|7|8]\\d{9}$"), "手机号码不合法");

        /** 同一手机号，每天限制30条 */
//        String ss = new SimpleDateFormat("yyyy-MM-dd").format(new Date());   //将当前时间去掉时分秒
//        Date time = new SimpleDateFormat("yyyy-MM-dd").parse(ss);     //再格式化回date类型

        //////////开始发送
        String baseUrl = "https://eco.taobao.com/router/rest";
        String appkey = "23377230";
        String secret = "856241097193e5235f188bdfe49c8c41";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(new Date());

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.set("sign_method", "md5");
        map.set("timestamp", date.toString());
        map.set("sms_type", "normal");
        map.set("partner_id", "top-sdk-java-20160607");
        map.set("v", "2.0");
        map.set("sms_free_sign_name", "小皇叔");// ///
        map.set("method", "alibaba.aliqin.fc.sms.num.send");
        map.set("app_key", appkey);
        map.set("sms_template_code", "SMS_19640001");
        map.set("format", "json");
        map.set("rec_num", mobile);
        map.set("sms_param", "{\"code\":\"" + code + "\"}");

        try {
            String sign = this.signTop(map.toSingleValueMap(), secret, "md5");
            map.set("sign", sign);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .build()
                .encode()
                .toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.ALL));
        headers.setAccept(Arrays.asList(MediaType.ALL));

        HttpEntity<Map> reqEntity = new HttpEntity<>(map, headers);

        log.debug("=============" + reqEntity);


        ResponseEntity<String> respEntity = restTemplate.exchange(
                uri,
                HttpMethod.POST,
                reqEntity,
                String.class
        );

        String respStr = respEntity.getBody();
        //String respStr = restTemplate.postForObject(buildRequestUrl(baseUrl, map), null, String)      //拼接url版
//        String respStr = restTemplate.postForObject(baseUrl, this.convertMap2LinkedMultiValueMap(map), String)
        log.debug("发送结果==================>\n" + respStr);

        Assert.isTrue(!StringUtils.isEmpty(respStr), "发送短信失败");
        DBObject jsonObj = (DBObject) JSON.parse(respStr);
        if (jsonObj.get("alibaba_aliqin_fc_sms_num_send_response") != null) {
            return "SUCCESS";
        }

        Assert.isTrue(jsonObj.get("error_response") != null, "发送短信时出错");

        log.debug(String.valueOf(jsonObj.get("error_response")));
        return (String) ((DBObject) jsonObj.get("error_response")).get("msg");

        //群发短信不返回成功不返回code
        //{"error_response":{"code":15,"msg":"Remote service error","sub_code":"isv.AMOUNT_NOT_ENOUGH","sub_msg":"余额不足","request_id":"ishs4hyfsq33"}}
        //{"alibaba_aliqin_fc_sms_num_send_response":{"result":{"err_code":"0","model":"102574675838^1103278843184","success":true},"request_id":"zqb0zttxyjgz"}}
    }

    /**
     * 随机生成字符串
     * @param length 字符串长度
     * @return
     */
    private static String getRandomString(int length) {
        String base = "0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /////////////////////////////////////////////////////////////////以下是阿里大于短信签名，都是私有方法

    /**
     * 仿造淘宝阿里大于签名
     *
     * @param params
     * @param secret
     * @param signMethod
     *            暂时先用md5
     * @return
     * @throws IOException
     */
    private String signTop(
            Map<String, String> params,
            String secret,
            String signMethod
    ) throws IOException {
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
     * 将map转为LinkedMultiValueMap
     * @param map
     * @return
     */
    @Deprecated
    private static LinkedMultiValueMap convertMap2LinkedMultiValueMap(Map<String, String> map) {
        String[] keys = map.keySet().toArray(new String[0]);     //取得所有key的集合
        LinkedMultiValueMap parameterSet = new LinkedMultiValueMap();
        for (String key : keys) {
            parameterSet.set(key, map.get(key));
        }
        return parameterSet;
    }

    /**
     * 拼接url，把参数拼接到url后面
     * @param baseUrl
     * @param params
     * @return
     */
    @Deprecated
    private static String buildRequestUrl(String baseUrl, Map<String, String> params) {
        String url = baseUrl + "?";
        for (Map.Entry<String, String> map : params.entrySet()) {
            // System.err.println("map:" + map.getKey() + ":" + map.getValue());
            url += map.getKey() + "=" + map.getValue() + "&";
        }
        return url.substring(0, url.length() - 1);
    }
}
