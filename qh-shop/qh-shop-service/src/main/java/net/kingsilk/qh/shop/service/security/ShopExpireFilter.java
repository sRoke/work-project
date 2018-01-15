package net.kingsilk.qh.shop.service.security;

import net.kingsilk.qh.shop.api.ErrStatus;
import net.kingsilk.qh.shop.api.ErrStatusException;
import net.kingsilk.qh.shop.domain.Shop;
import net.kingsilk.qh.shop.repo.ShopRepo;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Component
public class ShopExpireFilter extends OncePerRequestFilter
        implements InitializingBean, Filter {

    @Autowired
    private ShopRepo shopRepo;


    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        AntPathMatcher pathMatcher = new AntPathMatcher();

        String url = request.getPathInfo();
        String shopPattern = "/brandApp/{brandAppId}/shop/{shopId}/**";
        String itemPropPattern = "/brandApp/{brandAppId}/shop/{shopId}/itemProp/{itemPropId}/**";
        String itemPattern = "/brandApp/{brandAppId}/shop/{shopId}/item/{itemId}/**";
        Map<String, String> shopMap = new LinkedHashMap<>();
        Map<String, String> itemPropMap = new LinkedHashMap<>();
        Map<String, String> itemMap = new LinkedHashMap<>();

        try {
            shopMap = pathMatcher.extractUriTemplateVariables(shopPattern, url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            itemPropMap = pathMatcher.extractUriTemplateVariables(itemPropPattern, url);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            itemMap = pathMatcher.extractUriTemplateVariables(itemPattern, url);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String brandAppId = shopMap.get("brandAppId");
        String shopId = shopMap.get("shopId");
        String itemPropId = itemPropMap.get("itemPropId");
        String itemId = itemMap.get("itemId");

        String basePath = "/api/brandApp/" + brandAppId + "/shop/" + shopId;
        List<RequestMatcher> matcherList = new ArrayList<>();
        matcherList.add(new AntPathRequestMatcher(basePath + "/authorities", HttpMethod.GET.name()));

        matcherList.add(new AntPathRequestMatcher(basePath, HttpMethod.GET.name()));
        matcherList.add(new AntPathRequestMatcher(basePath, HttpMethod.PUT.name()));

        matcherList.add(new AntPathRequestMatcher(basePath + "/shopAccount", HttpMethod.GET.name()));
        matcherList.add(new AntPathRequestMatcher(basePath + "/shopAccount/list", HttpMethod.GET.name()));

        matcherList.add(new AntPathRequestMatcher(basePath + "/order/**", HttpMethod.GET.name()));
//        matcherList.add(new AntPathRequestMatcher(basePath + "/order", HttpMethod.GET.name()));

        matcherList.add(new AntPathRequestMatcher(basePath + "/refund/**", HttpMethod.GET.name()));

        matcherList.add(new AntPathRequestMatcher(basePath + "/item/**", HttpMethod.GET.name()));
        matcherList.add(new AntPathRequestMatcher(basePath + "/item/**", HttpMethod.POST.name()));
        matcherList.add(new AntPathRequestMatcher(basePath + "/item/" + itemId, HttpMethod.PUT.name()));
        matcherList.add(new AntPathRequestMatcher(basePath + "/item", HttpMethod.PUT.name()));


        matcherList.add(new AntPathRequestMatcher(basePath + "/shopStaff/**", HttpMethod.GET.name()));

        matcherList.add(new AntPathRequestMatcher(basePath + "/category/**", HttpMethod.GET.name()));
        matcherList.add(new AntPathRequestMatcher(basePath + "/category/**", HttpMethod.POST.name()));
        matcherList.add(new AntPathRequestMatcher(basePath + "/category/**", HttpMethod.PUT.name()));
        matcherList.add(new AntPathRequestMatcher(basePath + "/category/**", HttpMethod.DELETE.name()));

        matcherList.add(new AntPathRequestMatcher(basePath + "/itemProp/**", HttpMethod.GET.name()));
        matcherList.add(new AntPathRequestMatcher(basePath + "/itemProp/**", HttpMethod.POST.name()));
        matcherList.add(new AntPathRequestMatcher(basePath + "/itemProp/**", HttpMethod.PUT.name()));
        matcherList.add(new AntPathRequestMatcher(basePath + "/itemProp/**", HttpMethod.DELETE.name()));

        matcherList.add(new AntPathRequestMatcher(basePath + "/shopOrder/**", HttpMethod.GET.name()));
        matcherList.add(new AntPathRequestMatcher(basePath + "/shopOrder/**", HttpMethod.POST.name()));

        Boolean flag = Boolean.FALSE;
        for (RequestMatcher matcher : matcherList) {
            if (matcher.matches(request)) {
                flag = Boolean.TRUE;
                break;
            }
        }
        if (!flag) {


            if (!shopMap.isEmpty()) {


                Shop shop = shopRepo.findOne(shopId);

            if (shop.getExpireDate().before(new Date())) {
                throw new ErrStatusException(ErrStatus.SHOP_EXPIRE, "门店过期，请续费！");
            }
//                if (shop != null) {
//                    throw new ErrStatusException(ErrStatus.SHOP_EXPIRE, "门店过期，请续费！");
//                }
            }
        }


        filterChain.doFilter(request, response);
    }


    @Override
    public void afterPropertiesSet() {

    }

}
