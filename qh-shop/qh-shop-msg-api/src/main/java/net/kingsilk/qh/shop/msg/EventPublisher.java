package net.kingsilk.qh.shop.msg;

/**
 * Created by zll on 24/08/2017.
 */
public interface EventPublisher {

//    void publish(
//            String exchange,
//            Object event
//    );

    void publish(
            Object event
    );
}
