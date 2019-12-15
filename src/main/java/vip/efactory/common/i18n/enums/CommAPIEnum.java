package vip.efactory.common.i18n.enums;

import lombok.Getter;

@Getter
public enum CommAPIEnum implements IBaseErrorEnum {
    // 登录获取token部分
    USERNAMEPWD_INVALID(100, "用户名密码无效"),

    // token验证部分
    AUTH_FAIL(200, "授权失败，无效授权码或无效状态信息"),
    TOKEN_INVALID(201, "无效的access_token"),

    // 权限检查部分
    INSUFFICIENT_PERRMISSION(300, "权限不足"),

    // 约束检查部分
    PROPERTY_CHECK_FAILED(400, "属性检查未通过"),
    INVALID_PARAM(401, "参数非法"),
    INVALID_JSON(402, "JSON字符串非法."),

    // 其他部分
    TIMEOUT(500, "请求超时"),
    ;

    private int errorCode;
    private String reason;
    private static final int MODULE_TYPE = ModuleTypeDefine.REST;
    private static int offset = ErrorCodeUtil.register(MODULE_TYPE);

    private CommAPIEnum(int errorCode, String reason) {
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
