package net.kingsilk.qh.raffle.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRaffle is a Querydsl query type for Raffle
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QRaffle extends EntityPathBase<Raffle> {

    private static final long serialVersionUID = -1589221614L;

    public static final QRaffle raffle = new QRaffle("raffle");

    public final QBase _super = new QBase(this);

    public final ListPath<String, StringPath> awardIds = this.<String, StringPath>createList("awardIds", String.class, StringPath.class, PathInits.DIRECT2);

    public final StringPath batchId = createString("batchId");

    public final DateTimePath<java.util.Date> beginTime = createDateTime("beginTime", java.util.Date.class);

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final BooleanPath daily = createBoolean("daily");

    //inherited
    public final DateTimePath<java.util.Date> dateCreated = _super.dateCreated;

    //inherited
    public final BooleanPath deleted = _super.deleted;

    public final StringPath desp = createString("desp");

    public final StringPath dialImg = createString("dialImg");

    public final EnumPath<net.kingsilk.qh.raffle.core.DrawTypeEnum> drawType = createEnum("drawType", net.kingsilk.qh.raffle.core.DrawTypeEnum.class);

    public final DateTimePath<java.util.Date> endTime = createDateTime("endTime", java.util.Date.class);

    public final NumberPath<Integer> freeCount = createNumber("freeCount", Integer.class);

    //inherited
    public final StringPath id = _super.id;

    public final NumberPath<Integer> integralConsumption = createNumber("integralConsumption", Integer.class);

    public final BooleanPath isDaily = createBoolean("isDaily");

    public final NumberPath<Integer> joins = createNumber("joins", Integer.class);

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    public final ListPath<String, StringPath> lostPrompts = this.<String, StringPath>createList("lostPrompts", String.class, StringPath.class, PathInits.DIRECT2);

    public final NumberPath<Integer> lotteryCount = createNumber("lotteryCount", Integer.class);

    public final BooleanPath mustFollow = createBoolean("mustFollow");

    public final StringPath raffleAppId = createString("raffleAppId");

    public final EnumPath<net.kingsilk.qh.raffle.core.RaffleEntranceEnum> raffleEntrance = createEnum("raffleEntrance", net.kingsilk.qh.raffle.core.RaffleEntranceEnum.class);

    public final StringPath raffleName = createString("raffleName");

    public final EnumPath<net.kingsilk.qh.raffle.core.RaffleStatusEnum> raffleStatus = createEnum("raffleStatus", net.kingsilk.qh.raffle.core.RaffleStatusEnum.class);

    public final EnumPath<net.kingsilk.qh.raffle.core.RaffleTypeEnum> raffleType = createEnum("raffleType", net.kingsilk.qh.raffle.core.RaffleTypeEnum.class);

    public final StringPath rule = createString("rule");

    public final StringPath seq = createString("seq");

    public final NumberPath<Integer> shareCount = createNumber("shareCount", Integer.class);

    public final StringPath shareDesc = createString("shareDesc");

    public final StringPath shareImg = createString("shareImg");

    public final StringPath shareTitle = createString("shareTitle");

    public final StringPath shareUrl = createString("shareUrl");

    public QRaffle(String variable) {
        super(Raffle.class, forVariable(variable));
    }

    public QRaffle(Path<? extends Raffle> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRaffle(PathMetadata metadata) {
        super(Raffle.class, metadata);
    }

}

