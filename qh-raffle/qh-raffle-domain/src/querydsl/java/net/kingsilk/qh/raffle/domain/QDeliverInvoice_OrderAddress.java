package net.kingsilk.qh.raffle.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QDeliverInvoice_OrderAddress is a Querydsl query type for OrderAddress
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QDeliverInvoice_OrderAddress extends BeanPath<DeliverInvoice.OrderAddress> {

    private static final long serialVersionUID = -158564638L;

    public static final QDeliverInvoice_OrderAddress orderAddress = new QDeliverInvoice_OrderAddress("orderAddress");

    public final StringPath adc = createString("adc");

    public final StringPath phone = createString("phone");

    public final StringPath receiver = createString("receiver");

    public final StringPath street = createString("street");

    public QDeliverInvoice_OrderAddress(String variable) {
        super(DeliverInvoice.OrderAddress.class, forVariable(variable));
    }

    public QDeliverInvoice_OrderAddress(Path<? extends DeliverInvoice.OrderAddress> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDeliverInvoice_OrderAddress(PathMetadata metadata) {
        super(DeliverInvoice.OrderAddress.class, metadata);
    }

}

