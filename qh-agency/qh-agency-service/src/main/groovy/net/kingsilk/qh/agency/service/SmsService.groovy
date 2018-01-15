package net.kingsilk.qh.agency.service

import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Service

/**
 * 发送短信服务
 */
@Service
@CompileStatic
@Deprecated
class SmsService {

    @Autowired
    MongoTemplate mongoTemplate

    //todo


}
