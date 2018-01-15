package msg.api.excelOut.voterecords;


import java.io.Serializable;

/**
 * 通知
 * <p>
 * 加锁路径为： ${prefix}/${className}/${wxMpAppId}
 */
public class ExportVoteRecordsEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 微信公众号 APP ID。
     */
    private String brandAppId;

    private String activityId;

    private String taskId;


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

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

//    public List<String> getSort() {
//        return sort;
//    }

//    public void setSort(List<String> sort) {
//        this.sort = sort;
//    }
//
//    public String getVoteKeyword() {
//        return voteKeyword;
//    }
//
//    public void setVoteKeyword(String voteKeyword) {
//        this.voteKeyword = voteKeyword;
//    }
//
//    public String getWorkKeyword() {
//        return workKeyword;
//    }
//
//    public void setWorkKeyword(String workKeyword) {
//        this.workKeyword = workKeyword;
//    }
//
//    public Task getTask() {
//        return task;
//    }
//
//    public void setTask(Task task) {
//        this.task = task;
//    }
}
