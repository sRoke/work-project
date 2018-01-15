package net.kingsilk.qh.raffle.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAdc is a Querydsl query type for Adc
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAdc extends EntityPathBase<Adc> {

    private static final long serialVersionUID = -988210506L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAdc adc = new QAdc("adc");

    public final StringPath id = createString("id");

    public final StringPath name = createString("name");

    public final StringPath no = createString("no");

    public final QAdc parent;

    public QAdc(String variable) {
        this(Adc.class, forVariable(variable), INITS);
    }

    public QAdc(Path<? extends Adc> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAdc(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAdc(PathMetadata metadata, PathInits inits) {
        this(Adc.class, metadata, inits);
    }

    public QAdc(Class<? extends Adc> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.parent = inits.isInitialized("parent") ? new QAdc(forProperty("parent"), inits.get("parent")) : null;
    }

}

