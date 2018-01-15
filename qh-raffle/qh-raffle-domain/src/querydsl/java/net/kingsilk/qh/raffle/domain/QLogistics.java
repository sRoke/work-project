package net.kingsilk.qh.raffle.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QLogistics is a Querydsl query type for Logistics
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QLogistics extends EntityPathBase<Logistics> {

    private static final long serialVersionUID = 788140969L;

    public static final QLogistics logistics = new QLogistics("logistics");

    public final QBase _super = new QBase(this);

    public final BooleanPath check = createBoolean("check");

    public final StringPath company = createString("company");

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.util.Date> dateCreated = _super.dateCreated;

    //inherited
    public final BooleanPath deleted = _super.deleted;

    public final StringPath expressNo = createString("expressNo");

    //inherited
    public final StringPath id = _super.id;

    public final BooleanPath isCheck = createBoolean("isCheck");

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    public final DateTimePath<java.util.Date> lastPushedTime = createDateTime("lastPushedTime", java.util.Date.class);

    public final StringPath lastSubscribeResult = createString("lastSubscribeResult");

    public final DateTimePath<java.util.Date> lastSubscribeTime = createDateTime("lastSubscribeTime", java.util.Date.class);

    public final NumberPath<Integer> recvPushedCount = createNumber("recvPushedCount", Integer.class);

    public final StringPath salt = createString("salt");

    public final EnumPath<net.kingsilk.qh.raffle.core.LogisticsStatusEnum> status = createEnum("status", net.kingsilk.qh.raffle.core.LogisticsStatusEnum.class);

    public final NumberPath<Integer> subscribeCount = createNumber("subscribeCount", Integer.class);

    public final BooleanPath subscribed = createBoolean("subscribed");

    public final StringPath trackInfo = createString("trackInfo");

    public QLogistics(String variable) {
        super(Logistics.class, forVariable(variable));
    }

    public QLogistics(Path<? extends Logistics> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLogistics(PathMetadata metadata) {
        super(Logistics.class, metadata);
    }

}

