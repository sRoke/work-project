package net.kingsilk.qh.activity.core.vote;

/**
 * 导入任务状态
 */
public enum TaskStatusEnum {

    IN_IMPORT("正在导入中"),
    COMPLETE_IMPORT("导入完成"),
    FAIL_IMPORT("导入失败");

    final String description;

    private TaskStatusEnum(String desp) {
        this.description = desp;
    }
}
