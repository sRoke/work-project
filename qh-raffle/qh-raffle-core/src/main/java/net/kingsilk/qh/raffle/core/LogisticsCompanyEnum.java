package net.kingsilk.qh.raffle.core;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zcw on 3/13/17.
 * 物流类型
 */
public enum LogisticsCompanyEnum {

    //// PS：以下名字都不可更换
    //// 参考：https://view.officeapps.live.com/op/view.aspx?src=https://www.kuaidi100.com/download/api_kuaidi100_com(20140729).doc

    ems("ems", "EMS"),
    guotongkuaidi("guotongkuaidi", "国通快递"),
    jiajiwuliu("jiajiwuliu", "佳吉快运"),
    rufengda("rufengda", "如风达"),
    shunfeng("shunfeng", "顺丰速递"),
    tiantian("tiantian", "天天快递"),
    ups("ups", "UPS"),
    yuantong("yuantong", "圆通速递"),
    yunda("yunda", "韵达快递"),
    huitongkuaidi("huitongkuaidi", "百世汇通"),
    zhaijisong("zhaijisong", "宅急送"),
    zhongtong("zhongtong", "中通速递"),
    debangwuliu("debangwuliu", "德邦物流"),
    quanfengkuaidi("quanfengkuaidi", "全峰快递"),
    shentong("shentong", "申通快递");


    LogisticsCompanyEnum(String code, String desp) {
        this.code = code;
        this.desp = desp;
    }

    public String getCode() {
        return code;
    }

    public String getDesp() {
        return desp;
    }


    private final String code;
    private final String desp;

    public static Map<String,String> getMap(){
        Map<String,String> enumMap = new HashMap<String,String>();
        for(LogisticsCompanyEnum company : LogisticsCompanyEnum.values()){
            enumMap.put(company.code,company.desp);
        }
        return enumMap;
    }
}
