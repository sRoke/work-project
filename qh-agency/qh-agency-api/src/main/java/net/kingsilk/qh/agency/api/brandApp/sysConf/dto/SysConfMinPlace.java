package net.kingsilk.qh.agency.api.brandApp.sysConf.dto;

import io.swagger.annotations.ApiParam;

import javax.ws.rs.QueryParam;

public class SysConfMinPlace {

    @QueryParam(value = "generalAgencyMinPlaceNum")
    String generalAgencyMinPlaceNum;

    @ApiParam(value = "品牌商ID")
    @QueryParam(value = "regionaLagencyMinPlaceNum")
    String regionaLagencyMinPlaceNum;

    @ApiParam(value = "品牌商ID")
    @QueryParam(value = "leagueMinPlaceNum")
    String leagueMinPlaceNum;

    @ApiParam(value = "该后的值")
    @QueryParam(value = "generalAgencyMinPlace")
    String generalAgencyMinPlace;

    @ApiParam(value = "该后的值")
    @QueryParam(value = "regionaLagencyMinPlace")
    String regionaLagencyMinPlace;

    @ApiParam(value = "该后的值")
    @QueryParam(value = "leagueMinPlace")
    String leagueMinPlace;

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
}
