package net.kingsilk.qh.agency.msg;

import org.springframework.context.event.ApplicationContextEvent;

/**
 * 消息事件如何发送。
 */
public enum EventPublishEnum {

    /**
     * 不发送异步消息。相当于 EventPublisherImpl 什么都不做
     */
    NONE,

    /**
     * 只发送 Spring 的 {@link ApplicationContextEvent}
     */
    APPLICATION_CONTEXT_EVENT,

    /**
     * 只向 RabbitMQ 发送。
     */
    MQ,

    /**
     *
     * 全部发送。
     */
    ALL

}
