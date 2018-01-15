package net.kingsilk.qh.oauth.api.s.oauth;

import io.swagger.annotations.*;

import java.util.*;

/**
 *
 */
@ApiModel
public class AuthorizeResp {


    @ApiModelProperty("是否需要用户明确手动授权")
    private boolean needUserAuthorize;

    @ApiModelProperty("code")
    private String code;

    @ApiModelProperty("state")
    private String state;

    @ApiModelProperty("accessToken")
    private String accessToken;

    @ApiModelProperty("填充参数之后的 redirectUri")
    private String redirectUri;

    @ApiModelProperty("需要用户授权的 scope 列表")
    private List<AuthScope> scopes;

    @ApiModel
    public static class AuthScope {

        @ApiModelProperty("scope")
        private String scope;

        @ApiModelProperty("是否已经授权")
        private boolean authed;

        @ApiModelProperty("能否取消授权")
        private boolean canDisable;

        @ApiModelProperty("对用户友好的中文提示")
        private String title;

        public String getScope() {
            return scope;
        }

        public void setScope(String scope) {
            this.scope = scope;
        }

        public boolean isAuthed() {
            return authed;
        }

        public void setAuthed(boolean authed) {
            this.authed = authed;
        }

        public boolean isCanDisable() {
            return canDisable;
        }

        public void setCanDisable(boolean canDisable) {
            this.canDisable = canDisable;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public boolean isNeedUserAuthorize() {
        return needUserAuthorize;
    }

    public void setNeedUserAuthorize(boolean needUserAuthorize) {
        this.needUserAuthorize = needUserAuthorize;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public void setRedirectUri(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public List<AuthScope> getScopes() {
        return scopes;
    }

    public void setScopes(List<AuthScope> scopes) {
        this.scopes = scopes;
    }
}
