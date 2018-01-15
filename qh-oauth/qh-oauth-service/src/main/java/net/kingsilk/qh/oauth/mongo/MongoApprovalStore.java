package net.kingsilk.qh.oauth.mongo;

import net.kingsilk.qh.oauth.domain.*;
import net.kingsilk.qh.oauth.repo.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.oauth2.provider.approval.*;
import org.springframework.stereotype.*;

import java.util.*;
import java.util.stream.*;

import static com.querydsl.core.types.dsl.Expressions.*;

/**
 *
 */
@Service
public class MongoApprovalStore implements ApprovalStore {

    private Logger log = LoggerFactory.getLogger(getClass());

    private boolean handleRevocationsAsExpiry = false;

    @Autowired
    private OAuthApprovalsRepo oauthApprovalsRepo;

    public static Set<String> scopeStrToSet(String scope) {
        return new HashSet<>(Arrays.asList(scope.split(",")));
    }

    public static String scopeSetToStr(Set<String> scopes) {
        if (scopes == null || scopes.isEmpty()) {
            return null;
        }
        return String.join(",", scopes);
    }

    @Override
    public boolean addApprovals(Collection<Approval> approvals) {

        log.debug(String.format("adding approvals: [%s]", approvals));

        for (Approval approval : approvals) {

            OAuthApprovals a = oauthApprovalsRepo.findOne(allOf(
                    QOAuthApprovals.oAuthApprovals.userId.eq(approval.getUserId()),
                    QOAuthApprovals.oAuthApprovals.clientId.eq(approval.getClientId()),
                    QOAuthApprovals.oAuthApprovals.scope.eq(approval.getScope())
            ));

            // update oauth_approvals set expiresAt=?, status=?, lastModifiedAt=?
            //      where userId=? and clientId=? and scope=?"; TABLE_NAME);
            // insert into oauth_approvals ( expiresAt,status,lastModifiedAt,userId,clientId,scope )
            //      values (?,?,?,?,?,?)

            if (a == null) {
                a = new OAuthApprovals();
                a.setUserId(approval.getUserId());
                a.setClientId(approval.getClientId());
                a.setScope(approval.getScope());
            }
            a.setExpiresAt(approval.getExpiresAt());
            a.setStatus(
                    approval.getStatus() == null
                            ? Approval.ApprovalStatus.APPROVED.toString()
                            : approval.getStatus().toString()
            );
            a.setLastUpdatedAt(approval.getLastUpdatedAt());
            oauthApprovalsRepo.save(a);
        }

        return true;
    }

    @Override
    public boolean revokeApprovals(Collection<Approval> approvals) {
        log.debug(String.format("Revoking approvals: [%s]", approvals));


        // "update oauth_approvals set expiresAt = ? where userId=? and clientId=? and scope=?"
        // "delete from %oauth_approvals where userId=? and clientId=? and scope=?"

        for (final Approval approval : approvals) {
            OAuthApprovals a = oauthApprovalsRepo.findOne(allOf(
                    QOAuthApprovals.oAuthApprovals.userId.eq(approval.getUserId()),
                    QOAuthApprovals.oAuthApprovals.clientId.eq(approval.getClientId()),
                    QOAuthApprovals.oAuthApprovals.scope.eq(approval.getScope())
            ));

            if (handleRevocationsAsExpiry) {

                a.setExpiresAt(new Date());
                oauthApprovalsRepo.save(a);

            } else {
                oauthApprovalsRepo.delete(a);
            }

        }
        return true;
    }

    @Override
    public Collection<Approval> getApprovals(String userId, String clientId) {

        // "select expiresAt,status,lastModifiedAt,userId,clientId,scope from oauth_approvals where userId=? and clientId=? "

        Iterable<OAuthApprovals> approvalIt = oauthApprovalsRepo.findAll(allOf(
                QOAuthApprovals.oAuthApprovals.userId.eq(userId),
                QOAuthApprovals.oAuthApprovals.clientId.eq(clientId)
        ));

        return StreamSupport.stream(approvalIt.spliterator(), false)
                .map(a -> new Approval(
                        a.getUserId(),
                        a.getClientId(),
                        a.getScope(),
                        a.getExpiresAt(),
                        Approval.ApprovalStatus.valueOf(a.getStatus()),
                        a.getLastUpdatedAt()
                ))
                .collect(Collectors.toList());

    }


    public boolean isHandleRevocationsAsExpiry() {
        return handleRevocationsAsExpiry;
    }

    public void setHandleRevocationsAsExpiry(boolean handleRevocationsAsExpiry) {
        this.handleRevocationsAsExpiry = handleRevocationsAsExpiry;
    }

    public OAuthApprovalsRepo getOauthApprovalsRepo() {
        return oauthApprovalsRepo;
    }

    public void setOauthApprovalsRepo(OAuthApprovalsRepo oauthApprovalsRepo) {
        this.oauthApprovalsRepo = oauthApprovalsRepo;
    }
}
