package net.kingsilk.qh.raffle.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWeiXinUserInfo is a Querydsl query type for WeiXinUserInfo
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QWeiXinUserInfo extends EntityPathBase<WeiXinUserInfo> {

    private static final long serialVersionUID = 1412581285L;

    public static final QWeiXinUserInfo weiXinUserInfo = new QWeiXinUserInfo("weiXinUserInfo");

    public final QBase _super = new QBase(this);

    public final StringPath appId = createString("appId");

    public final StringPath city = createString("city");

    public final StringPath country = createString("country");

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.util.Date> dateCreated = _super.dateCreated;

    //inherited
    public final BooleanPath deleted = _super.deleted;

    public final NumberPath<Integer> groupId = createNumber("groupId", Integer.class);

    public final StringPath headimgurl = createString("headimgurl");

    //inherited
    public final StringPath id = _super.id;

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    //inherited
    public final DateTimePath<java.util.Date> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath nickname = createString("nickname");

    public final StringPath openId = createString("openId");

    public final ListPath<String, StringPath> privilege = this.<String, StringPath>createList("privilege", String.class, StringPath.class, PathInits.DIRECT2);

    public final StringPath province = createString("province");

    public final StringPath remark = createString("remark");

    public final StringPath sex = createString("sex");

    public final BooleanPath subscribe = createBoolean("subscribe");

    public final DateTimePath<java.util.Date> subscribeTime = createDateTime("subscribeTime", java.util.Date.class);

    public final ListPath<Integer, NumberPath<Integer>> tagIdList = this.<Integer, NumberPath<Integer>>createList("tagIdList", Integer.class, NumberPath.class, PathInits.DIRECT2);

    public final StringPath unionId = createString("unionId");

    public QWeiXinUserInfo(String variable) {
        super(WeiXinUserInfo.class, forVariable(variable));
    }

    public QWeiXinUserInfo(Path<? extends WeiXinUserInfo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QWeiXinUserInfo(PathMetadata metadata) {
        super(WeiXinUserInfo.class, metadata);
    }

}

