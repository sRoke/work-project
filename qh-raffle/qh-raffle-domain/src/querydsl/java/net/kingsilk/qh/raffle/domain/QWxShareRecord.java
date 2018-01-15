package net.kingsilk.qh.raffle.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QWxShareRecord is a Querydsl query type for WxShareRecord
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QWxShareRecord extends EntityPathBase<WxShareRecord> {

    private static final long serialVersionUID = -134564347L;

    public static final QWxShareRecord wxShareRecord = new QWxShareRecord("wxShareRecord");

    public final QBase _super = new QBase(this);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.util.Date> dateCreated = _super.dateCreated;

    //inherited
    public final BooleanPath deleted = _super.deleted;

    //inherited
    public final StringPath id = _super.id;

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath raffleAppId = createString("raffleAppId");

    public final StringPath raffleId = createString("raffleId");

    public final StringPath shareUrl = createString("shareUrl");

    public final StringPath userId = createString("userId");

    public QWxShareRecord(String variable) {
        super(WxShareRecord.class, forVariable(variable));
    }

    public QWxShareRecord(Path<? extends WxShareRecord> path) {
        super(path.getType(), path.getMetadata());
    }

    public QWxShareRecord(PathMetadata metadata) {
        super(WxShareRecord.class, metadata);
    }

}

