package net.kingsilk.qh.agency.admin.controller.order

import io.swagger.annotations.ApiModel
import net.kingsilk.qh.agency.domain.Adc
import net.kingsilk.qh.agency.domain.Order


@ApiModel(value = "订单修改提供信息")
class OrderAddressReq {

     String orderId;
     String receiver;
     String phone;
     String countyNo;
     String street;

//     Order.OrderAddress convertReqToAddress(Order.OrderAddress orderAddress, Adc adc) {
//          System.err.println(receiver+"=="+phone+"=="+street)
//          orderAddress.receiver = receiver
//          orderAddress.phone = phone
//          orderAddress.street = street
//          orderAddress.adc = adc
//          return orderAddress;
//     }


     @Override
     public String toString() {
          return "OrderAddressReq{" +
                  "orderId='" + orderId + '\'' +
                  ", receiver='" + receiver + '\'' +
                  ", phone='" + phone + '\'' +
                  ", countyNo='" + countyNo + '\'' +
                  ", street='" + street + '\'' +
                  '}';
     }
}
