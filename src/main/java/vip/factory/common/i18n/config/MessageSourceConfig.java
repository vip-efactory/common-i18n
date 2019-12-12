package vip.factory.common.i18n.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.Locale;

/**
 * 国际化配置
 */
@Configuration
public class MessageSourceConfig {

    /*
     *  关于国际化处理器，Spring-webmvc5.2.2中支持：CookieLocaleResolver，SessionLocaleResolver，FixedLocaleResolver，AcceptHeaderLocaleResolver
    此处我使用AcceptHeaderLocaleResolver
     */
    @Bean
    public AcceptHeaderLocaleResolver localeResolver() {
        AcceptHeaderLocaleResolver ahlr = new AcceptHeaderLocaleResolver();
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
}
