package vip.efactory.common.i18n.enums;

import lombok.SneakyThrows;
import vip.efactory.common.i18n.util.FileUtil;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * 本工具类是为了快速地将项目中的实体属性快速地转换为国际化的key和value
 */
public class Entityi18nUtil {
    private static List<String> locales = new ArrayList<>();  // 支持的国际化种类，参考了Java的Locale类的种类
    private static List<Class> entities = new LinkedList<>(); // 保存要生成国际化的类信息

    /**
     * 静态初始化支持的国际化种类，参考了Java的Locale类的种类，更多参见：http://www.localeplanet.com/icu/
     */
    static {
        locales.add("zh_CN");   // 中文简体
        locales.add("zh_TW");   // 中文繁体
        locales.add("ru-RU");   // 俄语
        locales.add("ru-KZ");   // 俄语 -哈萨克斯坦
        locales.add("en_US");   // 美国英语
        locales.add("en_GB");   // 英国英语
        locales.add("en_CA");   // 加拿大英语
        locales.add("fr_FR");   // 法国法语
        locales.add("fr_CA");   // 加拿大法语
        locales.add("de_DE");   // 德语
        locales.add("it_IT");   // 意大利
        locales.add("ja_JP");   // 日语
        locales.add("ko_KR");   // 韩语
    }

    /**
     * 添加要生成国际化的实体
     */
    private static void addEntity(Class clazz) {
        entities.add(clazz);
    }

    /**
     * 批量添加
     *
     * @param clazzs
     */
    private static void addEntitys(List<Class> clazzs) {
        entities.addAll(clazzs);
    }

    // 对每一个实体实例,生成:key = value格式的条目!
    public static String installResourceEntry(Class clazz, Field field) {
        // 整的key键:类名.属性
        return clazz.getSimpleName() + "." + field.getName() + "=" + ErrorCodeUtil.toUnicodeString(field.getName());
    }

    /**
     * 利用实体生成国际化的资源文件
     *
     * @param fileName 仅仅是文件名，不包含路径与后缀
     * @param entities
     */
    @SneakyThrows
    public static String geni18nPropertiesFile(String fileName, List<Class> entities) {
        //生成的错误码文件的存放位置,直接在项目的指定位置,注意,如果手动修改过此文件,谨慎执行此main方法.
//        Map<String, String> envs = System.getenv();
        String pathanme = System.getenv("PWD") + File.separator + fileName + ".properties";
        //容纳所有的条目
        List<String> lines = new ArrayList<String>();
        //生成的时间写入注释头中
        lines.add("# This file create at " + new Date() + " ,by Entityi18nUtil! \n");
        String lastClass = "";
        String currClass = "";      //当前正在处理的实体类
        for (Class clazz : entities) {
            currClass = clazz.getName();         //通过反射获取到当前实体的类名
            if (!lastClass.equals(currClass)) {
                if (!lastClass.isEmpty()) {
                    lines.add("\n");                        //添加一个换行
                }
                lines.add("#" + currClass + "\n");          //添加一个当前实体类的注释
                lastClass = currClass;
            }

            // 得到当前实例的所有属性
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                lines.add(installResourceEntry(clazz, field) + "\n"); //生成实体对应的键值对
            }
        }
        //将上面的实体值生成文本文件,以便拷贝到项目里面来用
        FileUtil.writeFileByLines(lines, pathanme);         //将所有的键值对写出!

        System.out.println("文件写出完毕!");
        System.out.println("请到此目录找生成的文件:" + pathanme);
        return pathanme;
    }

    /**
     * 拷贝基础的生成文件，复制成其他的文件，以便专业的人员进一步进行优化翻译
     *
     * @param pathanme 基础的文件，不带国际化参数的文件
     * @param locales  要生成的国际化文件种类
     */
    public static void copyToLocale(String pathanme, List<String> locales) {
        // 拷贝成其他的国际化文件
        for (String locale : locales) {
            String newFileName = pathanme.substring(0, pathanme.lastIndexOf(".")) + "_" + locale + pathanme.substring(pathanme.lastIndexOf("."));
            FileUtil.copyFile(pathanme, newFileName, true);
            System.out.println("请到此目录找生成的文件:" + newFileName);
        }
    }

    /**
     * 拷贝基础的生成文件，复制成其他的文件，以便专业的人员进一步进行优化翻译，
     * 不指定生成哪些国际化就默认本文件中的默认列表
     *
     * @param pathanme 基础的文件，不带国际化参数的文件，包含路径及完整的文件名及后缀
     */
    public static void copyToLocale(String pathanme) {
        // 拷贝成其他的国际化文件
        for (String locale : locales) {
            String newFileName = pathanme.substring(0, pathanme.lastIndexOf(".")) + "_" + locale + pathanme.substring(pathanme.lastIndexOf("."));
            FileUtil.copyFile(pathanme, newFileName, true);
            System.out.println("请到此目录找生成的文件:" + newFileName);
        }
    }


    public static void main(String[] args) {
        addEntity(CommDBEnum.class);
        //生成的错误码文件的存放位置,直接在项目的指定位置,注意,如果手动修改过此文件,谨慎执行此main方法.
        String fileName = "messages";
        // 生成基础的文件，并拷贝生成其他的国际化的文件
//        copyToLocale(geni18nPropertiesFile(fileName, entities), locales);
        copyToLocale(geni18nPropertiesFile(fileName, entities));
    }

}
