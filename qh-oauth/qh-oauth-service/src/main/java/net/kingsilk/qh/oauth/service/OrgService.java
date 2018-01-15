package net.kingsilk.qh.oauth.service;


import com.querydsl.core.types.dsl.*;
import net.kingsilk.qh.oauth.domain.*;
import net.kingsilk.qh.oauth.repo.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.util.*;

@Service
public class OrgService {

    @Autowired
    private OrgRepo orgRepo;

    public Org checkExists(String userId, String orgId) {

        Org org = orgRepo.findOne(Expressions.allOf(
                QOrg.org.id.eq(orgId),
//                QOrg.org.userId.eq(userId),
                Expressions.anyOf(
                        QOrg.org.deleted.isNull(),
                        QOrg.org.deleted.eq(false)
                )
        ));
        Assert.isTrue(org != null, "组织不存在");
        return org;
    }

}
