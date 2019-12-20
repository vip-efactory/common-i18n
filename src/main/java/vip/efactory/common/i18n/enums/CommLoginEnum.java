package vip.efactory.common.i18n.enums;

import lombok.Getter;

/**
 * 登录相关的错误码
 */
@Getter
public enum CommLoginEnum implements IBaseErrorEnum {
    UNKNOWN(0, "登录未知异常"),
    USERNAME_PWD(1, "登录失败，用户名或密码错误"),
    MODIFY_PWD(2, "修改密码失败"),
    OLD_PWD_ERROR(3, "原密码不正确"),
    USER_LOCKED(4, "账户已经被锁定"),
    NOT_AUTHED(5, "您没有被授权"),
    VERIFY_IS_INVALID(6, "验证码错误"),
    SESSION_INVALID(7, "会话已失效，请重新登录！"),
    LOGIN_ATTEMPTS_EXCEEDS_LIMIT(8, "登录尝试次数超过限制次数！"),//login attempts exceeds the limit
    ;

    private int errorCode;
    private String reason;
    private static int offset = ErrorCodeUtil.register(ModuleTypeDefine.LOGIN);

    private CommLoginEnum(int errorCode, String reason) {
        this.errorCode = errorCode;
        this.reason = reason;
    }

    @Override
    public int getErrorCode() {
        return errorCode + offset;
    }

};