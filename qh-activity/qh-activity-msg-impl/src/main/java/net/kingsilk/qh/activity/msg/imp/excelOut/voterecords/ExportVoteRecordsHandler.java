package net.kingsilk.qh.activity.msg.imp.excelOut.voterecords;

import msg.api.excelOut.voterecords.ExportVoteRecordsApi;
import msg.api.excelOut.voterecords.ExportVoteRecordsEvent;
import net.kingsilk.qh.activity.core.vote.TaskTypeEnum;
import net.kingsilk.qh.activity.domain.Task;
import net.kingsilk.qh.activity.msg.imp.AbstractJobImpl;
import net.kingsilk.qh.activity.repo.TaskRepo;
import net.kingsilk.qh.activity.service.QhActivityProperties;
import net.kingsilk.qh.activity.service.service.excelout.ExportVoteRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 *
 */
@Component("voteRecordsExcelOutApi")
public class ExportVoteRecordsHandler extends AbstractJobImpl implements ExportVoteRecordsApi {

    @Autowired
    private QhActivityProperties qhActivityProperties;


    @Autowired
    private TaskRepo taskRepo;


    @Autowired
    private ExportVoteRecordsService exportVoteRecordsService;

    public String getLockKey(ExportVoteRecordsEvent event) {
        StringBuilder buf = new StringBuilder();
        buf
                .append(qhActivityProperties.getMq().getPrefix())
                .append("/").append(ExportVoteRecordsEvent.class.getName())
                .append("/").append(event.getTaskId()).append("111");
        return buf.toString();
    }

    @Override
    public void handle(ExportVoteRecordsEvent exportVoteRecordsEvent) {

        String lockKey = getLockKey(exportVoteRecordsEvent);

        long waitLockTime = qhActivityProperties.getMq().getDefaultConf().getLockWaitTime();

        String taskId = exportVoteRecordsEvent.getTaskId();
        Task task = taskRepo.findOne(taskId);

        Assert.notNull(task.getTaskTypeEnum(), "未传过来任务类型参数");
        if (task.getTaskTypeEnum() == TaskTypeEnum.VOTERECORDS_TASK) {
            lockAndExec(lockKey, waitLockTime, () -> exportVoteRecordsService.findAndExcelOut(exportVoteRecordsEvent));
        }
    }
}
