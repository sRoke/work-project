package net.kingsilk.qh.activity.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QBargainHelpUser is a Querydsl query type for BargainHelpUser
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QBargainHelpUser extends EntityPathBase<BargainHelpUser> {

    private static final long serialVersionUID = 15674221L;

    public static final QBargainHelpUser bargainHelpUser = new QBargainHelpUser("bargainHelpUser");

    public final QBase _super = new QBase(this);

    public final StringPath bargainId = createString("bargainId");

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.util.Date> dateCreated = _super.dateCreated;

    //inherited
    public final BooleanPath deleted = _super.deleted;

    public final NumberPath<Integer> helpPrice = createNumber("helpPrice", Integer.class);

    //inherited
    public final StringPath id = _super.id;

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath userId = createString("userId");

    public QBargainHelpUser(String variable) {
        super(BargainHelpUser.class, forVariable(variable));
    }

    public QBargainHelpUser(Path<? extends BargainHelpUser> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBargainHelpUser(PathMetadata metadata) {
        super(BargainHelpUser.class, metadata);
    }

}

