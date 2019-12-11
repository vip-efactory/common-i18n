package vip.factory.common.i18n.enums;

import lombok.Getter;

@Getter
public enum CommEmailEnum implements IBaseErrorEnum {

    EMAIL_CFG_LOST(1, "邮箱配置丢失"),
    EMAIL_REGISTERED(2, "邮箱已注册"),
    EMAIL_TEST_ERROR(3, "Email配置测试失败，请检查配置"),
    EMAIL_QUEUE_INSERT_ERROR(4, "EMAIL队列已满，任务增加失败！"),
    ;

    private int errorCode;
    private String reason;

    private static final int MODULE_TYPE = ModuleTypeDefine.EMAIL;
    private static int offset = ErrorCodeUtil.register(MODULE_TYPE);

    CommEmailEnum(int errorCode, String reason) {
        this.errorCode = errorCode;
        this.reason = reason;
    }

    public int getErrorCode() {
        switch (this) {
//            case SUCCESS:
//            case INVALID_TOKEN:
//                return errorCode;
            default:
                return errorCode + offset;
        }
    }
};