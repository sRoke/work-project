package net.kingsilk.qh.vote.service.security;

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

public class VoteAppIdFilter extends OncePerRequestFilter
        implements InitializingBean {

    final String VOTEAPP_ID_REQUEST_ATTR = getClass().getName() + ".VOTEAPP_ID_REQUEST_ATTR";

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        AntPathMatcher pathMatcher = new AntPathMatcher();

        String url = request.getPathInfo();

        String pattern = "/voteApp/{voteAppId}/**";
        Map<String, String> voteAppMap = new LinkedHashMap<>();

        try {
            voteAppMap = pathMatcher.extractUriTemplateVariables(pattern, url);
        } catch (Exception e) {
//            e.printStackTrace()
        }

        if (!voteAppMap.isEmpty()) {

            RequestContextHolder.getRequestAttributes().setAttribute(
                    VOTEAPP_ID_REQUEST_ATTR,
                    voteAppMap.get("voteAppId"),
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
    public String getVoteAppId() {
        return (String) RequestContextHolder
                .getRequestAttributes()
                .getAttribute(VOTEAPP_ID_REQUEST_ATTR, SCOPE_REQUEST);
    }
}
