package net.kingsilk.qh.shop.service.service;

import net.kingsilk.qh.shop.api.ErrStatus;
import net.kingsilk.qh.shop.api.ErrStatusException;
import net.kingsilk.qh.shop.domain.Shop;
import net.kingsilk.qh.shop.domain.Sms;
import net.kingsilk.qh.shop.repo.SmsRepo;
import net.kingsilk.qh.shop.service.QhShopProperties;
import net.kingsilk.tb4j.api.TbUtils;
import net.kingsilk.tb4j.api.alibaba.aliqin.fc.sms.num.send.SendApi;
import net.kingsilk.tb4j.api.alibaba.aliqin.fc.sms.num.send.SendReq;
import net.kingsilk.tb4j.api.alibaba.aliqin.fc.sms.num.send.SendResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.regex.Pattern;

@Service
public class SmsServive {

    @Autowired
    private SendApi sendApi;

    @Autowired
    private QhShopProperties props;

    @Autowired
    private SmsRepo smsRepo;

    @Autowired
    @Qualifier("mvcConversionService")
    private ConversionService conversionService;

    public SendResp send(SendReq req, Shop shop) {
        String appKey = props.getSms().getAppKey();
        String appSecret = props.getSms().getAppSecret();

        if (Pattern.matches(shop.getPhone(), "^((1[3,5,8][0-9])|(14[5,7])|(17[0,6,7,8])|(19[7]))\\d{8}$")) {
            throw new ErrStatusException(ErrStatus.PARAMNUll, "手机号码非法");
        }

        Date date = new Date();

        req.setAppKey(appKey);
        req.setTargetAppKey(null);
        req.setSignMethod("md5");
        req.setSign(null);
        req.setSession(null);
        req.setTimestamp(date);
        req.setPartnerId("top-sdk-java-20160607");

        req.setExtend(shop.getName());
        req.setSmsType("normal");
        req.setSmsFreeSignName("生意参谋");

        String base = "0123456789";
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 6; i++) {
            int number = new Random().nextInt(base.length());
            sb.append(base.charAt(number));
        }
        String code = sb.toString();

        Map<String, String> map = new LinkedHashMap<>();
        map.put("code", code);
        map.put("brandCom", shop.getName());

        req.setSmsParam(mapConvertJson(map));
        req.setRecNum(shop.getPhone());
        req.setSmsTemplateCode("SMS_96600028");

        Map<String, String> reqMap = convert(req);
        String sign = TbUtils.signMd5(reqMap, appSecret);
        req.setSign(sign);

        SendResp sendResp = sendApi.send(req);

        sendResp.check();

        Sms sms = new Sms();
        sms.setPhone(shop.getPhone());
        sms.setContent(code);
        sms.setSendTime(date);

        if (sendResp.getBizResp().getResult().getSuccess()) {
            sms.setContent(code);
            sms.setValid(true);
            smsRepo.save(sms);
        } else {
            sms.setContent(code);
            sms.setValid(false);
            smsRepo.save(sms);
        }

        return sendResp;
    }

    private String mapConvertJson(Map map) {
        StringBuilder query = new StringBuilder();
        query.append('{');
        Optional.ofNullable(map).ifPresent(map1 ->
                {
                    map1.keySet().forEach(it ->
                            query.append("\"").append(it).append("\"").append(':').append("\"").append(map1.get(it)).append("\"").append(",")
                    );
                }
        );

        String substring = query.substring(0, query.length() - 1);
        substring = substring + '}';
        return substring;
    }


    private Map convert(SendReq src) {
        Map<String, String> map = new LinkedHashMap<>();

//        TypeDescriptor reqTd = TypeDescriptor.valueOf(BaseReq.class);
//        TypeDescriptor strTd = TypeDescriptor.valueOf(String.class);
//        TypeDescriptor mapTd = TypeDescriptor.map(Map.class, strTd, strTd);
//
//        ConversionService conversionService = conversionServiceProvider.getObject();
//        Map<String, String> reqMap = (Map<String, String>) conversionService.convert(src, reqTd, mapTd);
//        map.putAll(reqMap);

        String extend = src.getExtend();
        if (StringUtils.hasText(extend)) {
            map.put("extend", extend);
        }


        String smsType = src.getSmsType();
        if (StringUtils.hasText(smsType)) {
            map.put("sms_type", smsType);
        }

        String smsFreeSignName = src.getSmsFreeSignName();
        if (StringUtils.hasText(smsFreeSignName)) {
            map.put("sms_free_sign_name", smsFreeSignName);
        }

        String smsParam = src.getSmsParam();
        if (StringUtils.hasText(smsParam)) {
            map.put("sms_param", smsParam);
        }


        String recNum = src.getRecNum();
        if (StringUtils.hasText(recNum)) {
            map.put("rec_num", recNum);
        }

        String smsTemplateCode = src.getSmsTemplateCode();
        if (StringUtils.hasText(smsTemplateCode)) {
            map.put("sms_template_code", smsTemplateCode);
        }
        return map;
    }

}
