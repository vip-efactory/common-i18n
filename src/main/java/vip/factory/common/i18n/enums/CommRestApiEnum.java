package vip.factory.common.i18n.enums;

import lombok.Getter;

@Getter
public enum CommRestApiEnum implements IBaseErrorEnum {
    USERNAMEPWD_INVALID(1, "用户名密码无效"),
    AUTH_FAIL(2, "授权失败，无效授权码或无效状态信息"),
    TOKEN_INVALID(3, "无效的access_token"),
    TIMEOUT(4, "请求超时"),
    INSUFFICIENT_PERRMISSION(5, "权限不足"),
    ;

    private int errorCode;
    private String reason;
    private static final int MODULE_TYPE = ModuleTypeDefine.REST;
    private static int offset = ErrorCodeUtil.register(MODULE_TYPE);

    private CommRestApiEnum(int errorCode, String reason) {
        this.errorCode = errorCode;
        this.reason = reason;
    }

    @Override
    public String getReason() {
        return reason;
    }

    @Override
    public int getErrorCode() {
        return errorCode + offset;
    }

}
