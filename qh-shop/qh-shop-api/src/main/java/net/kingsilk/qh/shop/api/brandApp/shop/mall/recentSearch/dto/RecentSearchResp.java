package net.kingsilk.qh.shop.api.brandApp.shop.mall.recentSearch.dto;

import java.util.LinkedHashSet;

public class RecentSearchResp {

    private LinkedHashSet<String> keyWords;

    public LinkedHashSet<String> getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(LinkedHashSet<String> keyWords) {
        this.keyWords = keyWords;
    }
}
