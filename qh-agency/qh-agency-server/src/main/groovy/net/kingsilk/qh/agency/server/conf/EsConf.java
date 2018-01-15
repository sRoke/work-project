package net.kingsilk.qh.agency.server.conf;

import net.kingsilk.qh.agency.es.repo.BaseRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * Created by lit on 17/9/13.
 */
@Configuration
@EnableElasticsearchRepositories(basePackageClasses = BaseRepository.class)
public class EsConf {

}
