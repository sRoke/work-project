package net.kingsilk.qh.agency.admin.controller.Refund

import io.swagger.annotations.ApiModel
import net.kingsilk.qh.agency.core.LogisticsCompanyEnum


@ApiModel(value = "售后订单处理意见信息")
class RefundHandleReq {

     String id;
     Boolean isAgree;
     LogisticsCompanyEnum company;
     String expressNo;


     @Override
     public String toString() {
          return "RefundHandleReq{" +
                  "id='" + id + '\'' +
                  ", isAgree=" + isAgree +
                  ", company=" + company +
                  ", expressNo='" + expressNo + '\'' +
                  '}';
     }
}
