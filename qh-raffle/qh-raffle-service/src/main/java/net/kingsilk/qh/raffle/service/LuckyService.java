package net.kingsilk.qh.raffle.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统枚举相关的服务
 */
@Service
public class LuckyService {

//    def springSecurityService

    static Map<String,List<HashMap<String,String>>> tabMap = new HashMap<String,List<HashMap<String,String>>>();
    /**
     *
     * @return
     */
    public static List<HashMap<String,String>> getTabList(String sessionId) {
        List<HashMap<String,String>> tabList = new  ArrayList<HashMap<String,String>>();
        tabList = tabMap.get(sessionId);
        return tabList;
    }

    public static List<HashMap<String,String>> setDefaultTabList(String sessionId){
        List<HashMap<String, String>> tabs = new ArrayList<>();
        HashMap<String, String> map0 = new HashMap<>();
        HashMap<String, String> map1 = new HashMap<>();
        HashMap<String, String> map2 = new HashMap<>();
        HashMap<String, String> map3 = new HashMap<>();
        HashMap<String, String> map4 = new HashMap<>();
        HashMap<String, String> map5 = new HashMap<>();
        HashMap<String, String> map6 = new HashMap<>();
        HashMap<String, String> map7 = new HashMap<>();
        map0.put("0","一等奖");
        map1.put("1","二等奖");
        map2.put("2","三等奖");
        map3.put("3","四等奖");
        map4.put("4","五等奖");
        map5.put("5","六等奖");
        map6.put("6","七等奖");
        map7.put("7","八等奖");
        if(!tabMap.containsKey(sessionId)){
            tabMap.put(sessionId,tabs);
        }
        return tabMap.get(sessionId);
    }

    public static List<HashMap<String,String>> setTabList(String sessionId,List<HashMap<String,String>> tabList){
        if(!tabMap.containsKey(sessionId)){
            tabMap.put(sessionId,tabList);
        }
        return tabList;
    }

    static void delTabListBySessionId(String sessionId){
        if(tabMap.containsKey(sessionId)){
            tabMap.remove(sessionId);
        }
    }
}
