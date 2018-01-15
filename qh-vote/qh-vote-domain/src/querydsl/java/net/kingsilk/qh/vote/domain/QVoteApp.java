package net.kingsilk.qh.vote.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QVoteApp is a Querydsl query type for VoteApp
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QVoteApp extends EntityPathBase<VoteApp> {

    private static final long serialVersionUID = -1372830165L;

    public static final QVoteApp voteApp = new QVoteApp("voteApp");

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

    public QVoteApp(String variable) {
        super(VoteApp.class, forVariable(variable));
    }

    public QVoteApp(Path<? extends VoteApp> path) {
        super(path.getType(), path.getMetadata());
    }

    public QVoteApp(PathMetadata metadata) {
        super(VoteApp.class, metadata);
    }

}

