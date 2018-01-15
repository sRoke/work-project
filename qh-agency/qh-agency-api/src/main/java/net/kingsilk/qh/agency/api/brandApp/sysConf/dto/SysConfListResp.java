package net.kingsilk.qh.agency.api.brandApp.sysConf.dto;

import java.util.Map;

public class SysConfListResp {

    private String generalAgencyMinPlaceNum;

    private String regionaLagencyMinPlaceNum;

    private String leagueMinPlaceNum;

    private String generalAgencyMinPlace;

    private String regionaLagencyMinPlace;

    private String leagueMinPlace;

    private String withdrawalMinAmount;

    private String cashierDiscount;

    private Map partnerTypes;

    public Map getPartnerTypes() {
        return partnerTypes;
    }

    public void setPartnerTypes(Map partnerTypes) {
        this.partnerTypes = partnerTypes;
    }

    public String getGeneralAgencyMinPlaceNum() {
        return generalAgencyMinPlaceNum;
    }

    public void setGeneralAgencyMinPlaceNum(String generalAgencyMinPlaceNum) {
        this.generalAgencyMinPlaceNum = generalAgencyMinPlaceNum;
    }

    public String getRegionaLagencyMinPlaceNum() {
        return regionaLagencyMinPlaceNum;
    }

    public void setRegionaLagencyMinPlaceNum(String regionaLagencyMinPlaceNum) {
        this.regionaLagencyMinPlaceNum = regionaLagencyMinPlaceNum;
    }

    public String getLeagueMinPlaceNum() {
        return leagueMinPlaceNum;
    }

    public void setLeagueMinPlaceNum(String leagueMinPlaceNum) {
        this.leagueMinPlaceNum = leagueMinPlaceNum;
    }

    public String getGeneralAgencyMinPlace() {
        return generalAgencyMinPlace;
    }

    public void setGeneralAgencyMinPlace(String generalAgencyMinPlace) {
        this.generalAgencyMinPlace = generalAgencyMinPlace;
    }

    public String getRegionaLagencyMinPlace() {
        return regionaLagencyMinPlace;
    }

    public void setRegionaLagencyMinPlace(String regionaLagencyMinPlace) {
        this.regionaLagencyMinPlace = regionaLagencyMinPlace;
    }

    public String getLeagueMinPlace() {
        return leagueMinPlace;
    }

    public void setLeagueMinPlace(String leagueMinPlace) {
        this.leagueMinPlace = leagueMinPlace;
    }

    public String getWithdrawalMinAmount() {
        return withdrawalMinAmount;
    }

    public void setWithdrawalMinAmount(String withdrawalMinAmount) {
        this.withdrawalMinAmount = withdrawalMinAmount;
    }

    public String getCashierDiscount() {
        return cashierDiscount;
    }

    public void setCashierDiscount(String cashierDiscount) {
        this.cashierDiscount = cashierDiscount;
    }
}
