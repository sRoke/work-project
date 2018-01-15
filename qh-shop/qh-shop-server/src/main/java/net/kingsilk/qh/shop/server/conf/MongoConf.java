package net.kingsilk.qh.shop.server.conf;

import net.kingsilk.qh.shop.repo.BaseRepo;
import net.kingsilk.qh.shop.repo.BaseRepoImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Random;

/**
 *
 */
@Configuration
@EnableMongoRepositories(repositoryBaseClass = BaseRepoImpl.class, basePackageClasses = BaseRepo.class)
@EnableMongoAuditing
class MongoConf {

    @Bean
    MyAuditorAware myAuditorAware() {
        return new MyAuditorAware();
    }

    // SpringDataMongodbQuery
    // QuerydslRepositorySupport


    class MyAuditorAware implements AuditorAware<String> {

        private final String[] auditors = {"xxx", "yyy", "zzz"};
        private final Random random = new Random(System.currentTimeMillis());

        @Override
        public String getCurrentAuditor() {
            return auditors[Math.abs(random.nextInt() % auditors.length)];
        }
    }
}
