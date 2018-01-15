package net.kingsilk.qh.agency.wap.api.report.dto;

import javax.ws.rs.QueryParam;
import java.util.Date;

/**
 * Created by lit on 17/7/28.
 */
public class PurchaseReq {
    @QueryParam(value = "startDate")
    private Date startDate;
    @QueryParam(value = "endDate")
    private Date endDate;


    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
