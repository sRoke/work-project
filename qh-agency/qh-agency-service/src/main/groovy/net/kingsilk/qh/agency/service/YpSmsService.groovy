package net.kingsilk.qh.agency.service

import com.mongodb.util.JSON
import groovy.transform.CompileStatic
import net.kingsilk.qh.agency.domain.Sms
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.util.Assert
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate

import java.text.SimpleDateFormat

/**
 * 云片网短信服务
 */
@Service
@CompileStatic
@Deprecated
class YpSmsService {

    @Autowired
    RestTemplate wwwRestTemplate

    private String apikey = "90a6fa6fcc87b96f4218dfbaaf5bd755"
    private String sendSmsUri = "http://yunpian.com/v1/sms/send.json"
    private String tplSendSms = "http://yunpian.com/v1/sms/tpl_send.json"
    private String getRemainTotal = "http://yunpian.com/v1/user/get.json"
    private String pullStatus = "http://sms.yunpian.com/v1/sms/pull_status.json"
    private String callbackUrl = "todo"
    private int smsMin = 1
    private String company = "钱皇网络"

    /**
     * 发送短信
     * @param reqMsg
     * @return
     */
    public sendSms(Sms sms, String mobile, String text) {
        Assert.notNull(mobile, "手机号码不能为空");
        Assert.isTrue(mobile ==~ /(^1[3|4|5|7|8]\d{9}$)|0\d{2}-\d{8}|0\d{3}-\d{7}/, "手机号码不合法");

        /**同一手机号，每天限制30条*/
        String ss = new SimpleDateFormat('yyyy-MM-dd').format(new Date())   //将当前时间去掉时分秒
        def time = new SimpleDateFormat('yyyy-MM-dd').parse(ss)                 //再格式化回date类型
        if (countSms(time, 25, mobile)) {
            Assert.isTrue(false, '请求短信过多，请明天再试')
        }
        /**限制结束***/

        MultiValueMap parameterSet = new LinkedMultiValueMap()
        parameterSet.apikey = apikey
        parameterSet.mobile = mobile
        parameterSet.text = text
        //parameterSet.uid = ''
        //parameterSet.callback_url = Holders.config.qh.yunpian.uri.callbackUrl

        String respStr = wwwRestTemplate.postForObject(sendSmsUri, parameterSet, String)
        if (respStr) {
            def jsonObj = JSON.parse(respStr)
            switch (jsonObj.getAt("code") as Integer) {
                case 0:
                    //成功向运营商请求
                    if (!sms) {
                        println(respStr)
                        return;
                    }
                    Sms.YpSendStatus resp = sms.getYpSendStatus()
                    if (jsonObj.getAt("code") == 0) {
                        resp.no = new ObjectId().toString()
                        resp.count = Integer.parseInt(jsonObj.getAt("result.count").toString())
                        resp.fee = Integer.parseInt(jsonObj.getAt("result.fee").toString())
                        resp.sid = Long.parseLong(jsonObj.getAt("result.sid").toString())
                        //resp.save()
                        return resp;
                    }
                    break;
                case 3:
                    //log.debug('云片网账户余额不足')
                    break;
                case 5:
                    Assert.isTrue(false, "短信模板匹配失败")
                    break;
                case 8:
                    //30秒内重复提交相同的内容
                    Assert.isTrue(false, '短信请求过于频繁')
                    break;
                case 9:
                    //5分钟内重复提交相同的内容超过3次
                    Assert.isTrue(false, '短信请求过于频繁')
                    break;
                case 10:
                    Assert.isTrue(false, "手机号在黑名单列表中")
                    break;
                case 17:
                    Assert.isTrue(false, "请求短信过多，请明天再试")
                    break;
                case 22:
                    //同一个手机号验证码类内容，每小时最多能获取3条
                    Assert.isTrue(false, "短信发送过于频繁，请稍后再试")
                    break
                case 33:
                    Assert.isTrue(false, '同一个手机号同一验证码模板每30秒只能发送一条。')
                    break;
            }
            Assert.isTrue(jsonObj.getAt("code") == 0, "信息发送失败")
        }
    }

    /**
     * 主动获取短信状态报告
     */
    def pullSmsStatus() {
//        MultiValueMap parameterSet = new LinkedMultiValueMap()
//        parameterSet.apikey = apikey
//        parameterSet.page_size = '50'     //每页个数，最大100个，默认20个
//        String respStr = wwwRestTemplate.postForObject(pullStatus, parameterSet, String)
//        if (respStr) {
//            def jsonObj = JSON.parse(respStr)
//            for (def json : jsonObj.sms_status) {
////                println("++++++++++++++" + json)
//                def sms = Sms.createCriteria().get({
//                    eq('ypSendStatus.sid', Long.parseLong(json.sid + ''))
//                })
//                if (sms) {
//                    def yp = sms.ypSendStatus
//                    yp.uid = json.uid.toString() + ""
//                    yp.userReceiveTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(json.user_receive_time + '')
//                    yp.errorMsg = json.error_msg ? json.error_msg : null
//                    yp.mobile = json.mobile
//                    yp.reportStatus = json.report_status + ''
//                    sms.ypSendStatus = yp;
//                    sms.save()
//                } else {
//                    //log.debug('------------当前短信无记录')
//                }
//            }
//        }
    }

    /**
     * 统计到指定时间的短信数量，并对比值
     * @param time 时间
     * @param num 所需数量
     * @param phone 接收短信的手机号
     * @return
     */
    private boolean countSms(Date time, int num, String phone) {
//        def count = Sms.createCriteria().get {
//            projections {
//                rowCount()
//            }
//            gt("sendTime", time)
//            eq("phone", phone)
//        }
//        return count > num
        //todo
        return false;
    }

}
