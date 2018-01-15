package net.kingsilk.qh.raffle.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QBase is a Querydsl query type for Base
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QBase extends EntityPathBase<Base> {

    private static final long serialVersionUID = -569727109L;

    public static final QBase base = new QBase("base");

    public final StringPath createdBy = createString("createdBy");

    public final DateTimePath<java.util.Date> dateCreated = createDateTime("dateCreated", java.util.Date.class);

    public final BooleanPath deleted = createBoolean("deleted");

    public final StringPath id = createString("id");

    public final StringPath lastModifiedBy = createString("lastModifiedBy");

    public final DateTimePath<java.util.Date> lastModifiedDate = createDateTime("lastModifiedDate", java.util.Date.class);

    public QBase(String variable) {
        super(Base.class, forVariable(variable));
    }

    public QBase(Path<? extends Base> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBase(PathMetadata metadata) {
        super(Base.class, metadata);
    }

}

