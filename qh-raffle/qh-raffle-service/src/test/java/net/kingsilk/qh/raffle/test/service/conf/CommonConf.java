package net.kingsilk.qh.raffle.test.service.conf;

import net.kingsilk.qh.raffle.QhRaffleProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonConf {
    @Bean
    public QhRaffleProperties qhRaffleProperties() {
        return new QhRaffleProperties();
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
