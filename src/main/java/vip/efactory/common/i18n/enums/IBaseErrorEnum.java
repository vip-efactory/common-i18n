package vip.efactory.common.i18n.enums;

/**
 * 定义基本的错误枚举应有的方法
 */
public interface IBaseErrorEnum {
    /**
     * 错误描述
     */
    String getReason();

    /**
     * 获取错误码
     */
    int getErrorCode();

    String name();
}
