package vip.efactory.common.i18n.enums;

import lombok.Getter;

/**
 * 高级搜索枚举相关的错误信息
 */
@Getter
public enum CommADSearchEnum implements IBaseErrorEnum {
    CONDITION_NAME_IS_NULL(1, "查询条件名称不允许为空"),
    CONDITION_NAME_IS_ILLEGAL(2, "查询条件名称非法"),
    CONDITION_VALUE_IS_NULL(3, "查询条件值不允许为空"),
    CONDITION_VALUE_IS_ILLEGAL(4, "查询条件值非法"),
    CONDITIONS_IS_EMPTY(5, "查询条件集合不允许为空"),

    ;

    private int errorCode;
    private String reason;

    private static final int MODULE_TYPE = ModuleTypeDefine.ADSEARCH;
    private static int offset = ErrorCodeUtil.register(MODULE_TYPE);

    private CommADSearchEnum(int errorCode, String reason) {
        this.errorCode = errorCode;
        this.reason = reason;
    }

    @Override
    public int getErrorCode() {
        switch (this) {
//		case SUCCESS:
//		case INVALID_TOKEN:
//			return errorCode;
            default:
                return errorCode + offset;
        }
    }
}
