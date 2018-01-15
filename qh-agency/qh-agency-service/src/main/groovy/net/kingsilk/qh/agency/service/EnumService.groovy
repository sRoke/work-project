package net.kingsilk.qh.agency.service

import groovy.transform.CompileStatic
import net.kingsilk.qh.agency.core.StaffAuthorityEnum
import org.springframework.beans.factory.config.BeanDefinition
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider
import org.springframework.core.type.filter.RegexPatternTypeFilter
import org.springframework.stereotype.Service
import org.springframework.util.Assert

import java.util.regex.Pattern

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
    static final Map cache = [:]

    /**
     *
     * @return
     */
    public Map getEnumMap(String enumType) {
        def entry = cache.get(enumType)
        if (entry) {
            return [
                    "$enumType": entry
            ]
        }

        Class clazz
        try {
            clazz = EnumService.getClassLoader().loadClass("net.kingsilk.qh.agency.core." + enumType)
            load(clazz, enumType)
        } catch (ClassNotFoundException e) {
            return [:]
        }

        return [
                "$enumType": cache.get(enumType)
        ]

    }

    public Map getEnumMap() {
        return cache
    }

    static {
        loadAll()
    }

    static loadAll() {
        cache.clear()

        final ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new RegexPatternTypeFilter(Pattern.compile("net\\.kingsilk\\..*\\..*\\..*\\..*Enum")));

        final Set<BeanDefinition> classes = provider.findCandidateComponents("net.kingsilk");

        for (BeanDefinition bean : classes) {
            Class<?> clazz = Class.forName(bean.getBeanClassName());
            if (clazz.isEnum()) {
                load(clazz, clazz.name.substring(clazz.name.lastIndexOf(".") + 1))
            }
        }
    }

    static void load(Class enumClass, String enumType) {
        Assert.notNull(enumClass)

        Assert.isTrue(enumClass.isEnum())

        if (enumClass == StaffAuthorityEnum) {
            return
        }
        def entry = cache.get(enumType)
        if (entry) {
            return
        }
        Object[] consts = enumClass.getEnumConstants();

        def data = []
        String code = "code"
        String desp = "desp";
        /*if (consts) {
            consts.each {
                if (it) {
                    data.add([
                            name       : it."${code}",
                            description: it."${desp}"
                    ])
                }
                cache.put(enumType, data)
            };

        }*/


    }
}