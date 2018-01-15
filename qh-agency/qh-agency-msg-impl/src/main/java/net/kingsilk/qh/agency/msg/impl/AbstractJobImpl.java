package net.kingsilk.qh.agency.msg.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.support.locks.*;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 *
 */
public abstract class AbstractJobImpl {

    protected Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private LockRegistry lockRegistry;

    protected void lockAndExec(
            String lockKey,
            long waitLockTime,
            Runnable runnable
    ) {

        long startTime = System.currentTimeMillis();
        log.info("任务开始");

        Lock lock = lockRegistry.obtain(lockKey);
        try {
            if (!lock.tryLock(waitLockTime, TimeUnit.MILLISECONDS)) {
                log.info("加锁失败，任务中止");
                return;
            }
        } catch (InterruptedException e) {
            log.warn("在等待加锁时被中止", e);
            return;
        }

        // 已经加锁OK，执行

        try {
            runnable.run();

            long endTime = System.currentTimeMillis();
            Duration duration = Duration.ofMillis(endTime - startTime);
            log.info("任务完成。耗时：" + duration.toString());
        } catch (Exception e) {
            log.error("执行任务时出现异常，将终止。", e);
        } finally {
            lock.unlock();
        }
    }
}
