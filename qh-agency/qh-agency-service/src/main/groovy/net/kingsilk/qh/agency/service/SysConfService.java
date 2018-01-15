package net.kingsilk.qh.agency.service;

import net.kingsilk.qh.agency.core.SysConfTypeEnum;
import net.kingsilk.qh.agency.domain.SysConf;
import net.kingsilk.qh.agency.repo.SysConfRepo;
import net.kingsilk.qh.agency.security.BrandAppIdFilter;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统配置相关服务
 */
@Service
public class SysConfService {

    @Autowired
    private SysConfRepo sysConfRepo;

    /**
     * 更新配置
     *
     * @param key
     * @param value
     * @return true/false
     */
    public Boolean updata(String brandAppId, String key, Object value){
        if(value == null){
            value = "";
         }
        SysConf conf = sysConfRepo.findByKeyAndBrandAppId(key, brandAppId);
        if(conf == null){
            conf = new SysConf();
            conf.setBrandAppId(brandAppId);
            conf.setKey(key);
        }
        try{
            if(value instanceof Integer){
                conf.setValueInt((Integer) value);
            }else if(value instanceof String){
                conf.setValueText((String) value);
            }else if(value instanceof Map){
                conf.setValueMap((Map) value);
            }else if(value instanceof ArrayList){
                conf.setValueList((ArrayList) value);
            }else {
                throw new RuntimeException("参数类型错误");
            }
            conf.setDefaultValue(value.toString());
            sysConfRepo.save(conf);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public SysConf getSysConf(){
        List<SysConf> sysConfList = sysConfRepo.findAll();
        SysConf sysConf = new SysConf();
        if (sysConfList == null) {
            sysConf = sysConfList.get(0);
        } else {
            sysConfRepo.save(sysConf);
        }
        return sysConf;
    }

    /**
     * 获取对应key值得conf配置
     * @param key
     * @return
     */
    public SysConf getSysConf(String key) {
        SysConf conf = this.getSysConf(key, null);
        return conf;
    }
    /**
     * 获取对应key值得conf配置
     * @param key
     * @return
     */
    public SysConf getSysConf(String key, String brandAppId) {
        SysConf conf = this.getSysConf(key, brandAppId, null);
        return conf;
    }
    /**
     * 获取对应key值得conf配置
     * @param key
     * @return
     */
    public SysConf getSysConf(String key, String brandAppId, SysConfTypeEnum type) {
        SysConf conf = this.getSysConf(key, brandAppId, type, null);
        return conf;
    }
    /**
     * 获取对应key值得conf配置
     * @param key
     * @return
     */
    public SysConf getSysConf(String key, String brandAppId, SysConfTypeEnum type, String defaultValue) {
        SysConf conf = this.queryInsertSysConf(key, brandAppId, type, defaultValue);
        return conf;
    }

    /**
     * 生成配置表
     * @param key
     * @return
     */
    private SysConf queryInsertSysConf(String key, String brandAppId, SysConfTypeEnum type, String defaultValue) {
        SysConf conf = sysConfRepo.findByKeyAndBrandAppId(key,brandAppId);
        ObjectMapper mpper = new ObjectMapper();
        type = conf.getDataType();
        defaultValue = conf.getDefaultValue();
        if (conf == null) {
            conf = new SysConf();
            conf.setKey(key);
            // 如果传入type,则优先使用,没有则使用默认的
        }
        if (type == null) {
            type = SysConfTypeEnum.TEXT;
            conf.setDataType(type);
        }
        switch (type) {
            case INT:
                conf.setValueInt(Integer.parseInt(defaultValue));
                break;
            case TEXT:
                conf.setValueText(defaultValue);
                break;
            case MAP:
                try{
                    conf.setValueMap(mpper.readValue(defaultValue,Map.class));
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case LIST:
                try{
                    conf.setValueList(mpper.readValue(defaultValue,ArrayList.class));
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
        }
        sysConfRepo.save(conf);
        return conf;
    }

    /**
     * 通过配置表查询
     */
    public Map getDetailBySysConf(SysConf object) {
        if (object == null) {
            return null;
        }
        // 获取值得类型
        Object value = null;
        Assert.notNull(object.getDataType(),"类型不能为空");
        switch (object.getDataType()) {
            case INT:
                value = object.getValueInt();
                break;
            case LIST:
                value = object.getValueList();
                break;
            case MAP:
                value = object.getValueMap();
                break;
            case TEXT:
                value = object.getValueText();
                break;
        }

        Map map = new HashMap<>();
        map.put("key",object.getKey());
        map.put("title",object.getTitle());
        map.put("dataType",object.getDataType());
        map.put("value",value);
        return map;
    }

}
