package net.kingsilk.qh.platform.msg.api.admin;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

public interface AddSAEventEXApi {

    /**
     * 新增超级管理员
     * <p>
     * <p>
     * - 加分布式锁。
     */
    @AddSAListener
    @EventListener
    @Async
    void handle(AddSAEventEx addSAEventEx);

}
