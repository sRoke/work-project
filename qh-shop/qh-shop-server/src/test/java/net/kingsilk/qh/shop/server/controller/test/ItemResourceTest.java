package net.kingsilk.qh.shop.server.controller.test;

import net.kingsilk.qh.shop.server.controller.BaseTest;
import org.junit.Test;

public class ItemResourceTest extends BaseTest {

    @Test
    public void page() {
        String url = qhShopProperties.getShop().getAdmin().getUrl()
                + "brandApp/5a0e724c46e0fb00082d605d/shop/5a0e72e71794266d27a4eb08/mall/item?page=0&size=20";


        requestPost(url, null);
    }

}
