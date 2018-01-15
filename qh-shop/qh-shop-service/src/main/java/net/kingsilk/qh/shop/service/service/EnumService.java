package net.kingsilk.qh.shop.service.service;

import groovy.transform.CompileStatic;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.RegexPatternTypeFilter;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * 系统枚举相关的服务
 */
@Service
@CompileStatic
class EnumService {
    /**
     * 查询的缓存结果。
     */
    /*
     * key: enumType
     * value:[{
     *      name:"",
     *      desp:""
     *},{},{}]
     */
    private static final Map cache = new HashMap();

    /**
     *
     * @return
     */
    public Map getEnumMap(String enumType) {
        Object entry = cache.get(enumType);
        if (entry != null) {
            Map<String, Object> entryMap = new HashMap<>();
            entryMap.put("enumType", entry);
            return entryMap;
        }

        Class clazz;
        try {
            clazz = EnumService.class.getClassLoader().loadClass("net.kingsilk.qh.shop.core." + enumType);
            load(clazz, enumType);
        } catch (ClassNotFoundException e) {
            return new HashMap();
        }

        Map<String, Object> entryMap = new HashMap<>();
        entryMap.put("enumType", cache.get(enumType));
        return entryMap;

    }

    public Map getEnumMap() {
        return cache;
    }

    static {
        try {
            loadAll();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    static void loadAll() throws ClassNotFoundException {
        cache.clear();

        final ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new RegexPatternTypeFilter(Pattern.compile("net\\.kingsilk\\..*\\..*\\..*\\..*Enum")));

        final Set<BeanDefinition> classes = provider.findCandidateComponents("net.kingsilk");

        for (BeanDefinition bean : classes) {
            Class<?> clazz = Class.forName(bean.getBeanClassName());
            if (clazz.isEnum()) {
                load(clazz, clazz.getName().substring(clazz.getName().lastIndexOf(".") + 1));
            }
        }
    }

    static void load(Class enumClass, String enumType) {
        Assert.notNull(enumClass);

        Assert.isTrue(enumClass.isEnum());

        Object entry = cache.get(enumType);
        if (entry != null) {
            return;
        }
        Object[] constants = enumClass.getEnumConstants();

        Map data = new HashMap();
        String code = "code";
        String desp = "desp";

//        if (constants != null) {
//            for (Object constant : constants) {
//                if (constant != null) {
//                    Field[] fs = constant.getClass().getDeclaredFields();
//                    data.put("name", constant.getClass().getName());
//                    data.put("description", constant.getClass());
//                }
//                cache.put(enumType, data);
//            }
//        }


    }
}