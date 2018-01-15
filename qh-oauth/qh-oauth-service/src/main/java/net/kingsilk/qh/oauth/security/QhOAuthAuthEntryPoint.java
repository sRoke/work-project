package net.kingsilk.qh.oauth.security;

import net.kingsilk.qh.oauth.*;
import net.kingsilk.qh.oauth.core.*;
import org.apache.commons.logging.*;
import org.springframework.security.core.*;
import org.springframework.security.web.authentication.*;
import org.springframework.util.*;
import org.springframework.web.util.*;

import javax.servlet.http.*;
import java.util.*;

/**
 * 根据 特定请求参数，将用户302到 /wxLogin， /phoneLogin, /login 这几个URL
 *
 * @see LoginUrlAuthenticationEntryPoint
 */
@Deprecated
public class QhOAuthAuthEntryPoint extends LoginUrlAuthenticationEntryPoint {


    private static final Log log = LogFactory.getLog(QhOAuthAuthEntryPoint.class);

    /**
     * 选择登录方式的参数名。
     *
     * 默认使用 微信登录。候选值请参考 ： LoginTypeEnum
     *
     * @see net.kingsilk.qh.oauth.core.LoginTypeEnum
     */
    public static final String LOGIN_TYPE_PARAM = "loginType";

    private QhOAuthProperties props;

    public QhOAuthAuthEntryPoint() {
        super("/login");
    }

    public QhOAuthAuthEntryPoint(QhOAuthProperties props) {
        super(props.getQhOAuth().getWap().getPasswordLoginUrl());
        this.props = props;
    }

    public QhOAuthAuthEntryPoint(String loginFormUrl) {
        super(loginFormUrl);
    }

//    @Override
//    protected String buildRedirectUrlToLoginPage(
//            HttpServletRequest request,
//            HttpServletResponse response,
//            AuthenticationException authException
//    ) {
//        String loginUrl = super.buildRedirectUrlToLoginPage()
//
//
//    }

    @Override
    protected String determineUrlToUseForThisRequest(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception
    ) {

        QhOAuthProperties.QhOAuth.Wap wap = props.getQhOAuth().getWap();

        Map<String, String[]> paramMap = request.getParameterMap();
        String loginTypeStr = request.getParameter(LOGIN_TYPE_PARAM);
        LoginTypeEnum loginType = null;
        if (!StringUtils.isEmpty(loginTypeStr)) {
            loginType = LoginTypeEnum.valueOf(loginTypeStr.toUpperCase());
        }

        if (loginType == null) {
            loginType = LoginTypeEnum.WX;
        }


        UriComponentsBuilder builder;
        switch (loginType) {

            case PHONE:
                return wap.getPhoneLoginUrl();

            case PASSWORD:
                return wap.getPasswordLoginUrl();

            case WX_SCAN:

                builder = UriComponentsBuilder.fromUriString(wap.getWxScanLoginUrl());

                builder.queryParam(WxLoginFilter.PARAM_CHECK, "");
                if (paramMap.containsKey(WxLoginFilter.PARAM_AUTO_CREATE_USER)) {
                    builder.queryParam(WxLoginFilter.PARAM_AUTO_CREATE_USER, "");
                }
                return builder.build().toString();

            case WX_QYH:

                builder = UriComponentsBuilder.fromUriString(wap.getWxQyhLoginUrl());
                builder.queryParam(WxQyhLoginFilter.PARAM_CHECK, "");
                if (paramMap.containsKey(WxLoginFilter.PARAM_AUTO_CREATE_USER)) {
                    builder.queryParam(WxLoginFilter.PARAM_AUTO_CREATE_USER, "");
                }
                return builder.build().toString();

            case WX_QYH_SCAN:

                builder = UriComponentsBuilder.fromUriString(wap.getWxQyhScanLoginUrl());
                builder.queryParam(WxQyhScanLoginFilter.PARAM_CHECK, "");
                if (paramMap.containsKey(WxLoginFilter.PARAM_AUTO_CREATE_USER)) {
                    builder.queryParam(WxLoginFilter.PARAM_AUTO_CREATE_USER, "");
                }
                return builder.build().toString();

            // LoginTypeEnum.WX
            default:
                builder = UriComponentsBuilder.fromUriString(wap.getWxLoginUrl());
                builder.queryParam(WxLoginFilter.PARAM_CHECK, "");
                if (paramMap.containsKey(WxLoginFilter.PARAM_AUTO_CREATE_USER)) {
                    builder.queryParam(WxLoginFilter.PARAM_AUTO_CREATE_USER, "");
                }
                return builder.build().toString();
        }
    }


}
