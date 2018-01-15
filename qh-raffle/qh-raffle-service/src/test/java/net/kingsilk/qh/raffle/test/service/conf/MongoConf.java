package net.kingsilk.qh.raffle.test.service.conf;

import net.kingsilk.qh.raffle.repo.BaseRepo;
import net.kingsilk.qh.raffle.repo.BaseRepoImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Random;

@Configuration
@EnableMongoRepositories(repositoryBaseClass = BaseRepoImpl.class, basePackageClasses = BaseRepo.class)
@EnableMongoAuditing
public class MongoConf {

    @Bean
    MyAuditorAware myAuditorAware() {
        return new MyAuditorAware();
    }

    private static final String[] auditors = {"xxx", "yyy", "zzz"};
    private static final Random random = new Random(System.currentTimeMillis());

    public class MyAuditorAware implements AuditorAware<String> {

        @Override
        public String getCurrentAuditor() {
            return auditors[Math.abs(random.nextInt()) % auditors.length];
        }
    }
}