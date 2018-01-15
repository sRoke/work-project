package net.kingsilk.qh.activity.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QVoteActivity is a Querydsl query type for VoteActivity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QVoteActivity extends EntityPathBase<VoteActivity> {

    private static final long serialVersionUID = 1260795370L;

    public static final QVoteActivity voteActivity = new QVoteActivity("voteActivity");

    public final QBase _super = new QBase(this);

    public final StringPath activityName = createString("activityName");

    public final StringPath brandAppId = createString("brandAppId");

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.util.Date> dateCreated = _super.dateCreated;

    //inherited
    public final BooleanPath deleted = _super.deleted;

    public final StringPath desp = createString("desp");

    public final BooleanPath forceFollow = createBoolean("forceFollow");

    public final BooleanPath forcePhone = createBoolean("forcePhone");

    //inherited
    public final StringPath id = _super.id;

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    public final NumberPath<Integer> maxTicketPerDay = createNumber("maxTicketPerDay", Integer.class);

    public final NumberPath<Integer> maxVotePerDay = createNumber("maxVotePerDay", Integer.class);

    public final BooleanPath mustCheck = createBoolean("mustCheck");

    public final StringPath primaryImgUrl = createString("primaryImgUrl");

    public final StringPath rule = createString("rule");

    public final StringPath shareContent = createString("shareContent");

    public final StringPath shareTitle = createString("shareTitle");

    public final DateTimePath<java.util.Date> signUpEndTime = createDateTime("signUpEndTime", java.util.Date.class);

    public final DateTimePath<java.util.Date> signUpStartTime = createDateTime("signUpStartTime", java.util.Date.class);

    public final NumberPath<Integer> totalJoinPeople = createNumber("totalJoinPeople", Integer.class);

    public final NumberPath<Integer> totalVisit = createNumber("totalVisit", Integer.class);

    public final NumberPath<Integer> totalVote = createNumber("totalVote", Integer.class);

    public final NumberPath<Integer> totalVoteCount = createNumber("totalVoteCount", Integer.class);

    public final DateTimePath<java.util.Date> voteEndTime = createDateTime("voteEndTime", java.util.Date.class);

    public final NumberPath<Integer> votePeoplePerDay = createNumber("votePeoplePerDay", Integer.class);

    public final DateTimePath<java.util.Date> voteStartTime = createDateTime("voteStartTime", java.util.Date.class);

    public final EnumPath<net.kingsilk.qh.activity.core.vote.VoteStatusEnum> voteStatusEnum = createEnum("voteStatusEnum", net.kingsilk.qh.activity.core.vote.VoteStatusEnum.class);

    public final StringPath wordsOfThanks = createString("wordsOfThanks");

    public QVoteActivity(String variable) {
        super(VoteActivity.class, forVariable(variable));
    }

    public QVoteActivity(Path<? extends VoteActivity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QVoteActivity(PathMetadata metadata) {
        super(VoteActivity.class, metadata);
    }

}

