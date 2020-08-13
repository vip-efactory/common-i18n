package vip.efactory.common.i18n.enums;


import lombok.Getter;

/**
 * 数据库操作相关的错误码定义
 */
@Getter
public enum CommDBEnum implements IBaseErrorEnum {
    UNKNOWN(0, "数据库操作异常"),
    CONNECT_FAILED(1, "连接数据库失败"),

    INSERT_ERROR(100, "插入数据失败"),
    INSERT_EXIST(101, "插入数据失败,数据已经存在"),
    INSERT_ITEM_REPEAT(102, "插入数据失败,插入列表中存在重复的项目:"),

    DELETE_ERROR(200, "删除数据失败"),
    DELETE_NON_EXISTENT(201, "删除数据失败, 删除对象不存在."),
    DELETE_LOCKED(202, "删除数据失败,数据被锁定."),
    DELETE_BIND(203, "删除数据失败,部分数据被绑定."),
    DELETE_SELECTED_BIND(205, "删除数据失败,您选中的数据已经被绑定"),
    DELETE_BIND_ALL(204, "删除数据失败,数据被绑定."),

    UPDATE_ERROR(300, "更新数据失败"),
    UPDATE_NON_EXISTENT(301, "更新数据失败, 更新对象不存在"),
    UPDATE_EXISTENT(302, "更新数据失败, 更新对象已存在"),
    UPDATE_ITEM_REPEAT(303, "更新数据失败,更新列表中存在重复的项目"),
    UPDATE_NEW_BY_OLD_NOT_ALLOWED(304, "更新失败，不允许旧数据覆盖数据库中新的数据"),

    SELECT_ERROR(400, "查找数据失败"),
    SELECT_NON_EXISTENT(401, "查找数据失败,查找对象不存在"),
    SELECT_PROPERTY_NOT_EMPTY(402, "查询属性条件不允许为空"),
    SELECT_PROPERTY_NAME_NOT_EMPTY(403, "查询属性条件名不允许为空"),

    // 用户用主键查询或删除记录时
    KEY_NOT_NULL(500, "主键不允许为空"),
    ;

    private int errorCode;
    private String reason;
    private static final int MODULE_TYPE = ModuleTypeDefine.DATABASE;
    private static int offset = ErrorCodeUtil.register(MODULE_TYPE);

    CommDBEnum(int errorCode, String reason) {
        this.errorCode = errorCode;
        this.reason = reason;
    }


    @Override
    public int getErrorCode() {
        return errorCode + offset;
    }

}
