package net.kingsilk.qh.activity.domain;

import net.kingsilk.qh.activity.core.vote.TaskStatusEnum;
import net.kingsilk.qh.activity.core.vote.TaskTypeEnum;

import java.util.Date;
import java.util.List;

public class Task extends Base{

    //任务类型
    private TaskStatusEnum taskStatus = TaskStatusEnum.IN_IMPORT;

    /**任务名称**/
    private String name;

    //该导入任务的文件存储id
    private String fileId;

    private String fileName;

    private boolean isTemplate = false;

    private Integer successCount = 0;

    private Integer failCount = 0;

    private Long time = 0l;

    private TaskTypeEnum taskTypeEnum;

    private String forLockKey;

    private String userId;

    private String brandAppId;

    private String activityId;

    private String wxComAppId;

    private String wxMpAppId;

    /**
     * 导出结果的排序
     */
    private List sort;

    /**
     * 作品投票记录搜索关键字
     */
    private String voteKeyword;

    /**
     * 投票作品搜索关键字
     */
    private String workKeyword;
    //描述
    private String desp;

    public TaskStatusEnum getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatusEnum taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public boolean isTemplate() {
        return isTemplate;
    }

    public void setTemplate(boolean template) {
        isTemplate = template;
    }

    public Integer getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(Integer successCount) {
        this.successCount = successCount;
    }

    public Integer getFailCount() {
        return failCount;
    }

    public void setFailCount(Integer failCount) {
        this.failCount = failCount;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getDesp() {
        return desp;
    }

    public void setDesp(String desp) {
        this.desp = desp;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public TaskTypeEnum getTaskTypeEnum() {
        return taskTypeEnum;
    }

    public void setTaskTypeEnum(TaskTypeEnum taskTypeEnum) {
        this.taskTypeEnum = taskTypeEnum;
    }

    public String getForLockKey() {
        return forLockKey;
    }

    public void setForLockKey(String forLockKey) {
        this.forLockKey = forLockKey;
    }

    public List getSort() {
        return sort;
    }

    public void setSort(List sort) {
        this.sort = sort;
    }

    public String getVoteKeyword() {
        return voteKeyword;
    }

    public void setVoteKeyword(String voteKeyword) {
        this.voteKeyword = voteKeyword;
    }

    public String getWorkKeyword() {
        return workKeyword;
    }

    public void setWorkKeyword(String workKeyword) {
        this.workKeyword = workKeyword;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBrandAppId() {
        return brandAppId;
    }

    public void setBrandAppId(String brandAppId) {
        this.brandAppId = brandAppId;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getWxComAppId() {
        return wxComAppId;
    }

    public void setWxComAppId(String wxComAppId) {
        this.wxComAppId = wxComAppId;
    }

    public String getWxMpAppId() {
        return wxMpAppId;
    }

    public void setWxMpAppId(String wxMpAppId) {
        this.wxMpAppId = wxMpAppId;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskStatus=" + taskStatus +
                ", name='" + name + '\'' +
                ", fileId='" + fileId + '\'' +
                ", fileName='" + fileName + '\'' +
                ", isTemplate=" + isTemplate +
                ", successCount=" + successCount +
                ", failCount=" + failCount +
                ", time=" + time +
                ", taskTypeEnum=" + taskTypeEnum +
                ", forLockKey='" + forLockKey + '\'' +
                ", sort=" + sort +
                ", voteKeyword='" + voteKeyword + '\'' +
                ", workKeyword='" + workKeyword + '\'' +
                ", desp='" + desp + '\'' +
                '}';
    }
}
