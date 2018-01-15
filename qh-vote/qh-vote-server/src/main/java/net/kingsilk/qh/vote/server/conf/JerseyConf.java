package net.kingsilk.qh.vote.server.conf;

import io.swagger.jaxrs.config.BeanConfig;
import net.kingsilk.qh.vote.server.resource.home.HomeResource;
import net.kingsilk.qh.vote.server.resource.voteApp.authorities.AuthoritiesResource;
import net.kingsilk.qh.vote.server.resource.voteApp.common.CommonResource;
import net.kingsilk.qh.vote.server.resource.voteApp.vote.task.TaskResource;
import net.kingsilk.qh.vote.server.resource.voteApp.vote.test.TestResource;
import net.kingsilk.qh.vote.server.resource.voteApp.vote.voteActivity.VoteActivityResource;
import net.kingsilk.qh.vote.server.resource.voteApp.vote.voteRecord.VoteRecordResource;
import net.kingsilk.qh.vote.server.resource.voteApp.vote.voteWorks.VoteWorksResource;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MediaType;


@Configuration
public class JerseyConf {

    @Autowired
    ApplicationContext ctx;


    @Bean
    ResourceConfig resourceConfig(BeanConfig swaggerBeanConfig) {


        ResourceConfig resourceConfig = new ResourceConfig();

        // 强制 响应的contentType中追加 charset
        // http://stackoverflow.com/questions/5514087/jersey-rest-default-character-encoding/20569571
        resourceConfig.register(new ContainerResponseFilter() {
            @Override
            public void filter(ContainerRequestContext request, ContainerResponseContext response) {
                MediaType type = response.getMediaType();
                if (type != null) {
                    String contentType = type.toString();
                    if (!contentType.contains("charset")) {
                        contentType = contentType + ";charset=utf-8";
                        response.getHeaders().putSingle("Content-Type", contentType);
                    }
                }
            }
        });

        resourceConfig.register(MultiPartFeature.class);
        resourceConfig.register(io.swagger.jaxrs.listing.ApiListingResource.class);
        resourceConfig.register(io.swagger.jaxrs.listing.SwaggerSerializers.class);

        // 根据文档，需要手动一一将RESTful controller 添加至此。
        // http://docs.spring.io/spring-boot/docs/1.5.1.RELEASE/reference/htmlsingle/#boot-features-jersey

        // 统一要求：全部注册实现类，而非接口类

        resourceConfig.register(VoteRecordResource.class);
        resourceConfig.register(VoteActivityResource.class);
        resourceConfig.register(VoteWorksResource.class);
        resourceConfig.register(AuthoritiesResource.class);
        resourceConfig.register(CommonResource.class);
        resourceConfig.register(TaskResource.class);
        resourceConfig.register(HomeResource.class);
        resourceConfig.register(TestResource.class);

        resourceConfig.register(net.kingsilk.qh.vote.server.resource.voteApp.vote.wap.voteRecord.VoteRecordResource.class);
        resourceConfig.register(net.kingsilk.qh.vote.server.resource.voteApp.vote.wap.voteWorks.VoteWorksResource.class);
        resourceConfig.register(net.kingsilk.qh.vote.server.resource.voteApp.vote.wap.voteActivity.VoteActivityResource.class);


        resourceConfig.property(ServerProperties.RESPONSE_SET_STATUS_OVER_SEND_ERROR, true);
        resourceConfig.property(ServerProperties.PROCESSING_RESPONSE_ERRORS_ENABLED, true);
        return resourceConfig;
    }
}
