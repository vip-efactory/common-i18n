package vip.efactory.common.i18n.enums;


import lombok.Getter;

/**
 * 通用的，不便分类的枚举类型
 * @author dusuanyun
 */
@Getter
public enum CommonEnum implements IBaseErrorEnum {
    //
    SUCCESS(0, "成功"),
    ERROR(1, "未知错误"),
    ;

    private final int errorCode;
    private final String reason;
    private static final int MODULE_TYPE = ModuleTypeDefine.COMMON;
    private static final int offset = ErrorCodeUtil.register(MODULE_TYPE);

    CommonEnum(int errorCode, String reason) {
        this.errorCode = errorCode;
        this.reason = reason;
    }


    @Override
    public int getErrorCode() {
        return errorCode + offset;
    }

}
