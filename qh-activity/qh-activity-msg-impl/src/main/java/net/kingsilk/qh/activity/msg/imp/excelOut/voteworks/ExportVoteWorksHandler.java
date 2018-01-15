package net.kingsilk.qh.activity.msg.imp.excelOut.voteworks;

import msg.api.excelOut.voteworks.ExportVoteWorksApi;
import msg.api.excelOut.voteworks.ExportVoteWorksEvent;
import net.kingsilk.qh.activity.core.vote.TaskTypeEnum;
import net.kingsilk.qh.activity.domain.Task;
import net.kingsilk.qh.activity.msg.imp.AbstractJobImpl;
import net.kingsilk.qh.activity.repo.TaskRepo;
import net.kingsilk.qh.activity.service.QhActivityProperties;
import net.kingsilk.qh.activity.service.service.excelout.ExportVoteRecordsService;
import net.kingsilk.qh.activity.service.service.excelout.ExportVoteWorksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 *
 */
@Component("voteVoteWorksExcelOutApi")
public class ExportVoteWorksHandler extends AbstractJobImpl implements ExportVoteWorksApi{

    @Autowired
    private QhActivityProperties qhActivityProperties;


    @Autowired
    private TaskRepo taskRepo;


    @Autowired
    private ExportVoteRecordsService exportVoteRecordsService;

    @Autowired
    private ExportVoteWorksService exportVoteWorksService;


    public String getLockKey(ExportVoteWorksEvent event) {
        StringBuilder buf = new StringBuilder();
        buf
                .append(qhActivityProperties.getMq().getPrefix())
                .append("/").append(ExportVoteWorksEvent.class.getName())
                .append("/").append(event.getTaskId()).append("111");
        return buf.toString();
    }

    @Override
    public void handle(ExportVoteWorksEvent exportVoteWorksEvent) {

        String lockKey = getLockKey(exportVoteWorksEvent);

        long waitLockTime = qhActivityProperties.getMq().getDefaultConf().getLockWaitTime();

        String taskId = exportVoteWorksEvent.getTaskId();
        Task task = taskRepo.findOne(taskId);;

        Assert.notNull(task.getTaskTypeEnum(), "未传过来任务类型参数");
        if (task.getTaskTypeEnum() == TaskTypeEnum.VOTEWORKS_TASK) {
            lockAndExec(lockKey, waitLockTime, () -> exportVoteWorksService.findAndExcelOut(exportVoteWorksEvent));
        }
//        else if (task.getTaskTypeEnum() == TaskTypeEnum.VOT) {
//            System.out.println("VOTERECORDS");;
//            lockAndExec(lockKey, waitLockTime, () -> exportVoteRecordsService.findAndExcelOut(exportVoteWorksEvent));
//        }

    }
}
