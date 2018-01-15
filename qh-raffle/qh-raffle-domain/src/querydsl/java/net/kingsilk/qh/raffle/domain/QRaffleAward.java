package net.kingsilk.qh.raffle.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QRaffleAward is a Querydsl query type for RaffleAward
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QRaffleAward extends EntityPathBase<RaffleAward> {

    private static final long serialVersionUID = 539278955L;

    public static final QRaffleAward raffleAward = new QRaffleAward("raffleAward");

    public final QBase _super = new QBase(this);

    public final EnumPath<net.kingsilk.qh.raffle.core.AwardTypeEnum> awardType = createEnum("awardType", net.kingsilk.qh.raffle.core.AwardTypeEnum.class);

    public final NumberPath<Double> chance = createNumber("chance", Double.class);

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

    public final StringPath name = createString("name");

    public final NumberPath<Integer> num = createNumber("num", Integer.class);

    public final StringPath picture = createString("picture");

    public final StringPath prompt = createString("prompt");

    public final StringPath raffleAppId = createString("raffleAppId");

    public final StringPath raffleId = createString("raffleId");

    public final NumberPath<Integer> seqNum = createNumber("seqNum", Integer.class);

    public QRaffleAward(String variable) {
        super(RaffleAward.class, forVariable(variable));
    }

    public QRaffleAward(Path<? extends RaffleAward> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRaffleAward(PathMetadata metadata) {
        super(RaffleAward.class, metadata);
    }

}

