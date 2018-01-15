package net.kingsilk.qh.shop.repo;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.QueryDslMongoRepository;
import org.springframework.data.querydsl.EntityPathResolver;

import java.io.Serializable;

/**
 *
 */
public class BaseRepoImpl <T, ID extends Serializable>
        extends QueryDslMongoRepository<T, ID>
        implements MongoRepository<T, ID>,
        BaseRepo<T, ID> {

    public BaseRepoImpl(MongoEntityInformation<T, ID> entityInformation, MongoOperations mongoOperations) {
        super(entityInformation, mongoOperations);
    }

    public BaseRepoImpl(MongoEntityInformation<T, ID> entityInformation, MongoOperations mongoOperations, EntityPathResolver resolver) {
        super(entityInformation, mongoOperations, resolver);
    }
}
