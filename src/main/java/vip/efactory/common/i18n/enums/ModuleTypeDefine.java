package vip.efactory.common.i18n.enums;

/**
 * 定义模块的首部代码
 */
public interface ModuleTypeDefine {
    int COMMON = 0;             // 通用模块
    int HTTPSTATUS = 0;         // http 的通信码
    int DATABASE = 1000;        // 数据库模块
    int LOGIN = 1010;           // 登录模块
    int REST = 1020;            // REST API模块
    int FILE = 1030;            // 文件操作模块
    int EMAIL = 1040;           // Email模块
    int SMS = 1050;             // 短信模块
    int ACTIVITI = 1060;          // 审批流模块
}
