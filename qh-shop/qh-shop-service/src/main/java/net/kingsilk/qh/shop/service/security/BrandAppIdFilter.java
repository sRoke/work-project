package net.kingsilk.qh.shop.service.security;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.springframework.web.context.request.RequestAttributes.SCOPE_REQUEST;

/**
 * 将 OAuth2Authentication -> 包含员工权限信息的 Authentication
 * <p>
 * Spring Security OAuth 处理的资源认证过程中，授权的只是普通用户的信息，不是各个业务系统员工的信息。
 * 该 Filter 配置需要在 OAuth2AuthenticationProcessingFilter 之后。
 *
 * @see org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationProcessingFilter
 * @see org.springframework.security.web.authentication.AnonymousAuthenticationFilter
 * @see org.springframework.security.oauth2.provider.OAuth2Authentication
 */
public class BrandAppIdFilter
        extends OncePerRequestFilter
        implements InitializingBean {

    /**
     * Http 请求中 公司ID 的 请求头的名称。
     */
    static final String BRANDAPP_ID_REQUEST_HEADER = "BrandApp-Id";

    /**
     * Http 请求中 公司ID 的 请求头的名称。
     */
    final String BRANDAPP_ID_REQUEST_ATTR = getClass().getName() + ".BRANDAPP_ID_REQUEST_ATTR";

    /**
     * Http 请求中 门店ID 的 请求头的名称。
     */
    final String SHOP_ID_REQUEST_ATTR = getClass().getName() + ".SHOP_ID_REQUEST_ATTR";

    /**
     * 从 Request 请求头中读取公司ID，并保存到 RequestContextHolder 中。
     *
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        AntPathMatcher pathMatcher = new AntPathMatcher();

        String url = request.getPathInfo();
        String pattern = "/brandApp/{brandAppId}/**";
        String shopPattern = "/brandApp/{brandAppId}/shop/{shopId}/**";
        Map<String, String> brandAppMap = new LinkedHashMap<>();
        Map<String, String> shopMap = new LinkedHashMap<>();
        try {
            brandAppMap = pathMatcher.extractUriTemplateVariables(pattern, url);
        } catch (Exception e) {
//            e.printStackTrace()
        }

        try {
            shopMap = pathMatcher.extractUriTemplateVariables(shopPattern, url);
        } catch (Exception e) {
//            e.printStackTrace()
        }

        if (!brandAppMap.isEmpty()) {

            RequestContextHolder.getRequestAttributes().setAttribute(
                    BRANDAPP_ID_REQUEST_ATTR,
                    brandAppMap.get("brandAppId"),
                    SCOPE_REQUEST
            );

        }

        if (!shopMap.isEmpty()) {
            RequestContextHolder.getRequestAttributes().setAttribute(
                    SHOP_ID_REQUEST_ATTR,
                    shopMap.get("shopId"),
                    SCOPE_REQUEST
            );
        }

        filterChain.doFilter(request, response);
    }


    @Override
    public void afterPropertiesSet() {

    }

    /**
     * 获取公司ID。
     *
     * @return 公司ID
     */
    public String getBrandAppId() {
        return (String) RequestContextHolder
                .getRequestAttributes()
                .getAttribute(BRANDAPP_ID_REQUEST_ATTR, SCOPE_REQUEST);
    }


    /**
     * 获取店铺id
     */
    public String getShopId() {
        return (String) RequestContextHolder
                .getRequestAttributes()
                .getAttribute(SHOP_ID_REQUEST_ATTR, SCOPE_REQUEST);
    }
}
