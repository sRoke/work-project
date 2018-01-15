package net.kingsilk.qh.agency.service

import groovy.transform.CompileStatic
import net.kingsilk.qh.agency.domain.Company
import net.kingsilk.qh.agency.repo.CompanyRepo
import net.kingsilk.qh.agency.security.BrandAppIdFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
@CompileStatic
class CompanyService {

    @Autowired
    CompanyRepo companyRepo

    /**
     * 当前登录的会员
     * @return
     */
//    Company getCurCompany() {
//        String brandId = BrandIdFilter.brandId
//        Company company = companyRepo.findOne(companyId)        //这里就不做删除判断了
//        return company
//    }
}
