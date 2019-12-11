package vip.factory.common.i18n.enums;

import lombok.Getter;

/**
 * 短信枚举相关的错误信息
 */
@Getter
public enum CommSMSEnum implements IBaseErrorEnum {
    SMS_AUTH_ERROR(1, "短信认证失败"),
    SMS_TEST_ERROR(2, "短信测试失败"),
    ;

    private int errorCode;
    private String reason;

    private static final int MODULE_TYPE = ModuleTypeDefine.SMS;
    private static int offset = ErrorCodeUtil.register(MODULE_TYPE);

    private CommSMSEnum(int errorCode, String reason) {
        this.errorCode = errorCode;
        this.reason = reason;
    }

    public int getErrorCode() {
        switch (this) {
//		case SUCCESS:
//		case INVALID_TOKEN:
//			return errorCode;
            default:
                return errorCode + offset;
        }
    }
};