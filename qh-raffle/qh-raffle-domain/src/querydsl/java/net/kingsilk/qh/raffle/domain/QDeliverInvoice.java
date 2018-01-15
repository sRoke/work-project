package net.kingsilk.qh.raffle.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDeliverInvoice is a Querydsl query type for DeliverInvoice
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QDeliverInvoice extends EntityPathBase<DeliverInvoice> {

    private static final long serialVersionUID = -491421070L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDeliverInvoice deliverInvoice = new QDeliverInvoice("deliverInvoice");

    public final QBase _super = new QBase(this);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.util.Date> dateCreated = _super.dateCreated;

    //inherited
    public final BooleanPath deleted = _super.deleted;

    public final StringPath deliverStaff = createString("deliverStaff");

    public final EnumPath<net.kingsilk.qh.raffle.core.DeliverStatusEnum> deliverStatus = createEnum("deliverStatus", net.kingsilk.qh.raffle.core.DeliverStatusEnum.class);

    //inherited
    public final StringPath id = _super.id;

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath logisticsesId = createString("logisticsesId");

    public final StringPath raffleAppId = createString("raffleAppId");

    public final StringPath raffleId = createString("raffleId");

    public final QDeliverInvoice_OrderAddress receiverAddr;

    public final StringPath recordId = createString("recordId");

    public final StringPath seq = createString("seq");

    public QDeliverInvoice(String variable) {
        this(DeliverInvoice.class, forVariable(variable), INITS);
    }

    public QDeliverInvoice(Path<? extends DeliverInvoice> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDeliverInvoice(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDeliverInvoice(PathMetadata metadata, PathInits inits) {
        this(DeliverInvoice.class, metadata, inits);
    }

    public QDeliverInvoice(Class<? extends DeliverInvoice> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.receiverAddr = inits.isInitialized("receiverAddr") ? new QDeliverInvoice_OrderAddress(forProperty("receiverAddr")) : null;
    }

}

