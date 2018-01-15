package net.kingsilk.qh.oauth.core.wap.conf;

import net.kingsilk.qh.oauth.repo.*;
import org.springframework.context.annotation.*;
import org.springframework.data.mongodb.config.*;
import org.springframework.data.mongodb.repository.config.*;

/**
 *
 */
@Configuration()
@EnableMongoRepositories(repositoryBaseClass = BaseRepoImpl.class, basePackageClasses = BaseRepo.class)
@EnableMongoAuditing
public class MongoConf {

}
