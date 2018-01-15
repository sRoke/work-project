package net.kingsilk.qh.oauth.mongo;

import net.kingsilk.qh.oauth.domain.*;
import net.kingsilk.qh.oauth.repo.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.oauth2.common.util.*;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.code.*;
import org.springframework.stereotype.*;

import static com.querydsl.core.types.dsl.Expressions.*;

/**
 *
 */
@Service
public class MongoAuthorizationCodeServices extends RandomValueAuthorizationCodeServices {


    @Autowired
    private OAuthCodeRepo oauthCodeRepo;


    @Override
    protected void store(String code, OAuth2Authentication authentication) {
        // "insert into oauth_code (code, authentication) values (?, ?)";
        OAuthCode oauthCode = new OAuthCode();
        oauthCode.setCode(code);
        oauthCode.setAuthentication(SerializationUtils.serialize(authentication));
        oauthCodeRepo.save(oauthCode);
    }


    @Override
    protected OAuth2Authentication remove(String code) {

        // "select code, authentication from oauth_code where code = ?";
        // "delete from oauth_code where code = ?";


        OAuthCode oauthCode = oauthCodeRepo.findOne(allOf(
                QOAuthCode.oAuthCode.code.eq(code)
        ));
        if (oauthCode == null) {
            return null;
        }

        OAuth2Authentication authentication = SerializationUtils.deserialize(oauthCode.getAuthentication());
        oauthCodeRepo.delete(oauthCode);
        return authentication;
    }
}
