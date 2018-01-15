package net.kingsilk.qh.shop.api.brandApp.shop.withdraw.dto;

import io.swagger.annotations.ApiParam;

public class WithdrawInfo {

    @ApiParam(value = "提现总金额")
    private Integer sum;

    @ApiParam(value = "可提现余额")
    private Integer account;

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public Integer getAccount() {
        return account;
    }

    public void setAccount(Integer account) {
        this.account = account;
    }
}
