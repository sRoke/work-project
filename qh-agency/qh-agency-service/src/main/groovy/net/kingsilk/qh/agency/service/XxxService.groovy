package net.kingsilk.qh.agency.service

import groovy.transform.CompileStatic
import org.springframework.stereotype.Service

/**
 * 示例 Service。
 */
@Service
@CompileStatic
class XxxService {

    /**
     * Service 方法的入参、返回值都要使用强类型，javadoc要详细说明。
     *
     * @param name 用户姓名。
     * @return 打招呼信息
     */
    String hi(String name) {
        return "hi ${name} ~"
    }
}
