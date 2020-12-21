package vip.efactory.common.i18n.enums;


import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import vip.efactory.common.i18n.service.ILocaleMsgSourceService;
import vip.efactory.common.i18n.util.FileUtil;

import java.util.*;

@Component
@Slf4j
public class ErrorCodeUtil {
    private static List<Integer> moduleList = new Vector<Integer>();
    private static ILocaleMsgSourceService localeMessageSourceService;

    //通过下面的方法为静态成员赋值!!!
    @Autowired
    public void setLocaleMessageSourceService(ILocaleMsgSourceService localeMessageSourceService) {
        ErrorCodeUtil.localeMessageSourceService = localeMessageSourceService;
    }

    //得到6位的错误代码,例如:101000,这里的101就是moduleType
    public static int register(int moduleType) {
        synchronized (moduleList) {
            if (moduleList.contains(moduleType)) {
                log.error("moduleType[{}] registered ", moduleType);
                return 0;
            }
            moduleList.add(moduleType);
            return moduleType * 1000;
        }
    }

    //对每一个枚举实例,生成:key = value格式的条目!
    public static String installResourceName(IBaseErrorEnum iEnum) throws Exception {
        return toPropertiesKey(iEnum) + "=" + toUnicodeString(iEnum.getReason());
    }

    //生成完整的key键
    public static String toPropertiesKey(IBaseErrorEnum iEnum) {
        return iEnum.getClass().getSimpleName() + "." + iEnum.getErrorCode() + "." + iEnum.name();
    }

    //生成完整的Value值
    public static String toUnicodeString(String s) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= 0 && c <= 255) {
                sb.append(c);
            } else {
                sb.append("\\u" + Integer.toHexString(c));
            }
        }
        return sb.toString();
    }

    //使用枚举类型来获取值
    public static String getMessage(IBaseErrorEnum iEnum, String... args) {
        return getMessage(toPropertiesKey(iEnum), args);
    }

    //使用key来获取值
    public static String getMessage(String key, String... args) {
        String value = null;
        value = localeMessageSourceService.getMessage(key, args);
        if (StringUtils.isEmpty(value) || value.equals(key)) {
            value = key;
            log.warn("missing key [{}]", key);
        }
        return value;
    }

    //从main方法中独立，方便其他地方使用

    /**
     *  利用枚举生成国际化的资源文件
     * @param fileName 仅仅是文件名，不包含路径与后缀
     * @param enums
     */
    @SneakyThrows
    public static String geni18nPropertiesFile(String fileName, List<IBaseErrorEnum> enums) {
        //生成的错误码文件的存放位置,直接在项目的指定位置,注意,如果手动修改过此文件,谨慎执行此main方法.
        String pathanme = "src/main/resources/i18n/" + fileName + ".properties";
        //容纳所有的条目
        List<String> lines = new ArrayList<String>();
        //生成的时间写入注释头中
        lines.add("# This file create at " + new Date() + " ,by ErrorCodeUtil! \n");
        String lastClass = "";
        String currClass = "";      //当前正在处理的枚举类
        for (IBaseErrorEnum iEnum : enums) {
            currClass = iEnum.getClass().getName();         //通过反射获取到当前枚举的类名
            if (!lastClass.equals(currClass)) {
                if (!lastClass.isEmpty()) {
                    lines.add("\n");                        //添加一个换行
                }
                lines.add("#" + currClass + "\n");          //添加一个当前枚举类的注释
                lastClass = currClass;
            }
            lines.add(ErrorCodeUtil.installResourceName(iEnum) + "\n"); //生成枚举对应的键值对
        }
        //将上面的枚举值生成文本文件,以便拷贝到项目里面来用
        FileUtil.writeFileByLines(lines, pathanme);         //将所有的键值对写出!
        System.out.println("文件写出完毕!");
        System.out.println("请到此目录找生成的文件:" + pathanme);
        return pathanme;
    }

    public static void main(String[] args) throws Exception {
        //生成的错误码文件的存放位置,直接在项目的指定位置,注意,如果手动修改过此文件,谨慎执行此main方法.
        String fileName = "CommErrorCode";

        //所有要生成Properties文件的枚举类,将其加入列表中
        List<IBaseErrorEnum> enums = new ArrayList<IBaseErrorEnum>();
        enums.addAll(Arrays.asList(CommonEnum.values()));
        enums.addAll(Arrays.asList(CommHttpStatusEnum.values()));
        enums.addAll(Arrays.asList(CommDBEnum.values()));
        enums.addAll(Arrays.asList(CommLoginEnum.values()));
        enums.addAll(Arrays.asList(CommAPIEnum.values()));
        enums.addAll(Arrays.asList(CommFileEnum.values()));
        enums.addAll(Arrays.asList(CommEmailEnum.values()));
        enums.addAll(Arrays.asList(CommSMSEnum.values()));
        enums.addAll(Arrays.asList(CommActivitiEnum.values()));
        enums.addAll(Arrays.asList(CommADSearchEnum.values()));

        Entityi18nUtil.copyToLocale(geni18nPropertiesFile(fileName, enums));
        Entityi18nUtil.copyToLocale("src/main/resources/i18n/ValidationMessages.properties");

    }
}
