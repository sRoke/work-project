package net.kingsilk.qh.vote.server.resource.voteApp.vote.task;

import net.kingsilk.qh.vote.api.ErrStatus;
import net.kingsilk.qh.vote.api.UniResp;
import net.kingsilk.qh.vote.api.voteApp.vote.task.TaskApi;
import net.kingsilk.qh.vote.api.voteApp.vote.task.dto.TaskResp;
import net.kingsilk.qh.vote.domain.Task;
import net.kingsilk.qh.vote.repo.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;

public class TaskResource implements TaskApi {

    @Autowired
    private TaskRepo taskRepo;

    @Override
    public UniResp<TaskResp> info(
            String voteAppId,
            String voteId,
            String id) {

        UniResp<TaskResp> uniResp = new UniResp<>();

        Task task = taskRepo.findOne(id);
        if (task == null){
            uniResp.setException("没有该任务");
            uniResp.setStatus(ErrStatus.FOUNDNULL);
            return uniResp;
        }

        TaskResp taskResp = new TaskResp();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        taskResp.setDateCreated(sdf.format(task.getDateCreated()));
        taskResp.setId(task.getId());
        taskResp.setTaskStatus(task.getTaskStatus());
        taskResp.setTaskTypeEnum(task.getTaskTypeEnum());
        uniResp.setData(taskResp);
        uniResp.setStatus(ErrStatus.OK);
        return uniResp;
    }
}
