package net.kingsilk.qh.raffle.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRaffleRecord is a Querydsl query type for RaffleRecord
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QRaffleRecord extends EntityPathBase<RaffleRecord> {

    private static final long serialVersionUID = 7907843L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRaffleRecord raffleRecord = new QRaffleRecord("raffleRecord");

    public final QBase _super = new QBase(this);

    public final BooleanPath accept = createBoolean("accept");

    public final QRaffleRecord_ShippingAddress addr;

    public final StringPath avatar = createString("avatar");

    public final StringPath awardId = createString("awardId");

    public final StringPath awardName = createString("awardName");

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.util.Date> dateCreated = _super.dateCreated;

    //inherited
    public final BooleanPath deleted = _super.deleted;

    public final EnumPath<net.kingsilk.qh.raffle.core.DrawTypeEnum> drawType = createEnum("drawType", net.kingsilk.qh.raffle.core.DrawTypeEnum.class);

    public final StringPath expressNo = createString("expressNo");

    public final BooleanPath firstShow = createBoolean("firstShow");

    public final EnumPath<net.kingsilk.qh.raffle.core.RecordHandleStatusEnum> handleStatus = createEnum("handleStatus", net.kingsilk.qh.raffle.core.RecordHandleStatusEnum.class);

    //inherited
    public final StringPath id = _super.id;

    public final BooleanPath isAccept = createBoolean("isAccept");

    public final BooleanPath isFirstShow = createBoolean("isFirstShow");

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath logisticsCompany = createString("logisticsCompany");

    public final StringPath logisticsId = createString("logisticsId");

    public final StringPath memo = createString("memo");

    public final StringPath nickName = createString("nickName");

    public final StringPath phone = createString("phone");

    public final StringPath raffleAppId = createString("raffleAppId");

    public final StringPath raffleId = createString("raffleId");

    public final StringPath sellerMemo = createString("sellerMemo");

    public final StringPath sn = createString("sn");

    public final StringPath userId = createString("userId");

    public final QWeiXinUserInfo weiXinUserInfoId;

    public QRaffleRecord(String variable) {
        this(RaffleRecord.class, forVariable(variable), INITS);
    }

    public QRaffleRecord(Path<? extends RaffleRecord> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRaffleRecord(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRaffleRecord(PathMetadata metadata, PathInits inits) {
        this(RaffleRecord.class, metadata, inits);
    }

    public QRaffleRecord(Class<? extends RaffleRecord> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.addr = inits.isInitialized("addr") ? new QRaffleRecord_ShippingAddress(forProperty("addr")) : null;
        this.weiXinUserInfoId = inits.isInitialized("weiXinUserInfoId") ? new QWeiXinUserInfo(forProperty("weiXinUserInfoId")) : null;
    }

}

