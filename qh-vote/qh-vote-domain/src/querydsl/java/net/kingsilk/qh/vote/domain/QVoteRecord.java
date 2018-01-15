package net.kingsilk.qh.vote.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QVoteRecord is a Querydsl query type for VoteRecord
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QVoteRecord extends EntityPathBase<VoteRecord> {

    private static final long serialVersionUID = -828593145L;

    public static final QVoteRecord voteRecord = new QVoteRecord("voteRecord");

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

    public final StringPath openId = createString("openId");

    public final StringPath voteActivityId = createString("voteActivityId");

    public final StringPath voterHeaderImgUrl = createString("voterHeaderImgUrl");

    public final StringPath voterIp = createString("voterIp");

    public final StringPath voterNickName = createString("voterNickName");

    public final StringPath voterPhone = createString("voterPhone");

    public final StringPath voterUserId = createString("voterUserId");

    public final StringPath voteWorksId = createString("voteWorksId");

    public QVoteRecord(String variable) {
        super(VoteRecord.class, forVariable(variable));
    }

    public QVoteRecord(Path<? extends VoteRecord> path) {
        super(path.getType(), path.getMetadata());
    }

    public QVoteRecord(PathMetadata metadata) {
        super(VoteRecord.class, metadata);
    }

}

