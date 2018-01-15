package net.kingsilk.qh.vote.msg.imp.excelOut.voteworks;

import msg.api.excelOut.voteworks.ExportVoteWorksApi;
import msg.api.excelOut.voteworks.ExportVoteWorksEvent;
import net.kingsilk.qh.vote.core.vote.TaskTypeEnum;
import net.kingsilk.qh.vote.domain.Task;
import net.kingsilk.qh.vote.msg.imp.AbstractJobImpl;
import net.kingsilk.qh.vote.repo.TaskRepo;
import net.kingsilk.qh.vote.service.QhVoteProperties;
import net.kingsilk.qh.vote.service.service.excelout.ExportVoteRecordsService;
import net.kingsilk.qh.vote.service.service.excelout.ExportVoteWorksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 *
 */
@Component("voteVoteWorksExcelOutApi")
public class ExportVoteWorksHandler extends AbstractJobImpl implements ExportVoteWorksApi {

    @Autowired
    private QhVoteProperties qhVoteProperties;


    @Autowired
    private TaskRepo taskRepo;


    @Autowired
    private ExportVoteRecordsService exportVoteRecordsService;

    @Autowired
    private ExportVoteWorksService exportVoteWorksService;


    public String getLockKey(ExportVoteWorksEvent event) {
        StringBuilder buf = new StringBuilder();
        buf
                .append(qhVoteProperties.getMq().getPrefix())
                .append("/").append(ExportVoteWorksEvent.class.getName())
                .append("/").append(event.getTaskId()).append("111");
        return buf.toString();
    }

    @Override
    public void handle(ExportVoteWorksEvent exportVoteWorksEvent) {

        String lockKey = getLockKey(exportVoteWorksEvent);

        long waitLockTime = qhVoteProperties.getMq().getDefaultConf().getLockWaitTime();

        String taskId = exportVoteWorksEvent.getTaskId();
        Task task = taskRepo.findOne(taskId);
        ;

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
