package net.kingsilk.qh.raffle.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserTickets is a Querydsl query type for UserTickets
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUserTickets extends EntityPathBase<UserTickets> {

    private static final long serialVersionUID = 1767769266L;

    public static final QUserTickets userTickets = new QUserTickets("userTickets");

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

    public final NumberPath<Integer> shareTotalTicket = createNumber("shareTotalTicket", Integer.class);

    public final NumberPath<Integer> surplusTicket = createNumber("surplusTicket", Integer.class);

    public final NumberPath<Integer> usedTotalTicket = createNumber("usedTotalTicket", Integer.class);

    public final StringPath userId = createString("userId");

    public QUserTickets(String variable) {
        super(UserTickets.class, forVariable(variable));
    }

    public QUserTickets(Path<? extends UserTickets> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserTickets(PathMetadata metadata) {
        super(UserTickets.class, metadata);
    }

}

