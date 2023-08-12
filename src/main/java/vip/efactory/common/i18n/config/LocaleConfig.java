package vip.efactory.common.i18n.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import vip.efactory.common.i18n.service.ILocaleMsgSourceService;
import vip.efactory.common.i18n.service.LocaleMsgSourceServiceImpl;

import java.util.Locale;

/**
 * 国际化信息配置类
 * @author dusuanyun
 */
@Configuration
public class LocaleConfig implements WebMvcConfigurer {

    /*
     *  关于国际化处理器，Spring-webmvc5.2.2中支持：CookieLocaleResolver，SessionLocaleResolver，FixedLocaleResolver，AcceptHeaderLocaleResolver
     * 此处我使用：SessionLocaleResolver
     */
    @Bean
    public static LocaleResolver localeResolver() {
//        AcceptHeaderLocaleResolver ahlr = new AcceptHeaderLocaleResolver();
        SessionLocaleResolver ahlr = new SessionLocaleResolver();
        //设置默认区域, 中文简体
        ahlr.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
        return ahlr;
    }

    /**
     * 绑定加载国际化的资源文件
     */
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
        // 加载需要的国际化文件：错误码，校验信息，业务信息
        messageSource.setBasenames("classpath:i18n/CommErrorCode", "classpath:i18n/ValidationMessages", "classpath:i18n/messages");
        return messageSource;
    }

    /**
     * 绑定校验器需要的资源文件
     */
    @Bean
    public Validator getValidator(MessageSource messageSource) {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.setValidationMessageSource(messageSource);
        return validator;
    }

    /**
     * 这是一个专门用来处理国际化的组件
     * 以后可以优化使用配置的文件里的参数
     */
    @Bean
    public ILocaleMsgSourceService iLocaleMsgSourceService(MessageSource messageSource) {
        return new LocaleMsgSourceServiceImpl(messageSource);
    }

    /**
     * 区域解析拦截器，拦截到相关的参数，自动变更区域设置。
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        // 使用自己重写方法的解析器
        LocaleChangeInterceptor lci = new I18NLocaleChangeInterceptor();
        // 设置请求地址的参数,默认为：locale
        // lci.setParamName(LocaleChangeInterceptor.DEFAULT_PARAM_NAME);
        return lci;
    }

    /**
     * 注册区域拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}
