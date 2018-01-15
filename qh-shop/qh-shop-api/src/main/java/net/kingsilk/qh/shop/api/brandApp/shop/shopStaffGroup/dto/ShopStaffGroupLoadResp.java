package net.kingsilk.qh.shop.api.brandApp.shop.shopStaffGroup.dto;

import net.kingsilk.qh.shop.api.common.dto.Base;

import java.util.Map;

public class ShopStaffGroupLoadResp extends Base {

    private Map<String, Map<String, Map<String, String>>> authorMap;

    public Map<String, Map<String, Map<String, String>>> getAuthorMap() {
        return authorMap;
    }

    public void setAuthorMap(Map<String, Map<String, Map<String, String>>> authorMap) {
        this.authorMap = authorMap;
    }
}
