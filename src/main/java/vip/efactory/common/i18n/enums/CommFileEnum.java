package vip.efactory.common.i18n.enums;

import lombok.Getter;

@Getter
public enum CommFileEnum implements IBaseErrorEnum {
    UNKNOWN(0, "文件操作失败"),
    FILE_NON_EXISTENT(1, "文件不存在"),
    FILE_TOO_LARGE(2, "文件过大"),
    FILE_LOCKED(3, "文件被锁定"),
    FILE_FORMAT_ERROR(4, "文件格式错误"),
    FILE_UNSUPPORT_FORMAT(5, "不支持的文件格式"),
    FILE_EXIST(6, "文件已存在"),
    DIR_EXIST(7, "文件夹已存在"),
    FILE_SAVE_FAILED(8, "文件保存失败！"),
    FILE_EXCEL_2007(9, "文件格式必须为excel2007,并且内容不为空"),
    FILE_EXPORT_ERROR(10, "文件导出失败，请重试"),
    FILE_MAGIC_ERROR(11, "magic非法"),
    FILE_UNKNOWN_IMAGENAME(12, "未知的imagename"),
    FILE_DATA_COLUMN_FORMAT_ERROR(13, "文件内数据列格式错误"),
    PO_ID_MISSING(14, "实体Id缺失，上传文件保存失败！"),
    ;

    private int errorCode;
    private String reason;
    private static final int MODULE_TYPE = ModuleTypeDefine.FILE;
    private static int offset = ErrorCodeUtil.register(MODULE_TYPE);

    private CommFileEnum(int errorCode, String reason) {
        this.errorCode = errorCode;
        this.reason = reason;
    }

    public int getErrorCode() {
        return errorCode + offset;
    }

};