package net.kingsilk.qh.raffle.server.conf;

import net.kingsilk.qh.raffle.repo.BaseRepo;
import net.kingsilk.qh.raffle.repo.BaseRepoImpl;
import org.springframework.context.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.config.*;
import org.springframework.data.mongodb.repository.config.*;

import java.util.*;

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