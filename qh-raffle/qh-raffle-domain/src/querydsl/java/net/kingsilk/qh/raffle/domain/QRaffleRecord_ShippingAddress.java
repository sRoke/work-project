package net.kingsilk.qh.raffle.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QRaffleRecord_ShippingAddress is a Querydsl query type for ShippingAddress
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QRaffleRecord_ShippingAddress extends BeanPath<RaffleRecord.ShippingAddress> {

    private static final long serialVersionUID = 1674186843L;

    public static final QRaffleRecord_ShippingAddress shippingAddress = new QRaffleRecord_ShippingAddress("shippingAddress");

    public final StringPath adc = createString("adc");

    public final StringPath memo = createString("memo");

    public final StringPath phone = createString("phone");

    public final StringPath receiver = createString("receiver");

    public final StringPath street = createString("street");

    public QRaffleRecord_ShippingAddress(String variable) {
        super(RaffleRecord.ShippingAddress.class, forVariable(variable));
    }

    public QRaffleRecord_ShippingAddress(Path<? extends RaffleRecord.ShippingAddress> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRaffleRecord_ShippingAddress(PathMetadata metadata) {
        super(RaffleRecord.ShippingAddress.class, metadata);
    }

}

