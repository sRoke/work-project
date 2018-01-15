package net.kingsilk.qh.vote.api.voteApp.vote.voteWorks.dto;

public class CheckNumResp {

    /**
     * 未审核数量
     */
    private Integer checkingNum;

    /**
     * 已审核数量
     */
    private Integer checkedNum;

    public Integer getCheckingNum() {
        return checkingNum;
    }

    public void setCheckingNum(Integer checkingNum) {
        this.checkingNum = checkingNum;
    }

    public Integer getCheckedNum() {
        return checkedNum;
    }

    public void setCheckedNum(Integer checkedNum) {
        this.checkedNum = checkedNum;
    }
}
