package net.kingsilk.qh.oauth.core.wap.conf;

import groovy.transform.*;
import org.springframework.boot.autoconfigure.web.*;
import org.springframework.context.annotation.*;
import org.springframework.core.*;
import org.springframework.core.serializer.support.*;
import org.springframework.session.data.mongo.*;
import org.springframework.session.web.http.*;
import org.springframework.util.*;

import java.io.*;
import java.util.*;

/**
 * spring-session 相关配置
 *
 * spring-session-mongo 默认会使用 JACKSON 作为数据库中序列化的工具，但有问题。
 * 使用 JDK 的序列化，需要配合 `ConfigurableObjectInputStream`
 * `Thread.currentThread().getContextClassLoader()`
 * 防止 不同 ClassLoader 加载同一个类，却不相同的问题。
 *
 * 参考：https://docs.spring.io/spring-boot/docs/1.5.7.RELEASE/reference/htmlsingle/#using-boot-devtools-known-restart-limitations
 *
 * @see org.springframework.session.data.mongo.config.annotation.web.http.MongoHttpSessionConfiguration
 * @see org.springframework.session.data.mongo.AbstractMongoSessionConverter
 */
@Configuration
@CompileStatic
public class SessionConf {

    @Bean
    public JdkMongoSessionConverter jdkMongoSessionConverter() {

        // 参考：DefaultSerializer
        SerializingConverter sc = new SerializingConverter();

        // 参考：DefaultDeserializer
        DeserializingConverter dc = new DeserializingConverter(inputStream -> {

            ObjectInputStream objectInputStream = new ConfigurableObjectInputStream(
                    inputStream,
                    Thread.currentThread().getContextClassLoader()
            );
            try {
                return objectInputStream.readObject();
            } catch (ClassNotFoundException e) {
                throw new NestedIOException("Failed to deserialize object type", e);
            }

        });

        return new JdkMongoSessionConverter(sc, dc);
    }

    @Bean
    public CookieSerializer cookieSerializer(
            ServerProperties serverProperties
    ) {
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();

        String cookiePath = Arrays.asList(
                serverProperties.getSession().getCookie().getPath(),
                serverProperties.getContextPath(),
                "/"
        ).stream()
                .filter(StringUtils::hasText)
                .findFirst()
                .get();

        serializer.setCookiePath(cookiePath);
        return serializer;
    }

}
