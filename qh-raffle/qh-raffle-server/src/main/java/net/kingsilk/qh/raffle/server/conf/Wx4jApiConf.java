package net.kingsilk.qh.raffle.server.conf;

import net.kingsilk.qh.raffle.QhRaffleProperties;
import net.kingsilk.wx4j.broker.api.wxCom.mp.at.WxComMpAtApi;
import net.kingsilk.wx4j.broker.api.wxCom.mp.scence.WxScenceApi;
import net.kingsilk.wx4j.broker.api.wxCom.mp.user.WxComMpUserApi;
import net.kingsilk.wx4j.broker.client.wxCom.mp.at.WxComMpAtApiImpl;
import net.kingsilk.wx4j.broker.client.wxCom.mp.scence.WxScenceApiImpl;
import net.kingsilk.wx4j.broker.client.wxCom.mp.user.WxComMpUserApiImpl;
import net.kingsilk.wx4j.client.mp.api.kfMsg.KfMsgApi;
import net.kingsilk.wx4j.client.mp.api.kfMsg.KfMsgApiImpl;
import net.kingsilk.wx4j.client.mp.api.qrCode.QrCodeApi;
import net.kingsilk.wx4j.client.mp.api.qrCode.QrCodeApiImpl;
import net.kingsilk.wx4j.client.mp.api.tplMsg.TplMsgApi;
import net.kingsilk.wx4j.client.mp.api.tplMsg.TplMsgApiImpl;
import net.kingsilk.wx4j.client.mp.api.user.UserApi;
import net.kingsilk.wx4j.client.mp.api.user.UserApiImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestOperations;

@Configuration
public class Wx4jApiConf {

    @Bean
    public WxComMpUserApi wx4jWxComUserApi(
            @Qualifier("restTemplate")
                    RestOperations restOperations,
            QhRaffleProperties qhRaffleProperties

    ) {
        return new WxComMpUserApiImpl(restOperations, qhRaffleProperties.getWx4jUt().getBasePath());
    }

    @Bean
    public TplMsgApi wx4jTplMsgApi(
            @Qualifier("restTemplate")
                    RestOperations restOperations
    ) {
        return new TplMsgApiImpl(restOperations);
    }

    @Bean
    public WxComMpAtApi wx4jWxComMpAtApi(
            @Qualifier("restTemplate")
                    RestOperations restOperations,
            QhRaffleProperties qhRaffleProperties
    ) {
        return new WxComMpAtApiImpl(restOperations, qhRaffleProperties.getWx4jUt().getBasePath());
    }

    @Bean
    public QrCodeApi wx4jQrCodeApi(
            @Qualifier("restTemplate")
                    RestOperations restOperations
    ) {
        return new QrCodeApiImpl(restOperations);
    }


    @Bean
    public UserApi wx4jUserApi(
            @Qualifier("restTemplate")
                    RestOperations restOperations
    ) {
        return new UserApiImpl(restOperations);
    }

    @Bean
    public KfMsgApi wx4jkfMsgApi(
            @Qualifier("restTemplate")
                    RestOperations restOperations
    ) {
        return new KfMsgApiImpl(restOperations);
    }

    @Bean
    public WxScenceApi wx4jWxScenceApi(
            @Qualifier("restTemplate")
                    RestOperations restOperations,
            QhRaffleProperties qhRaffleProperties
    ) {
        return new WxScenceApiImpl(restOperations, qhRaffleProperties.getWx4jUt().getBasePath());
    }
}
