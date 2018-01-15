package net.kingsilk.qh.activity.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTask is a Querydsl query type for Task
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTask extends EntityPathBase<Task> {

    private static final long serialVersionUID = -1411732394L;

    public static final QTask task = new QTask("task");

    public final QBase _super = new QBase(this);

    public final StringPath activityId = createString("activityId");

    public final StringPath brandAppId = createString("brandAppId");

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.util.Date> dateCreated = _super.dateCreated;

    //inherited
    public final BooleanPath deleted = _super.deleted;

    public final StringPath desp = createString("desp");

    public final NumberPath<Integer> failCount = createNumber("failCount", Integer.class);

    public final StringPath fileId = createString("fileId");

    public final StringPath fileName = createString("fileName");

    public final StringPath forLockKey = createString("forLockKey");

    //inherited
    public final StringPath id = _super.id;

    public final BooleanPath isTemplate = createBoolean("isTemplate");

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath name = createString("name");

    public final ListPath<Object, SimplePath<Object>> sort = this.<Object, SimplePath<Object>>createList("sort", Object.class, SimplePath.class, PathInits.DIRECT2);

    public final NumberPath<Integer> successCount = createNumber("successCount", Integer.class);

    public final EnumPath<net.kingsilk.qh.activity.core.vote.TaskStatusEnum> taskStatus = createEnum("taskStatus", net.kingsilk.qh.activity.core.vote.TaskStatusEnum.class);

    public final EnumPath<net.kingsilk.qh.activity.core.vote.TaskTypeEnum> taskTypeEnum = createEnum("taskTypeEnum", net.kingsilk.qh.activity.core.vote.TaskTypeEnum.class);

    public final BooleanPath template = createBoolean("template");

    public final NumberPath<Long> time = createNumber("time", Long.class);

    public final StringPath userId = createString("userId");

    public final StringPath voteKeyword = createString("voteKeyword");

    public final StringPath workKeyword = createString("workKeyword");

    public final StringPath wxComAppId = createString("wxComAppId");

    public final StringPath wxMpAppId = createString("wxMpAppId");

    public QTask(String variable) {
        super(Task.class, forVariable(variable));
    }

    public QTask(Path<? extends Task> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTask(PathMetadata metadata) {
        super(Task.class, metadata);
    }

}

