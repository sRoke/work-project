package net.kingsilk.qh.vote.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QVoteWorks is a Querydsl query type for VoteWorks
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QVoteWorks extends EntityPathBase<VoteWorks> {

    private static final long serialVersionUID = -714535668L;

    public static final QVoteWorks voteWorks = new QVoteWorks("voteWorks");

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

    public final DateTimePath<java.util.Date> lastVoteTime = createDateTime("lastVoteTime", java.util.Date.class);

    public final StringPath name = createString("name");

    public final StringPath nickName = createString("nickName");

    public final StringPath openId = createString("openId");

    public final NumberPath<Integer> order = createNumber("order", Integer.class);

    public final StringPath phone = createString("phone");

    public final NumberPath<Integer> pv = createNumber("pv", Integer.class);

    public final NumberPath<Integer> rank = createNumber("rank", Integer.class);

    public final StringPath seq = createString("seq");

    public final DateTimePath<java.util.Date> signUpTime = createDateTime("signUpTime", java.util.Date.class);

    public final StringPath slogan = createString("slogan");

    public final EnumPath<net.kingsilk.qh.vote.core.vote.VoteWorksStatusEnum> status = createEnum("status", net.kingsilk.qh.vote.core.vote.VoteWorksStatusEnum.class);

    public final NumberPath<Integer> totalVotes = createNumber("totalVotes", Integer.class);

    public final StringPath userId = createString("userId");

    public final NumberPath<Integer> virtualVotes = createNumber("virtualVotes", Integer.class);

    public final StringPath voteActivityId = createString("voteActivityId");

    public final StringPath workerHeaderImgUrl = createString("workerHeaderImgUrl");

    public final StringPath worksImgUrl = createString("worksImgUrl");

    public QVoteWorks(String variable) {
        super(VoteWorks.class, forVariable(variable));
    }

    public QVoteWorks(Path<? extends VoteWorks> path) {
        super(path.getType(), path.getMetadata());
    }

    public QVoteWorks(PathMetadata metadata) {
        super(VoteWorks.class, metadata);
    }

}

