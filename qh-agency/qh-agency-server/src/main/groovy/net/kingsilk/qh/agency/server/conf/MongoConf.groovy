package net.kingsilk.qh.agency.server.conf

import net.kingsilk.qh.agency.repo.BaseRepo
import net.kingsilk.qh.agency.repo.BaseRepoImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.data.mongodb.config.EnableMongoAuditing
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

/**
 *
 */
@Configuration
@EnableMongoRepositories(repositoryBaseClass = BaseRepoImpl, basePackageClasses = BaseRepo)
@EnableMongoAuditing
class MongoConf {

    @Bean
    MyAuditorAware myAuditorAware() {
        return new MyAuditorAware()
    }

    // SpringDataMongodbQuery
    // QuerydslRepositorySupport

    class MyAuditorAware implements AuditorAware<String> {

        private static final auditors = ["xxx", "yyy", "zzz"]
        private static final Random random = new Random(System.currentTimeMillis());

        @Override
        String getCurrentAuditor() {
            return auditors[random.nextInt() % auditors.size()]
        }
    }
}
