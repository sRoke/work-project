package net.kingsilk.qh.oauth.core.wap.conf;

import io.swagger.jaxrs.config.BeanConfig;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class SwaggerConf extends WebMvcConfigurerAdapter {
    @Bean
    public BeanConfig swaggerBeanConfig(
            BuildProperties buildProperties
    ) {

        BeanConfig config = new BeanConfig();
        config.setTitle(buildProperties.getName());
        config.setVersion(buildProperties.getVersion());
        config.setBasePath("/api");

        String[] schemes = {"https", "http"};
        config.setSchemes(schemes);
        config.setResourcePackage("net.kingsilk.qh.oauth.api");
        config.setScan(true);
        return config;
    }
//
//    @Bean
//    public Docket petApi() {
//
//        ApiInfo apiInfo = new ApiInfoBuilder()
//                .title("qh-oauth-wap")
//                .description("qh-oauth-wap api 文档")
//                .contact(new Contact("钱皇网络", "https://kingsilk.net/", "admin@kingsilk.net"))
//                .license("private")
//                //.licenseUrl("https://github.com/springfox/springfox/blob/master/LICENSE")
//                .version("2.0")
//                .build();
//
//        ApiKey apiKey = new ApiKey("mykey", "api_key", "header");
//
//        AlternateTypeRule rule1 = newRule(
//                typeResolver.resolve(DeferredResult.class, typeResolver.resolve(ResponseEntity.class, WildcardType.class)),
//                typeResolver.resolve(WildcardType.class));
//
////        // 全局响应信息
////        ResponseMessage responseMessage = new ResponseMessageBuilder()
////                .code(500)
////                .message("500 message")
////                .responseModel(new ModelRef("Error"))
////                .build()
//
//        SecurityContext securityContext = SecurityContext.builder()
//                .securityReferences(defaultAuth())
//                .forPaths(and(
//                        not(regex("/error.*")),
//                        not(regex("/")),
//
//                        // spring-boot-starter-actuator
//                        not(regex("/logfile.*")),
//                        not(regex("/auditevents.*")),
//                        not(regex("/autoconfig.*")),
//                        not(regex("/beans.*")),
//                        not(regex("/configprops.*")),
//                        not(regex("/dump.*")),
//                        not(regex("/info.*")),
//                        not(regex("/mappings.*")),
//                        not(regex("/trace.*")),
//                        not(regex("/env.*")),
//                        not(regex("/actuator.*")),
//                        not(regex("/health.*")),
//                        not(regex("/heapdump.*")),
//                        not(regex("/logfile.*")),
//                        not(regex("/loggers.*")),
//                        not(regex("/metrics.*"))
//                ))
//                .build();
//
//        // 全局 header
//        Parameter parameter = new ParameterBuilder()
//                .name("Authorization")
//                .description("Bearer格式的认证信息")
//                .modelRef(new ModelRef("string"))
//                .parameterType("header")
//                .required(true)
//                .build();
//
//        Docket docket = new Docket(DocumentationType.SWAGGER_2);
//
//        docket.select()  // => ApiSelectorBuilder
//                .apis(RequestHandlerSelectors.any())
//                .paths(and(
//                        not(regex("/error.*")),
//                        not(regex("/")),
//                        not(regex("/api/test.*"))
//                ))
//                .build();
//
//        docket
//                .ignoredParameterTypes(MetaClass.class)  // XXX 重要，对于groovy而言
//                .apiInfo(apiInfo)
//                .pathMapping("/")
//                .directModelSubstitute(LocalDate.class, String.class)
//                .genericModelSubstitutes(ResponseEntity.class)
//                .alternateTypeRules(rule1)
//                .useDefaultResponseMessages(false)
////                .globalResponseMessage(RequestMethod.GET, [responseMessage])
//                .securitySchemes(Arrays.asList(apiKey))
//                .securityContexts(Arrays.asList(securityContext))
//                //.enableUrlTemplating(true)
//                .globalOperationParameters(Arrays.asList(parameter));
//        //.additionalModels(typeResolver.resolve(AdditionalModel.class))
//        return docket;
//    }
//
//
//    @Autowired
//    private TypeResolver typeResolver;
//
//
//    public List<SecurityReference> defaultAuth() {
//        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
//        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
//        authorizationScopes[0] = authorizationScope;
//        return Arrays.asList(
//                new SecurityReference("mykey", authorizationScopes)
//        );
//    }
//
//    @Bean
//    public SecurityConfiguration security() {
//        return new SecurityConfiguration(
//                "test-app-client-id",
//                "test-app-client-secret",
//                "test-app-realm",
//                "test-app",
//                "apiKey",
//                ApiKeyVehicle.HEADER,
//                "api_key",
//                "," /*scope separator*/);
//    }
//
//    @Bean
//    public UiConfiguration uiConfig() {
//        return new UiConfiguration(
//                "validatorUrl",// url
//                "none",       // docExpansion          => none | list
//                "alpha",      // apiSorter             => alpha
//                "schema",     // defaultModelRendering => schema
//                UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS,
//                false,        // enableJsonEditor      => true | false
//                true,         // showRequestHeaders    => true | false
//                60000L);      // requestTimeout => in milliseconds, defaults to null (uses jquery xh timeout)
//    }

}
