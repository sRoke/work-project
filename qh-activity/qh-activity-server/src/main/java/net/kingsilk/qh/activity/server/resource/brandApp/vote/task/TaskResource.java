package net.kingsilk.qh.activity.server.resource.brandApp.vote.task;

import net.kingsilk.qh.activity.api.ErrStatus;
import net.kingsilk.qh.activity.api.UniResp;
import net.kingsilk.qh.activity.api.brandApp.vote.task.TaskApi;
import net.kingsilk.qh.activity.api.brandApp.vote.task.dto.TaskResp;
import net.kingsilk.qh.activity.domain.Task;
import net.kingsilk.qh.activity.repo.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;

public class TaskResource implements TaskApi{

    @Autowired
    private TaskRepo taskRepo;

    @Override
    public UniResp<TaskResp> info(
            String brandAppId,
            String activityId,
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
