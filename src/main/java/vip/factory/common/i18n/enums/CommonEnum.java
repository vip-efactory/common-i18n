package vip.factory.common.i18n.enums;


import lombok.Getter;

/**
 * 通用的，不便分类的枚举类型
 */
@Getter
public enum CommonEnum implements IBaseErrorEnum {
    SUCCESS(0, "成功"),
    ERROR(1, "未知错误"),
    INVALID_PARAM(2, "参数非法"),
    INVALID_JSON(3, "JSON字符串非法."),
    UNKNOWN_METHOD(4, "方法名未知"),
    ;

    private int errorCode;
    private String reason;
    private static final int MODULE_TYPE = ModuleTypeDefine.COMMON;
    private static int offset = ErrorCodeUtil.register(MODULE_TYPE);

    CommonEnum(int errorCode, String reason) {
        this.errorCode = errorCode;
        this.reason = reason;
    }


    public int getErrorCode() {
        return errorCode + offset;
    }

}
