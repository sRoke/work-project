package net.kingsilk.qh.oauth.security;

import net.kingsilk.qh.oauth.core.*;
import org.springframework.security.web.util.matcher.*;
import org.springframework.util.*;
import org.springframework.web.util.*;

import javax.servlet.http.*;

/**
 *
 */
@Deprecated
public class LoginTypeRequestMatcher implements RequestMatcher {


    /**
     * 选择登录方式的参数名。
     *
     * 默认使用 微信登录。候选值请参考 ： LoginTypeEnum
     *
     * @see net.kingsilk.qh.oauth.core.LoginTypeEnum
     */
    public static final String LOGIN_TYPE_PARAM = "loginType";


    private LoginTypeEnum matchedLoginType = null;


    public LoginTypeRequestMatcher(LoginTypeEnum matchedLoginType) {
        this.matchedLoginType = matchedLoginType;
    }

    public LoginTypeRequestMatcher() {

    }

    @Override
    public boolean matches(HttpServletRequest request) {

        if (matchedLoginType == null) {
            return false;
        }

        MultiValueMap<String, String> queryParams = UriComponentsBuilder.newInstance()
                .query(request.getQueryString())
                .build()
                .getQueryParams();

        if (!queryParams.containsKey(LOGIN_TYPE_PARAM)) {
            return false;
        }

        String loginTypeStr = queryParams.getFirst(LOGIN_TYPE_PARAM);

        if (StringUtils.hasText(loginTypeStr)) {
            return false;
        }

        LoginTypeEnum loginType = LoginTypeEnum.valueOf(loginTypeStr.toUpperCase());

        return matchedLoginType.equals(loginType);
    }

    public LoginTypeEnum getMatchedLoginType() {
        return matchedLoginType;
    }

    public void setMatchedLoginType(LoginTypeEnum matchedLoginType) {
        this.matchedLoginType = matchedLoginType;
    }
}
