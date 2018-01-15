package net.kingsilk.qh.agency.service

import com.mongodb.util.JSON
import groovy.transform.CompileStatic
import net.kingsilk.qh.agency.domain.Sms
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Service
import org.springframework.util.Assert
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate

import java.security.GeneralSecurityException
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * 阿里大于短信服务
 *
 * @deprecated 挪到 qh-common 中
 */
@Service
@CompileStatic
@Deprecated
class AliSmsService {

    @Autowired
    private RestTemplate wwwRestTemplate

    @Autowired
    private MongoTemplate mongoTemplate

    /**
     * 发送短信(群发短信大于不会校验号码)
     * @param sms ;信息对象
     * @param mobile 手机号，群发短信需传入多个号码，以英文逗号分隔，一次调用最多传入200个号码
     * @param template 模板号
     * @param params 参数，可为空，需拼接成json格式
     * @param extent 扩展号，可为空
     * @param boo 是否群发
     * @return
     */
    Sms sendSms(Sms sms, String mobile, String template, String params, String extent,String freeSign, boolean boo = true) {
        Assert.notNull(mobile, "手机号码不能为空");
        Assert.notNull(template, "模板号不能为空");
        sms.sendTime = new Date()
        //去除空格
        mobile = replaceBlank(mobile)
        template = replaceBlank(template)
        if (params) {
            params = replaceBlank(params)
        }
        if (boo) {
            Assert.isTrue(mobile ==~ /(1[3|4|5|7|8]\d{9},)*(1[3|4|5|7|8]\d{9})*/, "手机号码不合法");
        } else {
            Assert.isTrue(mobile ==~ /(^1[3|4|5|7|8]\d{9}$)/, "手机号码不合法");
        }

        /** 同一手机号，每天限制30条 */
        String ss = new SimpleDateFormat('yyyy-MM-dd').format(new Date())   //将当前时间去掉时分秒
        def time = new SimpleDateFormat('yyyy-MM-dd').parse(ss)                 //再格式化回date类型
        if (countSms(time, 25, mobile)) {
            Assert.isTrue(false, '请求短信过多，请明天再试')
        }
        /** 限制结束 ***/

        String baseUrl = "https://eco.taobao.com/router/rest";
        String appkey = "23377230";
        String secret = "856241097193e5235f188bdfe49c8c41";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(new Date());

        def map = [:]
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
        if (extent) {
            map.put("extend", extent);
        }
        if (params) {
            map.put("sms_param", params);
        }
        String sign = signTop(map, secret, "md5");
        map.put("sign", sign);
        //String respStr = wwwRestTemplate.postForObject(buildRequestUrl(baseUrl, map), null, String)      //拼接url版
        String respStr = wwwRestTemplate.postForObject(baseUrl, convertMap2LinkedMultiValueMap(map), String)
        if (respStr) {
//            if (!sms) {
//                // 仅用于测试
//                return respStr;
//            }
            def jsonObj = JSON.parse(respStr)
            Sms.ALiSendStatus aliSendStatus = sms.aLiSendStatus
            if (jsonObj.getAt("alibaba_aliqin_fc_sms_num_send_response") && !boo) {
                //发送成功
                aliSendStatus.setNo(new ObjectId().toString())
                aliSendStatus.setModel(jsonObj.getAt("alibaba_aliqin_fc_sms_num_send_response.result.model").toString())
                aliSendStatus.setErrorCode("0")
                aliSendStatus.success = true
                //aliSendStatus.msg = ''
                return sms
            } else if (jsonObj.getAt("error_response") && !boo) {
                //发送失败
                aliSendStatus.setNo(new ObjectId().toString())
                aliSendStatus.setModel(jsonObj.getAt("error_response.model").toString())
                aliSendStatus.setErrorCode(jsonObj.getAt("error_response.code").toString())
                aliSendStatus.setSuccess(false)
                aliSendStatus.setMsg(jsonObj.getAt("error_response.sub_msg").toString())
                return sms
            }
        }
        //群发短信不返回成功不返回code
        //{"error_response":{"code":15,"msg":"Remote service error","sub_code":"isv.AMOUNT_NOT_ENOUGH","sub_msg":"余额不足","request_id":"ishs4hyfsq33"}}
        //{"alibaba_aliqin_fc_sms_num_send_response":{"result":{"err_code":"0","model":"102574675838^1103278843184","success":true},"request_id":"zqb0zttxyjgz"}}
    }

    /**
     * 获取短信状态
     * @param date ;时间
     * @param mobile ;手机号
     * @param pageSize ;数量
     */
    public pullSmsStatus(Date date, String mobile, Integer pageSize) {

        String baseUrl = "https://eco.taobao.com/router/rest";
        String appkey = "23377230";
        String secret = "856241097193e5235f188bdfe49c8c41";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateNow = sdf.format(new Date());

        Assert.notNull(date, '查询时间必填')
        Assert.isTrue(mobile ==~ /(^1[3|4|5|7|8]\d{9}$)|0\d{2}-\d{8}|0\d{3}-\d{7}/, "手机号码不合法");

        sdf = new SimpleDateFormat("yyyyMMdd");
        String queryDate = sdf.format(date)

        if (pageSize <= 0) {
            pageSize = 20
        }

        def map = [:]
        map.put("sign_method", "md5");
        map.put("timestamp", dateNow.toString());
        map.put("page_size", pageSize as String);
        map.put("partner_id", "top-sdk-java-20160607");
        map.put("v", "2.0");
        map.put("query_date", queryDate.toString());
        map.put("method", "alibaba.aliqin.fc.sms.num.query");
        map.put("app_key", appkey);
        map.put("format", "json");
        map.put("current_page", "1");
        map.put("rec_num", mobile);

        String sign = this.signTop(map, secret, "md5");
        map.put("sign", sign);

        String respStr = wwwRestTemplate.postForObject(baseUrl, this.convertMap2LinkedMultiValueMap(map), String)
        //{"alibaba_aliqin_fc_sms_num_query_response":{"current_page":1,"page_size":10,"total_count":2,"total_page":1,"values":{"fc_partner_sms_detail_dto":[{"extend":"1267002","rec_num":"18270839486","result_code":"DELIVRD","sms_code":"SMS_12981789","sms_content":"【小皇叔】亲爱的您已付款成功，感谢您选择小皇叔，订单正在审核中，请关注下一条短信内容查收回寄地址。","sms_receiver_time":"2016-08-22 12:50:17","sms_send_time":"2016-08-22 12:50:00","sms_status":3},{"extend":"1267002","rec_num":"18270839486","result_code":"DELIVRD","sms_code":"SMS_12981789","sms_content":"【小皇叔】亲爱的您已付款成功，感谢您选择小皇叔，订单正在审核中，请关注下一条短信内容查收回寄地址。","sms_receiver_time":"2016-08-22 12:40:27","sms_send_time":"2016-08-22 12:40:23","sms_status":3}]},"request_id":"zqcl4cuqkglz"}}
    }

    /**
     * 将map转为json字符串
     * @param map
     * @return
     */
    public String convertMap2Json(Map map) {
        if (!map) {
            return "";
        }
        String[] keys = map?.keySet().toArray(new String[0]);
        StringBuilder query = new StringBuilder();
        query.append('{')
        for (String key : keys) {
            String value = map.get(key);
            query.append("\"").append(key).append("\"").append(':').append("\"").append(value).append("\"").append(",");
        }
        String substring = query.substring(0, query.size() - 1)
        substring = substring + '}'
        return substring
    }

    /**
     * 去除字符串中的空格，换行，tab
     * @param str
     * @return
     */
    public String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    /**
     * 统计到指定时间的短信数量，并对比值
     * @param time 时间
     * @param num 所需数量
     * @param phone 接收短信的手机号
     * @return
     */
    private static boolean countSms(Date time, Integer num, String phone) {
        // todo
//        def count = Sms.createCriteria().get {
//            projections {
//                rowCount()
//            }
//            gt("sendTime", time)
//            eq("phone", phone)
//        }
        return false
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
     * @param map
     * @return
     */
    public static MultiValueMap convertMap2LinkedMultiValueMap(Map<String, String> map) {
        String[] keys = map.keySet().toArray(new String[0]);     //取得所有key的集合
        MultiValueMap parameterSet = new LinkedMultiValueMap()
        for (String key : keys) {
            parameterSet.set(key, map.get(key))
        }
        return parameterSet
    }
}
