package net.kingsilk.qh.agency.admin.controller.SmsTemplate

import net.kingsilk.qh.agency.domain.SmsTemplate
import org.springframework.data.domain.Page

/**
 * Created by zcw on 3/16/17.
 */
class SmsTemplateListResp {

    int curPage;

    int pageSize;

    int totalCount;

    List<SmsTemplateUIModel> recList = new ArrayList<>()

    void convert(Page<SmsTemplate> page) {
        this.curPage = page.getCurPage()
        this.pageSize = page.getPageSize()
        this.totalCount = page.getTotalCount()
        page.list.each { SmsTemplate it ->
            SmsTemplateUIModel model = SmsTemplateUIModel.convert(it)
            this.recList.add(model)
        }
    }
}
