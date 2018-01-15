package net.kingsilk.qh.agency.admin.api.refund.dto;

import io.swagger.annotations.ApiModel;
import net.kingsilk.qh.agency.core.LogisticsCompanyEnum;

@ApiModel(value = "售后订单处理意见信息")
public class RefundHandleReq  {

     private String id;
     private Boolean isAgree;
     private LogisticsCompanyEnum company;
     private String expressNo;

     public String getId() {
          return id;
     }

     public void setId(String id) {
          this.id = id;
     }

     public Boolean getAgree() {
          return isAgree;
     }

     public void setAgree(Boolean agree) {
          isAgree = agree;
     }

     public LogisticsCompanyEnum getCompany() {
          return company;
     }

     public void setCompany(LogisticsCompanyEnum company) {
          this.company = company;
     }

     public String getExpressNo() {
          return expressNo;
     }

     public void setExpressNo(String expressNo) {
          this.expressNo = expressNo;
     }
}
