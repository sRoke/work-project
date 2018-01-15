package net.kingsilk.qh.agency.es.repo;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * Created by lit on 17/9/13.
 */
@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable>
        extends ElasticsearchRepository<T, ID> {
}
