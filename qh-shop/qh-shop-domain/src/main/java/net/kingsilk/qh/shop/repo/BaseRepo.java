package net.kingsilk.qh.shop.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface BaseRepo<T, ID extends Serializable>
        extends MongoRepository<T, ID>, QueryDslPredicateExecutor<T> {

}
