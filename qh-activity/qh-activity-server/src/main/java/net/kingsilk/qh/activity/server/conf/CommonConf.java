package net.kingsilk.qh.activity.server.conf;

import net.kingsilk.qh.activity.service.QhActivityProperties;
import org.springframework.context.annotation.*;

import java.util.*;

@Configuration
public class CommonConf {
    @Bean
    public QhActivityProperties qhActivityProperties() {
        return new QhActivityProperties();
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
}
