package net.kingsilk.qh.raffle.security;

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

public class RaffleAppIdFilter extends OncePerRequestFilter
        implements InitializingBean {

    final String RAFFLEAPP_ID_REQUEST_ATTR = getClass().getName() + ".RAFFLEAPP_ID_REQUEST_ATTR";


    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        AntPathMatcher pathMatcher = new AntPathMatcher();

        String url = request.getPathInfo();

        String pattern = "/raffleApp/{raffleAppId}/**";
        Map<String, String> raffleAppMap = new LinkedHashMap<>();

        try {
            raffleAppMap = pathMatcher.extractUriTemplateVariables(pattern, url);
        } catch (Exception e) {
//            e.printStackTrace()
        }

        if (!raffleAppMap.isEmpty()) {

            RequestContextHolder.getRequestAttributes().setAttribute(
                    RAFFLEAPP_ID_REQUEST_ATTR,
                    raffleAppMap.get("raffleAppId"),
                    SCOPE_REQUEST
            );

        }
        filterChain.doFilter(request, response);

    }

    @Override
    public void afterPropertiesSet() {

    }

    /**
     * 获取bargain。
     *
     * @return bargainID
     */
    public String getRaffleAppId() {
        return (String) RequestContextHolder
                .getRequestAttributes()
                .getAttribute(RAFFLEAPP_ID_REQUEST_ATTR, SCOPE_REQUEST);
    }

}
