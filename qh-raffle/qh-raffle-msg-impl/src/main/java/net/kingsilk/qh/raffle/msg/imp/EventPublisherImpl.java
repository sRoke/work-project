package net.kingsilk.qh.raffle.msg.imp;




import msg.EventPublishEnum;
import msg.EventPublisher;
import net.kingsilk.qh.raffle.QhRaffleProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public class EventPublisherImpl implements EventPublisher {

    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private QhRaffleProperties qhRaffleProperties;


//    public void publish(
//            String exchange,
//            String routingKey,
//            Object event
//    ) {
//        // 本地（当前JVM）内的消息
//        applicationContext.publishEvent(event);
//
//        // MQ 的消息
//        amqpTemplate.convertAndSend(exchange, routingKey, event);
//
//    }


//    @Override
//    public void publish(
//            String exchange,
//            Object event
//    ) {
//
//        // 本地（当前JVM）内的消息
//        applicationContext.publishEvent(event);
//
//        // MQ 的消息
//        amqpTemplate.convertAndSend(exchange, event);
//
//    }

    @Override
    public void publish(
            Object event
    ) {

        try {


            EventPublishEnum publishTo = qhRaffleProperties.getMsg().getPublishTo();

            // 本地（当前JVM）内的消息
//            if (EventPublishEnum.APPLICATION_CONTEXT_EVENT.equals(publishTo)
//                    || EventPublishEnum.ALL.equals(publishTo)) {
//
//                if (log.isDebugEnabled()) {
//                    log.debug("发送 spring ApplicationEvent : " + event);
//                }
//
//                 applicationContext.publishEvent(event);
//
//            }

            // MQ 的消息
            if (EventPublishEnum.MQ.equals(publishTo)
                    || EventPublishEnum.ALL.equals(publishTo)) {


                String exchange = qhRaffleProperties.getMq().getPrefix()
                        + "."
                        + event.getClass().getName();
                System.out.println(event.getClass().getName());
                //msg.api.excelOut.voteworks.ExportVoteWorksEvent
                if (log.isDebugEnabled()) {
                    log.debug("发送 MQ 的 `" + exchange + "` 发送消息 : " + event);
                }


                amqpTemplate.convertAndSend(exchange, null, event);
            }

        } catch (Exception e) {
            log.warn(e.getMessage(), e);
        }

    }
}
