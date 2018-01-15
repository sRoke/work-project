package net.kingsilk.qh.oauth.core.wap.resource.s.oauth;

import net.kingsilk.qh.oauth.api.*;
import net.kingsilk.qh.oauth.api.s.oauth.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.*;
import org.springframework.security.oauth2.common.*;
import org.springframework.security.oauth2.common.exceptions.*;
import org.springframework.security.oauth2.common.util.*;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.approval.*;
import org.springframework.security.oauth2.provider.client.*;
import org.springframework.security.oauth2.provider.code.*;
import org.springframework.security.oauth2.provider.endpoint.*;
import org.springframework.security.oauth2.provider.implicit.*;
import org.springframework.security.oauth2.provider.password.*;
import org.springframework.security.oauth2.provider.refresh.*;
import org.springframework.security.oauth2.provider.request.*;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.stereotype.*;
import org.springframework.util.*;
import org.springframework.web.util.*;

import javax.servlet.http.*;
import javax.ws.rs.core.*;
import java.net.*;
import java.security.*;
import java.util.*;
import java.util.stream.*;

/**
 *
 * @see org.springframework.security.oauth2.provider.endpoint.AuthorizationEndpoint
 */
@Component
public class OAuthResource implements OAuthApi {


    //    @Autowired
//    private ConversionService cs;
    private Object implicitLock = new Object();


    @Context
    private HttpServletRequest request;

    @Autowired
    private ApprovalStore approvalStore;                            // MongoApprovalStore

    @Autowired
    private ClientDetailsService clientDetailsService;              // MongoClientDetailsService

    @Autowired
    private AuthorizationCodeServices authorizationCodeServices;    // MongoAuthorizationCodeServices

    @Autowired
    private OAuth2RequestFactory oAuth2RequestFactory;

    @Autowired
    private AuthorizationServerTokenServices tokenServices;

    @Autowired
    private AuthenticationManager authenticationManager;


    private RedirectResolver redirectResolver = new DefaultRedirectResolver();
    private OAuth2RequestValidator oauth2RequestValidator = new DefaultOAuth2RequestValidator();

    @Autowired
    private UserApprovalHandler userApprovalHandler;//= new DefaultUserApprovalHandler();


    @Autowired
    private TokenGranter tokenGranter;


    @Override
    public UniResp<AuthorizeResp> authorize(
            String responseType,
            String clientId,
            String redirectUri,
            String scope,
            String state
    ) {

        // 将所有参数作为 Map 存储
        Map<String, String> parameters = new HashMap<>();
        request.getParameterMap().entrySet().forEach(e -> parameters.put(e.getKey(), e.getValue()[0]));

        // Pull out the authorization request first, using the OAuth2RequestFactory. All further logic should
        // query off of the authorization request instead of referring back to the parameters map. The contents of the
        // parameters map will be stored without change in the AuthorizationRequest object once it is created.
        AuthorizationRequest authorizationRequest = oAuth2RequestFactory.createAuthorizationRequest(parameters);

        Set<String> responseTypes = authorizationRequest.getResponseTypes();

        if (!responseTypes.contains("token") && !responseTypes.contains("code")) {
            throw new UnsupportedResponseTypeException("Unsupported response types: " + responseTypes);
        }

        if (authorizationRequest.getClientId() == null) {
            throw new InvalidClientException("A client id must be provided");
        }

        Principal principal = request.getUserPrincipal();

        if (!(principal instanceof Authentication) || !((Authentication) principal).isAuthenticated()) {
            throw new InsufficientAuthenticationException(
                    "UserModel must be authenticated with Spring Security before authorization can be completed.");
        }

        ClientDetails client = clientDetailsService.loadClientByClientId(authorizationRequest.getClientId());

        // The resolved redirect URI is either the redirect_uri from the parameters or the one from
        // clientDetails. Either way we need to store it on the AuthorizationRequest.
        String redirectUriParameter = authorizationRequest.getRequestParameters().get(OAuth2Utils.REDIRECT_URI);
        String resolvedRedirect = redirectResolver.resolveRedirect(redirectUriParameter, client);
        if (!StringUtils.hasText(resolvedRedirect)) {
            throw new RedirectMismatchException(
                    "A redirectUri must be either supplied or preconfigured in the ClientDetails");
        }
        authorizationRequest.setRedirectUri(resolvedRedirect);

        // We intentionally only validate the parameters requested by the client (ignoring any data that may have
        // been added to the request by the manager).
        oauth2RequestValidator.validateScope(authorizationRequest, client);

        // Some systems may allow for approval decisions to be remembered or approved by default. Check for
        // such logic here, and set the approved flag on the authorization request accordingly.
        authorizationRequest = userApprovalHandler.checkForPreApproval(authorizationRequest,
                (Authentication) principal);

        // TODO: is this call necessary?
        boolean approved = userApprovalHandler.isApproved(authorizationRequest, (Authentication) principal);
        authorizationRequest.setApproved(approved);

        // 处理自动授权
        if (authorizationRequest.isApproved()) {

            // implicit 模式
            if (responseTypes.contains("token")) {

                AuthorizeResp authorizeResp = getImplicitGrantResponse(authorizationRequest);

                UniResp<AuthorizeResp> uniResp = new UniResp<>();
                uniResp.setStatus(200);
                uniResp.setData(authorizeResp);
                return uniResp;
            }

            // Authorization Code 授权方式
            if (responseTypes.contains("code")) {

                AuthorizeResp authorizeResp = getAuthorizationCodeResponse(authorizationRequest, (Authentication) principal);

                UniResp<AuthorizeResp> uniResp = new UniResp<>();
                uniResp.setStatus(200);
                uniResp.setData(authorizeResp);
                return uniResp;
            }
        }

        Assert.isTrue(false, "请检查配置，尚未实现手动授权");

        // 需要用户手动授权
        AuthorizeResp authorizeResp = getUserApprovalPageResponse(authorizationRequest, (Authentication) principal);

        UniResp<AuthorizeResp> uniResp = new UniResp<>();
        uniResp.setStatus(200);
        uniResp.setData(authorizeResp);
        return uniResp;
    }

    private AuthorizeResp getImplicitGrantResponse(AuthorizationRequest authorizationRequest) {

        TokenRequest tokenRequest = oAuth2RequestFactory.createTokenRequest(authorizationRequest, "implicit");
        OAuth2Request storedOAuth2Request = oAuth2RequestFactory.createOAuth2Request(authorizationRequest);
        OAuth2AccessToken accessToken = getAccessTokenForImplicitGrant(tokenRequest, storedOAuth2Request);
        if (accessToken == null) {
            throw new UnsupportedResponseTypeException("Unsupported response type: token");
        }

        AuthorizeResp authorizeResp = new AuthorizeResp();
        authorizeResp.setNeedUserAuthorize(false);
        authorizeResp.setCode(null);
        authorizeResp.setState(authorizationRequest.getState());
        authorizeResp.setAccessToken(accessToken.getValue());
        authorizeResp.setRedirectUri(appendAccessToken(authorizationRequest, accessToken));
        authorizeResp.setScopes(null);
        return authorizeResp;
    }

    private AuthorizeResp getAuthorizationCodeResponse(
            AuthorizationRequest authorizationRequest,
            Authentication authentication
    ) {


        String authorizationCode = generateCode(authorizationRequest, authentication);

        AuthorizeResp authorizeResp = new AuthorizeResp();
        authorizeResp.setNeedUserAuthorize(false);
        authorizeResp.setCode(authorizationCode);
        authorizeResp.setState(authorizationRequest.getState());
        authorizeResp.setAccessToken(null); // 该模式下是没有 access token 的

        String newRedirectUri = getSuccessfulRedirect(authorizationRequest, authorizationCode);
        authorizeResp.setRedirectUri(newRedirectUri);
        authorizeResp.setScopes(null);

        return authorizeResp;
    }

    private AuthorizeResp getUserApprovalPageResponse(
            AuthorizationRequest authorizationRequest,
            Authentication authentication
    ) {

        Collection<Approval> approvals = approvalStore.getApprovals(
                authentication.getName(),
                authorizationRequest.getClientId()
        );

        List<AuthorizeResp.AuthScope> authScopes = authorizationRequest.getScope()
                .stream()
                .map(scope -> {

                    AuthorizeResp.AuthScope authScope = new AuthorizeResp.AuthScope();
                    authScope.setScope(scope);

                    Optional<Approval> approvalOpt = approvals.stream()
                            .filter(approval -> approval.getScope().equals(scope))
                            .findFirst();
                    if (approvalOpt.isPresent()) {
                        Approval approval = approvalOpt.get();
                        authScope.setAuthed(Approval.ApprovalStatus.APPROVED.equals(approval.getStatus()));
                    }

                    authScope.setCanDisable(true); // TODO
                    authScope.setTitle(null); // TODO
                    return authScope;
                })
                .collect(Collectors.toList());


        AuthorizeResp authorizeResp = new AuthorizeResp();
        authorizeResp.setNeedUserAuthorize(true);
        authorizeResp.setCode(null);
        authorizeResp.setState(null);
        authorizeResp.setAccessToken(null);
        authorizeResp.setRedirectUri(null);
        authorizeResp.setScopes(authScopes);

        return authorizeResp;
    }


    // copy from AuthorizationEndpoint
    private String appendAccessToken(AuthorizationRequest authorizationRequest, OAuth2AccessToken accessToken) {

        Map<String, Object> vars = new LinkedHashMap<String, Object>();
        Map<String, String> keys = new HashMap<String, String>();

        if (accessToken == null) {
            throw new InvalidRequestException("An implicit grant could not be made");
        }

        vars.put("access_token", accessToken.getValue());
        vars.put("token_type", accessToken.getTokenType());
        String state = authorizationRequest.getState();

        if (state != null) {
            vars.put("state", state);
        }
        Date expiration = accessToken.getExpiration();
        if (expiration != null) {
            long expires_in = (expiration.getTime() - System.currentTimeMillis()) / 1000;
            vars.put("expires_in", expires_in);
        }
        String originalScope = authorizationRequest.getRequestParameters().get(OAuth2Utils.SCOPE);
        if (originalScope == null || !OAuth2Utils.parseParameterList(originalScope).equals(accessToken.getScope())) {
            vars.put("scope", OAuth2Utils.formatParameterList(accessToken.getScope()));
        }
        Map<String, Object> additionalInformation = accessToken.getAdditionalInformation();
        for (String key : additionalInformation.keySet()) {
            Object value = additionalInformation.get(key);
            if (value != null) {
                keys.put("extra_" + key, key);
                vars.put("extra_" + key, value);
            }
        }
        // Do not include the refresh token (even if there is one)
        return append(authorizationRequest.getRedirectUri(), vars, keys, true);
    }

    // copy from AuthorizationEndpoint
    private String append(String base, Map<String, ?> query, boolean fragment) {
        return append(base, query, null, fragment);
    }

    // copy from AuthorizationEndpoint
    private String append(String base, Map<String, ?> query, Map<String, String> keys, boolean fragment) {

        UriComponentsBuilder template = UriComponentsBuilder.newInstance();
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(base);
        URI redirectUri;
        try {
            // assume it's encoded to start with (if it came in over the wire)
            redirectUri = builder.build(true).toUri();
        } catch (Exception e) {
            // ... but allow client registrations to contain hard-coded non-encoded values
            redirectUri = builder.build().toUri();
            builder = UriComponentsBuilder.fromUri(redirectUri);
        }
        template.scheme(redirectUri.getScheme()).port(redirectUri.getPort()).host(redirectUri.getHost())
                .userInfo(redirectUri.getUserInfo()).path(redirectUri.getPath());

        if (fragment) {
            StringBuilder values = new StringBuilder();
            if (redirectUri.getFragment() != null) {
                String append = redirectUri.getFragment();
                values.append(append);
            }
            for (String key : query.keySet()) {
                if (values.length() > 0) {
                    values.append("&");
                }
                String name = key;
                if (keys != null && keys.containsKey(key)) {
                    name = keys.get(key);
                }
                values.append(name + "={" + key + "}");
            }
            if (values.length() > 0) {
                template.fragment(values.toString());
            }
            UriComponents encoded = template.build().expand(query).encode();
            builder.fragment(encoded.getFragment());
        } else {
            for (String key : query.keySet()) {
                String name = key;
                if (keys != null && keys.containsKey(key)) {
                    name = keys.get(key);
                }
                template.queryParam(name, "{" + key + "}");
            }
            template.fragment(redirectUri.getFragment());
            UriComponents encoded = template.build().expand(query).encode();
            builder.query(encoded.getQuery());
        }

        return builder.build().toUriString();

    }


    // copy from AuthorizationEndpoint
    private String generateCode(AuthorizationRequest authorizationRequest, Authentication authentication)
            throws AuthenticationException {

        try {

            OAuth2Request storedOAuth2Request = getOAuth2RequestFactory().createOAuth2Request(authorizationRequest);

            OAuth2Authentication combinedAuth = new OAuth2Authentication(storedOAuth2Request, authentication);
            String code = authorizationCodeServices.createAuthorizationCode(combinedAuth);

            return code;

        } catch (OAuth2Exception e) {

            if (authorizationRequest.getState() != null) {
                e.addAdditionalInformation("state", authorizationRequest.getState());
            }

            throw e;

        }
    }

    // copy from AuthorizationEndpoint
    private String getSuccessfulRedirect(AuthorizationRequest authorizationRequest, String authorizationCode) {

        if (authorizationCode == null) {
            throw new IllegalStateException("No authorization code found in the current request scope.");
        }

        Map<String, String> query = new LinkedHashMap<String, String>();
        query.put("code", authorizationCode);

        String state = authorizationRequest.getState();
        if (state != null) {
            query.put("state", state);
        }

        return append(authorizationRequest.getRedirectUri(), query, false);
    }


    protected OAuth2RequestFactory getOAuth2RequestFactory() {
        return oAuth2RequestFactory;
    }


    private OAuth2AccessToken getAccessTokenForImplicitGrant(
            TokenRequest tokenRequest,
            OAuth2Request storedOAuth2Request
    ) {
        OAuth2AccessToken accessToken = null;
        // These 1 method calls have to be atomic, otherwise the ImplicitGrantService can have a race condition where
        // one thread removes the token request before another has a chance to redeem it.
        synchronized (this.implicitLock) {
            accessToken = tokenGranter.grant("implicit",
                    new ImplicitTokenRequest(tokenRequest, storedOAuth2Request));
        }
        return accessToken;
    }

    // copy from AuthorizationEndpoint
    private List<TokenGranter> getDefaultTokenGranters() {


        List<TokenGranter> tokenGranters = new ArrayList<TokenGranter>();
        tokenGranters.add(new AuthorizationCodeTokenGranter(
                tokenServices,
                authorizationCodeServices,
                clientDetailsService,
                oAuth2RequestFactory
        ));
        tokenGranters.add(new RefreshTokenGranter(
                tokenServices,
                clientDetailsService,
                oAuth2RequestFactory
        ));

        ImplicitTokenGranter implicit = new ImplicitTokenGranter(
                tokenServices,
                clientDetailsService,
                oAuth2RequestFactory
        );

        tokenGranters.add(implicit);
        tokenGranters.add(new ClientCredentialsTokenGranter(
                tokenServices,
                clientDetailsService,
                oAuth2RequestFactory
        ));
        if (authenticationManager != null) {
            tokenGranters.add(new ResourceOwnerPasswordTokenGranter(
                    authenticationManager,
                    tokenServices,
                    clientDetailsService,
                    oAuth2RequestFactory
            ));
        }
        return tokenGranters;
    }

}
