package net.kingsilk.qh.activity.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBargainRecord is a Querydsl query type for BargainRecord
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QBargainRecord extends EntityPathBase<BargainRecord> {

    private static final long serialVersionUID = 1555311954L;

    public static final QBargainRecord bargainRecord = new QBargainRecord("bargainRecord");

    public final QBase _super = new QBase(this);

    public final StringPath bargainId = createString("bargainId");

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.util.Date> dateCreated = _super.dateCreated;

    //inherited
    public final BooleanPath deleted = _super.deleted;

    public final NumberPath<Integer> finalPrice = createNumber("finalPrice", Integer.class);

    public final ListPath<String, StringPath> helpUsers = this.<String, StringPath>createList("helpUsers", String.class, StringPath.class, PathInits.DIRECT2);

    //inherited
    public final StringPath id = _super.id;

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath skuId = createString("skuId");

    public final EnumPath<net.kingsilk.qh.activity.core.bargain.BargainRecordStatusEnum> status = createEnum("status", net.kingsilk.qh.activity.core.bargain.BargainRecordStatusEnum.class);

    public final StringPath userId = createString("userId");

    public QBargainRecord(String variable) {
        super(BargainRecord.class, forVariable(variable));
    }

    public QBargainRecord(Path<? extends BargainRecord> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBargainRecord(PathMetadata metadata) {
        super(BargainRecord.class, metadata);
    }

}

