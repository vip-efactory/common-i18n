package vip.efactory.common.i18n.enums;

import lombok.Getter;

/**
 * 审批流相关的错误码定义
 * @author dbdu
 */
@Getter
public enum CommActivitiEnum implements IBaseErrorEnum {
    // 流程文件删除错误
    DELETE_DELOYMENT_PROCESS_ERROR(0, "流程文件删除错误"),
    CHANGE_PROCESS_INSTANCE_STATE_ERROR(1, "更改流程文件状态错误"),

    DELETE_PROCESS_INSTANCE_ERROR(100, "删除流程实例错误"),

    DELETE_JOB_ERROR(200, "删除作业失败"),
    EXECUTE_JOB_ERROR(201, "执行作业失败"),
    CHANGE_JOB_RETRIES_ERROR(202, "更改作业可重试次数失败"),

    TASK_FINISHED_ERROR(300, "任务完成失败"),

    ERROR_AUTHORITY(400, "无此操作权限"),
    TASK_STATUS_ERROR(401, "任务暂未流转至此"),

    RUNNING_TASK_DELETE_ERROR(500, "流程已启动，记录不可删除"),
    TASK_START_ERROR(600, "任务启动失败"),

    TASK_WITHDRAW_ERROR(700, "任务撤销失败");

    private final int errorCode;
    private final String reason;

    private static final int MODULE_TYPE = ModuleTypeDefine.ACTIVITI;
    private static int offset = ErrorCodeUtil.register(MODULE_TYPE);

    CommActivitiEnum(int errorCode, String reason) {
        this.errorCode = errorCode;
        this.reason = reason;
    }

    @Override
    public int getErrorCode() {
        return errorCode + offset;
    }
}
