package net.kingsilk.qh.oauth.security;

import com.mongodb.*;
import com.mongodb.util.*;
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
import org.springframework.security.web.authentication.*;
import org.springframework.security.web.util.matcher.*;
import org.springframework.stereotype.*;
import org.springframework.util.*;
import org.springframework.web.client.*;
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
 * 只处理 GET /wxQyhScanLogin
 *
 * @see org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
 */
@Component
@Deprecated
public class WxQyhScanLoginFilter extends AbstractAuthenticationProcessingFilter {

    public static final String WX_QYH_SCAN_URL = "https://qy.weixin.qq.com/cgi-bin/loginpage";

    public static final String GET_PROVIDER_TOKEN_URL = "https://qyapi.weixin.qq.com/cgi-bin/service/get_provider_token";

    public static final String GET_LOGIN_INFO_URL = "https://qyapi.weixin.qq.com/cgi-bin/service/get_login_info";

    public static final String OPENID_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/convert_to_openid";
    /**
     * URL请求参数：是否检查登录状况，还是处理微信登录回调URL。
     * 仅仅检查参数是否存在，不检查参数值。
     */
    public static final String PARAM_CHECK = "redirectToWx";

    /**
     * URL请求参数：是登录公众号登录授权，还是扫描二维码授权。
     * 仅仅检查参数是否存在，不检查参数值。
     */
    public static final String PARAM_SCAN = "scan";

    /**
     * URL请求参数：是登录微信企业号，还是登录微信公众号
     * 仅仅检查参数是否存在，不检查参数值。
     */
    public static final String PARAM_WX_QYH = "wx_qyh";

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
    private RestTemplate restTemplate;

    public WxQyhScanLoginFilter() {
        super(new AntPathRequestMatcher("/wxQyhScanLogin", HttpMethod.GET.name()));
    }

    public String getWxQyhScanUrl() {
        return getWxQyhScanUrl(null);
    }

    /**
     * 每次都构建一个新的微信认证URL，并校验 state。
     * 适用于微信扫描登录。
     * @return
     */
    public String getWxQyhScanUrl(MultiValueMap<String, String> extraParams) {

        QhOAuthProperties.QhOAuth.Wap wap = props.getQhOAuth().getWap();

        WxUserAtState req = new WxUserAtState();
        wxUserAuthReqRepo.save(req);

        UriComponentsBuilder redirectBuilder = UriComponentsBuilder.fromHttpUrl(wap.getWxQyhScanLoginUrl())
                .queryParam("wxUserAuthReqId", req.getId());

        if (!StringUtils.isEmpty(extraParams)) {
            redirectBuilder.queryParams(extraParams);
        }

        String wxRedirectUri = redirectBuilder.build()
                .encode()
                .toUri()
                .toString();
        UriComponentsBuilder ucBuilder = UriComponentsBuilder.fromHttpUrl(WX_QYH_SCAN_URL)
                .queryParam("corp_id", wap.getWxQyh().getCorpId())
                .queryParam("redirect_uri", wxRedirectUri)
                .queryParam("response_type", "code")
                .queryParam("scope", "snsapi_base")
                .fragment("wechat_redirect");
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

        String redirectUrl = getWxQyhScanUrl(extraParams);
        logger.debug("redirectUrl:" + redirectUrl);
        response.sendRedirect(redirectUrl);

    }

    public String userIdToOpenId(String userId) {

        QhOAuthProperties.QhOAuth.Wap wap = props.getQhOAuth().getWap();

        HttpHeaders reqHeader = new HttpHeaders();
        reqHeader.setAccept(Arrays.asList(MediaType.ALL));
        reqHeader.setContentType(MediaType.APPLICATION_JSON_UTF8);

        //企业号微信内部授权登录获取token
        String wxQyhAt = wxApi.selfAt(wap.getWxQyh().getCorpId());
        String uri = UriComponentsBuilder.fromHttpUrl(OPENID_URL)
                .queryParam("access_token", wxQyhAt)
                .build()
                .toUri()
                .toString();

        Map<String, String> reqBody = new LinkedHashMap<>();
        reqBody.put("userId", userId);


        HttpEntity<Map<String, String>> reqEntity = new HttpEntity<>(reqBody, reqHeader);


        ResponseEntity<String> respEntity = restTemplate.exchange(uri,
                HttpMethod.POST, reqEntity, String.class);

        // Object respJson = new JsonSlurper().parseText(respEntity.body)
        DBObject openIdJson = (DBObject) JSON.parse(respEntity.getBody());
        logger.debug("===========userId转openId登录======" + openIdJson);
        return (String) openIdJson.get("openid");
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws AuthenticationException, UsernameNotFoundException, IOException {


        QhOAuthProperties.QhOAuth.Wap wap = props.getQhOAuth().getWap();

        Map<String, String[]> paramMap = request.getParameterMap();
        logger.debug("==========================wxQyhScanlogin==========" + paramMap);
        if (paramMap.containsKey(PARAM_CHECK)) {
            redirectToWx(request, response);
            return null;
        }
        String auth_code = request.getParameter("auth_code");
        Assert.notNull(auth_code, "微信企业号登录授权出错 - 缺少 auth_code 参数");
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.ALL));
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        String uri = UriComponentsBuilder.fromHttpUrl(GET_PROVIDER_TOKEN_URL)
                .build()
                .toUri()
                .toString();


        Map<String, String> body = new LinkedHashMap<>();
        body.put("corpid", wap.getWxQyh().getCorpId());
        body.put("provider_secret", wap.getWxQyh().getProviderSecret());

        HttpEntity<Map<String, String>> reqEntity = new HttpEntity<>(body, headers);

        ResponseEntity<String> respEntity = restTemplate.exchange(uri,
                HttpMethod.POST, reqEntity, String.class);
        DBObject resJson = (DBObject) JSON.parse(respEntity.getBody());
        String provider_access_token = (String) resJson.get("provider_access_token");
        Assert.notNull(provider_access_token, "微信企业号登录授权出错 - provider_access_token为空");

        HttpHeaders infoHeaders = new HttpHeaders();
        infoHeaders.setAccept(Arrays.asList(MediaType.ALL));
        infoHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        String infoUri = UriComponentsBuilder.fromHttpUrl(GET_LOGIN_INFO_URL)
                .queryParam("access_token", provider_access_token)
                .build()
                .toUri()
                .toString();


        Map<String, String> codeBody = new LinkedHashMap<>();
        codeBody.put("auth_code", auth_code);
        HttpEntity<Map<String, String>> infoReqEntity = new HttpEntity<>(codeBody, infoHeaders);

        ResponseEntity<String> infoRespEntity = restTemplate.exchange(infoUri,
                HttpMethod.POST, infoReqEntity, String.class);
        DBObject respJson = (DBObject) JSON.parse(infoRespEntity.getBody());
        logger.debug("===========企业号扫码登录======" + respJson);
        String userId = null;
        if (respJson.get("user_info") != null) {//非系统管理员有userinfo
            userId = (String) respJson.get("user_info");
        } else {
            logger.debug("==============userId为空=");
        }
        Assert.notNull(userId, "微信企业号登录授权出错 - userId为空");
        String openId = userIdToOpenId(userId);
        Assert.notNull(openId, "企业号的Id为空");
        logger.debug("=============openId==" + openId);

        boolean autoCreateUser = paramMap.containsKey(PARAM_AUTO_CREATE_USER);


        User user = null;//FIXME
//        // 查找用户
//        User user = userService.findUserByWxQyhOpenId(openId);
//
//        // 如果用户不存在，则处理自动创建用户
//        if (user == null && autoCreateUser) {
//            user = userService.registerByWxQyh(openId);
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
