package net.kingsilk.qh.oauth.mongo;

import net.kingsilk.qh.oauth.domain.*;
import net.kingsilk.qh.oauth.repo.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.security.core.authority.*;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.client.*;
import org.springframework.stereotype.*;

import java.util.*;
import java.util.stream.*;

import static com.querydsl.core.types.dsl.Expressions.*;

/**
 * 使用 MongoDb 存储，并实现一个 ClientDetailsService、ClientRegistrationService
 *
 * @see org.springframework.security.oauth2.provider.client.JdbcClientDetailsService
 */
@Service
public class MongoClientDetailsService
        implements ClientDetailsService, ClientRegistrationService {

    @Autowired
    public OAuthClientDetailsRepo oauthClientDetailsRepo;


    private ClientDetails toClientDetails(OAuthClientDetails ocd) {
        BaseClientDetails cd = new BaseClientDetails();
        cd.setClientId(ocd.getClientId());
        cd.setClientSecret(ocd.getClientSecret());
        cd.setScope(ocd.getScope());
        cd.setResourceIds(ocd.getResourceIds());

        if (ocd.getAuthorizedGrantTypes() != null) {
            cd.setAuthorizedGrantTypes(ocd.getAuthorizedGrantTypes());
        }

        cd.setRegisteredRedirectUri(ocd.getRegisteredRedirectUris());

        if (ocd.getAutoApproveScopes() != null) {
            cd.setAutoApproveScopes(ocd.getAutoApproveScopes());
        }

        if (ocd.getAuthorities() != null) {

            cd.setAuthorities(
                    AuthorityUtils.createAuthorityList(ocd.getAuthorities().toArray(new String[0])));
        }

        cd.setAccessTokenValiditySeconds(ocd.getAccessTokenValidity());
        cd.setRefreshTokenValiditySeconds(ocd.getRefreshTokenValidity());

        if (ocd.getAdditionalInformation() != null) {
            cd.setAdditionalInformation(ocd.getAdditionalInformation());
        }
        return cd;
    }

    private Set<String> getAutoApproveScopes(ClientDetails clientDetails) {
        if (clientDetails.isAutoApprove("true")) {
            return Collections.singleton("true");  // all scopes autoapproved
        }
        Set<String> scopes = new HashSet<String>();
        for (String scope : clientDetails.getScope()) {
            if (clientDetails.isAutoApprove(scope)) {
                scopes.add(scope);
            }
        }
        return scopes;
    }

    // resource_ids, scope, "
    //+ "authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, "
    //+ "refresh_token_validity, additional_information, autoapprove
    private void bind(ClientDetails from, OAuthClientDetails to) {

        to.setClientId(from.getClientId());
        to.setResourceIds(from.getResourceIds());
        to.setClientSecret(from.getClientSecret());
        to.setScope(from.getScope());
        to.setAuthorizedGrantTypes(from.getAuthorizedGrantTypes());
        to.setRegisteredRedirectUris(from.getRegisteredRedirectUri());

        if (from.getAuthorities() != null) {
            Set<String> authorities = from.getAuthorities().stream()
                    .map(ga -> ga.getAuthority())
                    .collect(Collectors.toSet());
            to.setAuthorities(authorities);
        }

        to.setAccessTokenValidity(from.getAccessTokenValiditySeconds());
        to.setRefreshTokenValidity(from.getRefreshTokenValiditySeconds());
        to.setAutoApproveScopes(getAutoApproveScopes(from));
        to.setAdditionalInformation(from.getAdditionalInformation());

    }


    @Override
    public ClientDetails loadClientByClientId(
            String clientId
    ) throws ClientRegistrationException {

        OAuthClientDetails oauthClientDetails = oauthClientDetailsRepo.findOne(allOf(
                QOAuthClientDetails.oAuthClientDetails.clientId.eq(clientId)
        ));

        if (oauthClientDetails == null) {
            throw new NoSuchClientException("No client with requested id: " + clientId);
        }
        return toClientDetails(oauthClientDetails);
    }

    @Override
    public void addClientDetails(
            ClientDetails clientDetails
    ) throws ClientAlreadyExistsException {
        OAuthClientDetails oauthClientDetails = oauthClientDetailsRepo.findOne(allOf(
                QOAuthClientDetails.oAuthClientDetails.clientId.eq(clientDetails.getClientId())
        ));
        if (oauthClientDetails != null) {
            throw new ClientAlreadyExistsException("Client already exists: " + clientDetails.getClientId());
        }
        oauthClientDetails = new OAuthClientDetails();

        bind(clientDetails, oauthClientDetails);

        oauthClientDetailsRepo.save(oauthClientDetails);
    }

    @Override
    public void updateClientDetails(
            ClientDetails clientDetails
    ) throws NoSuchClientException {
        OAuthClientDetails oauthClientDetails = oauthClientDetailsRepo.findOne(allOf(
                QOAuthClientDetails.oAuthClientDetails.clientId.eq(clientDetails.getClientId())
        ));
        if (oauthClientDetails == null) {
            throw new NoSuchClientException("No client found with id = " + clientDetails.getClientId());
        }

        bind(clientDetails, oauthClientDetails);

        oauthClientDetailsRepo.save(oauthClientDetails);
    }

    @Override
    public void updateClientSecret(String clientId, String secret) throws NoSuchClientException {
        OAuthClientDetails oauthClientDetails = oauthClientDetailsRepo.findOne(allOf(
                QOAuthClientDetails.oAuthClientDetails.clientId.eq(clientId)
        ));
        if (oauthClientDetails == null) {
            throw new NoSuchClientException("No client found with id = " + clientId);
        }

        oauthClientDetails.setClientSecret(secret);

        oauthClientDetailsRepo.save(oauthClientDetails);
    }

    @Override
    public void removeClientDetails(String clientId) throws NoSuchClientException {
        OAuthClientDetails oauthClientDetails = oauthClientDetailsRepo.findOne(allOf(
                QOAuthClientDetails.oAuthClientDetails.clientId.eq(clientId)
        ));

        if (oauthClientDetails == null) {
            throw new NoSuchClientException("No client found with id = " + clientId);
        }

        oauthClientDetailsRepo.delete(oauthClientDetails);
    }

    @Override
    public List<ClientDetails> listClientDetails() {
        return oauthClientDetailsRepo.findAll().stream()
                .map(ocd -> toClientDetails(ocd))
                .collect(Collectors.toList());
    }

}
