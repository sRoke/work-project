package net.kingsilk.qh.activity.api.brandApp.vote.task.dto;

import net.kingsilk.qh.activity.core.vote.TaskStatusEnum;
import net.kingsilk.qh.activity.core.vote.TaskTypeEnum;

public class TaskResp {

    private String id;

    private TaskStatusEnum taskStatus;

    private TaskTypeEnum taskTypeEnum;

    private String dateCreated;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TaskStatusEnum getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatusEnum taskStatus) {
        this.taskStatus = taskStatus;
    }

    public TaskTypeEnum getTaskTypeEnum() {
        return taskTypeEnum;
    }

    public void setTaskTypeEnum(TaskTypeEnum taskTypeEnum) {
        this.taskTypeEnum = taskTypeEnum;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }
}
