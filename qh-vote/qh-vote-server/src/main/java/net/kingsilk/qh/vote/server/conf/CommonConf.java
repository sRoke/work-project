package net.kingsilk.qh.vote.server.conf;


import net.kingsilk.qh.vote.service.QhVoteProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonConf {
    @Bean
    public QhVoteProperties qhVoteProperties() {
        return new QhVoteProperties();
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
