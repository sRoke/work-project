package net.kingsilk.qh.raffle.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRaffleApp is a Querydsl query type for RaffleApp
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QRaffleApp extends EntityPathBase<RaffleApp> {

    private static final long serialVersionUID = -1076532817L;

    public static final QRaffleApp raffleApp = new QRaffleApp("raffleApp");

    public final QBase _super = new QBase(this);

    public final StringPath brandAppId = createString("brandAppId");

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

    public final ListPath<String, StringPath> payLog = this.<String, StringPath>createList("payLog", String.class, StringPath.class, PathInits.DIRECT2);

    public final StringPath shopId = createString("shopId");

    public final StringPath userId = createString("userId");

    public QRaffleApp(String variable) {
        super(RaffleApp.class, forVariable(variable));
    }

    public QRaffleApp(Path<? extends RaffleApp> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRaffleApp(PathMetadata metadata) {
        super(RaffleApp.class, metadata);
    }

}

