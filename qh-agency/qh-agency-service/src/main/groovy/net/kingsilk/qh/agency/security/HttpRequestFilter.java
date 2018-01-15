package net.kingsilk.qh.agency.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.kingsilk.qh.agency.api.UniResp;
import net.kingsilk.qh.agency.service.PartnerService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *弃用
 */
//@Order(5)
//@WebFilter(filterName = "httpRequestFilter", urlPatterns = "/api/*")
@Deprecated
public class HttpRequestFilter extends OncePerRequestFilter
        implements InitializingBean {

    @Autowired
    private PartnerService partnerService;
    //    @Autowired
//    private PartnerRepo partnerRepo;
    private List<String> excludedPageArray = new ArrayList<>();


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        excludedPageArray.add("/common/getAdcList");
        boolean isExcludedPage = false;
        for (String page : excludedPageArray) {//判断是否在过滤url之外
            if ((request).getPathInfo().equals(page)) {
                isExcludedPage = true;
                break;
            }
        }

//        UniResp<String> check = partnerService.check();
//        if (!isExcludedPage && check.getStatus() != 200) {
//            response.setCharacterEncoding("UTF-8");
//            response.setContentType("application/json; charset=utf-8");
//            response.setStatus(HttpServletResponse.SC_OK);
//            ObjectMapper mapper = new ObjectMapper();
//            response.getWriter().write(mapper.writeValueAsString(check));
//        } else {
//            filterChain.doFilter(request, response);
//        }
    }

    @Override
    public void afterPropertiesSet() {

    }
}
