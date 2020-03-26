# common-i18n
通用的国际化基础组件，是JAVA EE项目的一部分

Spring5.2.2版本默认支持访问文件的scheme  
org.springframework.util.ResourceUtils都列举出来了
```
 classpath:
 file:
 jar:
 war:
 file
 jar
 war
 zip
 wsjar
 vfszip
 vfsfile
 vfs
	/** File extension for a regular jar file: ".jar". */
	public static final String JAR_FILE_EXTENSION = ".jar";

	/** Separator between JAR URL and file path within the JAR: "!/". */
	public static final String JAR_URL_SEPARATOR = "!/";

	/** Special separator between WAR URL and jar part on Tomcat. */
	public static final String WAR_URL_SEPARATOR = "*/";
```

# 特别感谢
- 感谢杨杰曾经编写的国际化枚举范例，本组件就是基于源代码简化，封装的。再次特别感谢！
