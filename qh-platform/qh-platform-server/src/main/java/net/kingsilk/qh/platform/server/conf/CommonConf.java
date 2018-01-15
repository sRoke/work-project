package net.kingsilk.qh.platform.server.conf;

import net.kingsilk.qh.platform.msg.EventPublisher;
import net.kingsilk.qh.platform.service.QhPlatformProperties;
import net.kingsilk.qh.platfrom.msg.impl.EventPublisherImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonConf {
    @Bean
    public QhPlatformProperties qhPlatformProperties() {
        return new QhPlatformProperties();
    }
//
//    @Bean
//    FormattingConversionServiceFactoryBean formattingConversionServiceFactoryBean(
//            Set<Converter> converters
//    ) {
//        FormattingConversionServiceFactoryBean fac = new FormattingConversionServiceFactoryBean();
//        fac.setConverters(converters);
//        return fac;
//    }

    @Bean
    public EventPublisher eventPublisher(){
        return new EventPublisherImpl();
    }
}
