package net.kingsilk.qh.oauth.security;

import com.mongodb.*;
import net.kingsilk.qh.oauth.*;
import net.kingsilk.qh.oauth.domain.User;
import net.kingsilk.qh.oauth.domain.*;
import net.kingsilk.qh.oauth.repo.*;
import net.kingsilk.qh.oauth.service.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.web.authentication.*;
import org.springframework.security.web.util.matcher.*;
import org.springframework.stereotype.*;
import org.springframework.util.*;
import org.springframework.web.util.*;

import javax.servlet.http.*;
import java.io.*;
import java.util.*;


/**
 * 该filter处理的URL应该是用户微信登录、授权后的回调URL。
 *
 *
 * 微信扫码登录参考
 * https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&id=open1419316505
 * 微信-开放平台 -> 网站应用 -> 网站应用微信登录开发指南
 *
 * 微信公众号授权登录参考：
 * https://mp.weixin.qq.com/wiki
 * 微信-公众平台 -> 微信网页授权
 *
 * 只处理 GET /wxLogin
 *
 * @see org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
 */
@Component
@Deprecated
public class WxScanLoginFilter extends AbstractAuthenticationProcessingFilter {


    public static final String WX_USER_SCAN_URL = "https://open.weixin.qq.com/connect/qrconnect";

    /**
     * URL请求参数：是否检查登录状况，还是处理微信登录回调URL。
     * 仅仅检查参数是否存在，不检查参数值。
     */
    public static final String PARAM_CHECK = "redirectToWx";

//    /**
//     * URL请求参数：是否强制重新登录。
//     * 仅仅检查参数是否存在，不检查参数值。
//     */
//    static final String PARAM_NEW = "new"

    /**
     * URL请求参数：微信登录成功后自动创建用户。
     * 仅仅检查参数是否存在，不检查参数值。
     */
    public static final String PARAM_AUTO_CREATE_USER = "autoCreateUser";


    @Autowired
    private QhOAuthProperties props;

    @Autowired
    private WxService wxService;

    @Autowired
    private SecService secService;

    @Autowired
    private WxUserAtStateRepo wxUserAuthReqRepo;

    @Autowired
    private QhCommonWxApi wxApi;

    @Autowired
    private UserService userService;

    @Autowired
    private OAuth2RestTemplate qhCommonAdminCRT;

    public WxScanLoginFilter() {
        super(new AntPathRequestMatcher("/wxScanLogin", HttpMethod.GET.name()));
    }

    public String getWxUserScanUrl() {
        return getWxUserScanUrl(null);
    }

    /**
     * 每次都构建一个新的微信认证URL，并校验 state。
     * 适用于微信扫描登录。
     * @return
     */
    public String getWxUserScanUrl(MultiValueMap<String, String> extraParams) {

        QhOAuthProperties.QhOAuth.Wap wap = props.getQhOAuth().getWap();

        WxUserAtState req = new WxUserAtState();
        wxUserAuthReqRepo.save(req);

        UriComponentsBuilder redirectBuilder = UriComponentsBuilder.fromHttpUrl(wap.getWxScanLoginUrl())
                .queryParam("wxUserAuthReqId", req.getId());

        if (extraParams != null && !extraParams.isEmpty()) {
            redirectBuilder.queryParams(extraParams);
        }

        String wxRedirectUri = redirectBuilder.build()
                .encode()
                .toUri()
                .toString();

        UriComponentsBuilder ucBuilder = UriComponentsBuilder.fromHttpUrl(WX_USER_SCAN_URL)
                .queryParam("appid", wap.getWx().getAppId())
                .queryParam("redirect_uri", wxRedirectUri)
                .queryParam("response_type", "code")
//                .queryParam("state", req.getState()) // FIXME
                .fragment("wechat_redirect");

        List<String> scopes = wap.getWx().getScopes();
        if (scopes != null && !scopes.isEmpty()) {
            ucBuilder.queryParam("scope", String.join("，", scopes));
        }

        String wxUserAuthUrl = ucBuilder.build()
                .encode()
                .toUri()
                .toString();

        return wxUserAuthUrl;
    }

    /**
     * 将用户跳转到微信相应的授权URL。
     *
     * @param request
     * @param response
     */
    public void redirectToWx(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        Map<String, String[]> paramMap = request.getParameterMap();

        MultiValueMap<String, String> extraParams = new LinkedMultiValueMap<>();
        if (paramMap.containsKey(PARAM_AUTO_CREATE_USER)) {
            extraParams.set(PARAM_AUTO_CREATE_USER, "");
        }

        String redirectUrl = getWxUserScanUrl(extraParams);
        logger.debug("redirectUrl:" + redirectUrl);
        response.sendRedirect(redirectUrl);

    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws AuthenticationException, UsernameNotFoundException, IOException {

        QhOAuthProperties.QhOAuth.Wap wap = props.getQhOAuth().getWap();

        Map<String, String[]> paramMap = request.getParameterMap();
        logger.debug("==========================wxScanlogin==========" + paramMap);
        if (paramMap.containsKey(PARAM_CHECK)) {
            redirectToWx(request, response);
            return null;
        }

        // 处理微信回调
        String code = request.getParameter("code");
        String state = request.getParameter("state");
        String wxUserAuthReqId = request.getParameter("wxUserAuthReqId");

        Assert.notNull(code, "微信登录授权出错 - 缺少 code 参数");
        Assert.notNull(state, "微信登录授权出错 - 缺少 state 参数");
        Assert.notNull(wxUserAuthReqId, "微信登录授权出错 - 缺少 wxUserAuthReqId 参数");

        // 验证 state
        WxUserAtState req = wxUserAuthReqRepo.findOne(wxUserAuthReqId);
        Assert.notNull(req, "微信登录授权出错 - 缺少 wxUserAuthReqId 参数");
//        Assert.isTrue(state.equals(req.getState()), "微信登录授权出错 - state 不匹配");  // FIXME
        Assert.isTrue(req.getExpiredAt().after(new Date()), "微信登录授权出错 - state 已过期");


        DBObject selfUserAtJson = (DBObject) wxApi.selfUserAt(wap.getWx().getAppId(), code);

        String openId = (String) ((DBObject) selfUserAtJson.get("data")).get("openId");
        Assert.notNull(openId, "微信企业号登录授权出错 - 缺少openId参数");

        boolean autoCreateUser = paramMap.containsKey(PARAM_AUTO_CREATE_USER);


        User user = null;// FIXME
//        // 查找用户
//        User user = userService.findUserByWx(openId);
//
//        // 如果用户不存在，则处理自动创建用户
//        if (user == null && autoCreateUser) {
//            user = userService.registerByWx(openId);
//        }

        List<GrantedAuthority> authorities = new ArrayList<>();

        // 使用用户的id作为用户名
        UserDetails userDetails = org.springframework.security.core.userdetails.User.withUsername(user.getId())
                .password(SecService.NO_PASSWORD)
                .authorities(authorities)
                .build();

        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, SecService.NO_PASSWORD, authorities);

        return auth;
    }

    @Autowired
    public void confAuthenticationManager(AuthenticationManager authenticationManager) {
        this.setAuthenticationManager(authenticationManager);
    }

}
