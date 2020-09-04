# common-i18n
Common internationalized basic components, part of the JAVA EE project;


The Spring 5.2.2 version supports the scheme org.springframework.util.ResourceUtils for accessing files by default.
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

# SpecialThanksTo
- Thanks to the international enumeration example written by Yang Jie, this component is simplified and encapsulated based on the source code. Thanks again!
