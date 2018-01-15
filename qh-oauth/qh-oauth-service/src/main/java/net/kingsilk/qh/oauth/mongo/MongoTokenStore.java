package net.kingsilk.qh.oauth.mongo;

import net.kingsilk.qh.oauth.domain.*;
import net.kingsilk.qh.oauth.repo.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.mongodb.core.*;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.security.oauth2.common.*;
import org.springframework.security.oauth2.common.util.SerializationUtils;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.stereotype.*;

import java.io.*;
import java.math.*;
import java.security.*;
import java.util.*;
import java.util.stream.*;

import static com.querydsl.core.types.dsl.Expressions.*;
import static org.springframework.data.mongodb.core.query.Criteria.*;

/**
 *
 */
@Service
public class MongoTokenStore implements TokenStore {

    private Logger log = LoggerFactory.getLogger(getClass());


    @Autowired
    private OAuthAccessTokenRepo oauthAccessTokenRepo;

    @Autowired
    private OAuthClientTokenRepo oauthClientTokenRepo;

    @Autowired
    private OAuthRefreshTokenRepo oauthRefreshTokenRepo;

    @Autowired
    private MongoTemplate mongoTemplate;

    private AuthenticationKeyGenerator authenticationKeyGenerator = new DefaultAuthenticationKeyGenerator();


    protected byte[] serializeAccessToken(OAuth2AccessToken token) {
        return SerializationUtils.serialize(token);
    }

    protected byte[] serializeRefreshToken(OAuth2RefreshToken token) {
        return SerializationUtils.serialize(token);
    }

    protected byte[] serializeAuthentication(OAuth2Authentication authentication) {
        return SerializationUtils.serialize(authentication);
    }

    protected OAuth2AccessToken deserializeAccessToken(byte[] token) {
        return SerializationUtils.deserialize(token);
    }

    protected OAuth2RefreshToken deserializeRefreshToken(byte[] token) {
        return SerializationUtils.deserialize(token);
    }

    protected OAuth2Authentication deserializeAuthentication(byte[] authentication) {
        return SerializationUtils.deserialize(authentication);
    }

    protected String extractTokenKey(String value) {
        if (value == null) {
            return null;
        }
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("MD5 algorithm not available.  Fatal (should be in the JDK).");
        }

        try {
            byte[] bytes = digest.digest(value.getBytes("UTF-8"));
            return String.format("%032x", new BigInteger(1, bytes));
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("UTF-8 encoding not available.  Fatal (should be in the JDK).");
        }
    }


    @Override
    public OAuth2Authentication readAuthentication(OAuth2AccessToken token) {
        return readAuthentication(token.getValue());
    }

    @Override
    public OAuth2Authentication readAuthentication(String token) {

        // "select token_id, authentication from oauth_access_token where token_id = ?";

        String tokenId = extractTokenKey(token);
        OAuthAccessToken oauthAt = oauthAccessTokenRepo.findOne(allOf(
                QOAuthAccessToken.oAuthAccessToken.tokenId.eq(tokenId)
        ));

        if (oauthAt == null) {
            log.info("Failed to find access token for token " + token);
            return null;
        }

        try {
            return deserializeAuthentication(oauthAt.getAuthentication());
        } catch (IllegalArgumentException e) {
            log.warn("Failed to deserialize authentication for " + token, e);
            removeAccessToken(token);
        }
        return null;

    }


    @Override
    public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {

        String refreshToken = null;

        if (token.getRefreshToken() != null) {
            refreshToken = token.getRefreshToken().getValue();
        }
        if (readAccessToken(token.getValue()) != null) {
            removeAccessToken(token.getValue());
        }

        // "insert into oauth_access_token (token_id, token, authentication_id,
        // user_name, client_id, authentication, refresh_token) values (?, ?, ?, ?, ?, ?, ?)";

        OAuthAccessToken at = new OAuthAccessToken();
        at.setTokenId(extractTokenKey(token.getValue()));
        at.setToken(serializeAccessToken(token));
        at.setAuthenticationId(authenticationKeyGenerator.extractKey(authentication));
        at.setUserName(authentication.isClientOnly()
                ? null
                : authentication.getName()
        );
        at.setClientId(authentication.getOAuth2Request().getClientId());
        at.setAuthentication(serializeAuthentication(authentication));
        at.setRefreshTokenId(extractTokenKey(refreshToken));

        oauthAccessTokenRepo.save(at);


    }

    @Override
    public OAuth2AccessToken readAccessToken(String tokenValue) {
        // "select token_id, token from oauth_access_token where token_id = ?";

        String tokenId = extractTokenKey(tokenValue);

        OAuthAccessToken oauthAt = oauthAccessTokenRepo.findOne(allOf(
                QOAuthAccessToken.oAuthAccessToken.tokenId.eq(tokenId)
        ));
        if (oauthAt == null) {
            log.info("Failed to find access token for token " + tokenValue);
            return null;
        }

        try {
            return deserializeAccessToken(oauthAt.getToken());
        } catch (IllegalArgumentException e) {
            log.warn("Failed to deserialize access token for " + tokenValue, e);
            removeAccessToken(tokenValue);
        }

        return null;
    }

    @Override
    public void removeAccessToken(OAuth2AccessToken token) {
        removeAccessToken(token.getValue());
    }

    public void removeAccessToken(String token) {
        //  "delete from oauth_access_token where token_id = ?";

        String tokenId = extractTokenKey(token);

        mongoTemplate.remove(
                new Query(where(QOAuthAccessToken.oAuthAccessToken.tokenId.getMetadata().getName()).is(tokenId)),
                OAuthAccessToken.class
        );
    }

    @Override
    public void storeRefreshToken(
            OAuth2RefreshToken refreshToken,
            OAuth2Authentication authentication
    ) {
        // "insert into oauth_refresh_token (token_id, token, authentication) values (?, ?, ?)";

        OAuthRefreshToken rt = new OAuthRefreshToken();
        rt.setTokenId(extractTokenKey(refreshToken.getValue()));
        rt.setToken(serializeRefreshToken(refreshToken));
        rt.setAuthentication(serializeAuthentication(authentication));

        oauthRefreshTokenRepo.save(rt);
    }

    @Override
    public OAuth2RefreshToken readRefreshToken(String tokenValue) {

        //  "select token_id, token from oauth_refresh_token where token_id = ?";

        String tokenId = extractTokenKey(tokenValue);

        OAuthRefreshToken rt = oauthRefreshTokenRepo.findOne(allOf(
                QOAuthRefreshToken.oAuthRefreshToken.tokenId.eq(tokenId)
        ));

        if (rt == null) {
            log.info("Failed to find refresh token for token " + tokenValue);
            return null;
        }

        try {
            return deserializeRefreshToken(rt.getToken());
        } catch (IllegalArgumentException e) {
            log.warn("Failed to deserialize refresh token for token " + tokenValue, e);
            removeRefreshToken(tokenValue);
        }
        return null;
    }

    @Override
    public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken token) {
        return readAuthenticationForRefreshToken(token.getValue());
    }

    public OAuth2Authentication readAuthenticationForRefreshToken(String value) {

        OAuthRefreshToken rt = oauthRefreshTokenRepo.findOne(allOf(
                QOAuthRefreshToken.oAuthRefreshToken.tokenId.eq(extractTokenKey(value))
        ));

        if (rt == null) {
            log.info("Failed to find access token for token " + value);
            return null;
        }

        try {
            return deserializeAuthentication(rt.getAuthentication());
        } catch (IllegalArgumentException e) {
            log.warn("Failed to deserialize access token for " + value, e);
            removeRefreshToken(value);
        }

        return null;
    }

    @Override
    public void removeRefreshToken(OAuth2RefreshToken token) {
        removeRefreshToken(token.getValue());
    }

    public void removeRefreshToken(String token) {
        String tokenId = extractTokenKey(token);

        mongoTemplate.remove(
                new Query(where(QOAuthRefreshToken.oAuthRefreshToken.tokenId.getMetadata().getName()).is(tokenId)),
                OAuthRefreshToken.class
        );
    }


    @Override
    public void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken refreshToken) {
        removeAccessTokenUsingRefreshToken(refreshToken.getValue());
    }


    public void removeAccessTokenUsingRefreshToken(String refreshToken) {

        // "delete from oauth_access_token where refresh_token = ?";
        String rtTokenId = extractTokenKey(refreshToken);

        mongoTemplate.remove(
                new Query(where(QOAuthAccessToken.oAuthAccessToken.refreshTokenId.getMetadata().getName()).is(rtTokenId)),
                OAuthAccessToken.class
        );

    }

    @Override
    public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {

        // "select token_id, token from oauth_access_token where authentication_id = ?";

        String authId = authenticationKeyGenerator.extractKey(authentication);

        OAuthAccessToken at = oauthAccessTokenRepo.findOne(allOf(
                QOAuthAccessToken.oAuthAccessToken.authenticationId.eq(authId)
        ));

        if (at == null) {
            log.debug("Failed to find access token for authentication " + authentication);
            return null;
        }

        OAuth2AccessToken accessToken;
        try {
            accessToken = deserializeAccessToken(at.getToken());

        } catch (IllegalArgumentException e) {
            log.error("Could not extract access token for authentication " + authentication, e);
            return null;
        }

        if (accessToken != null
                && !authId.equals(authenticationKeyGenerator.extractKey(readAuthentication(accessToken.getValue())))) {
            removeAccessToken(accessToken.getValue());
            // Keep the store consistent (maybe the same user is represented by this authentication but the details have
            // changed)
            storeAccessToken(accessToken, authentication);
        }
        return accessToken;
    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientIdAndUserName(
            String clientId,
            String userName
    ) {

        // "select token_id, token from oauth_access_token where user_name = ? and client_id = ?";

        Iterable<OAuthAccessToken> atIt = oauthAccessTokenRepo.findAll(allOf(
                QOAuthAccessToken.oAuthAccessToken.clientId.eq(clientId),
                QOAuthAccessToken.oAuthAccessToken.userName.eq(userName)
        ));

        if (!atIt.iterator().hasNext()) {
            log.info("Failed to find access token for clientId " + clientId + " and userName " + userName);
            return null;
        }

        return toList(atIt);
    }

    private List<OAuth2AccessToken> toList(Iterable<OAuthAccessToken> atIt) {
        return StreamSupport.stream(atIt.spliterator(), false)
                .map(at -> {
                    try {
                        return deserializeAccessToken(at.getToken());
                    } catch (IllegalArgumentException e) {
                        oauthAccessTokenRepo.delete(at);
                        return null;
                    }
                })
                .filter(t -> t != null)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<OAuth2AccessToken> findTokensByClientId(String clientId) {

        //  "select token_id, token from oauth_access_token where client_id = ?";

        Iterable<OAuthAccessToken> atIt = oauthAccessTokenRepo.findAll(allOf(
                QOAuthAccessToken.oAuthAccessToken.clientId.eq(clientId)
        ));

        if (!atIt.iterator().hasNext()) {
            log.info("Failed to find access token for clientId " + clientId);
            return null;
        }

        return toList(atIt);
    }
}
