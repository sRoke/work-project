package net.kingsilk.qh.agency.admin.controller.sms

import net.kingsilk.qh.agency.domain.Sms
import net.kingsilk.qh.agency.util.Page
import org.springframework.format.annotation.DateTimeFormat

/**
 * Created by zcw on 3/16/17.
 */
class SmsListResp {

    int curPage;

    int pageSize;

    int totalCount;

    List<SmsInfo> recList = new ArrayList()

    class SmsInfo {
        /**
         * 创建时间
         */
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        Date dateCreated;

        /**
         * 手机号
         */
        String phone;

        /**
         * 短信类型
         */
        String type;

        /**
         * 短信内容
         */
        String content;

        /**
         * 短信发送平台
         */
        String platform;
    }

    void convert(Page<Sms> page) {
        this.curPage = page.getCurPage()
        this.pageSize = page.getPageSize()
        this.totalCount = page.getTotalCount()
        page.getList().each { Sms it ->
            SmsInfo info = new SmsInfo()
            info.dateCreated = it.getDateCreated()
            info.phone = it.getPhone()
            info.type = it.getSmsType()
            info.content = it.getContent()
            info.platform = it.getPaltform()?.getCode()
            this.recList.add(info)
        }
    }
}
