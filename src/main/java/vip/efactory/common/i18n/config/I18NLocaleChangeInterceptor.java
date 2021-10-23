package vip.efactory.common.i18n.config;

import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 默认的LocaleChangeInterceptor只能从请求中获取区域信息，这样是不够的
 * 此处继承它，重写相关的方法，实现从header头中和请求中获取区域locale信息
 * @author dusuanyun
 */
public class I18NLocaleChangeInterceptor extends LocaleChangeInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws ServletException {
        // 先从请求头中获取国际化的区域信息
        String headerLocale = request.getHeader(getParamName());
        String requestLocale = request.getParameter(getParamName());
        // 若请求头中没有国际化参数，则从请求中获取，若都没有则为null
        String parseLocale = StringUtils.isEmpty(headerLocale) ? (StringUtils.isEmpty(requestLocale) ? null : requestLocale) : headerLocale;
        if (parseLocale != null) {
            if (checkHttpMethod(request.getMethod())) {
                LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
                if (localeResolver == null) {
                    throw new IllegalStateException(
                            "No LocaleResolver found: not in a DispatcherServlet request?");
                }
                try {
                    localeResolver.setLocale(request, response, parseLocaleValue(parseLocale));
                } catch (IllegalArgumentException ex) {
                    if (isIgnoreInvalidLocale()) {
                        if (logger.isDebugEnabled()) {
                            logger.debug("Ignoring invalid locale value [" + parseLocale + "]: " + ex.getMessage());
                        }
                    } else {
                        throw ex;
                    }
                }
            }
        }
        // Proceed in any case.
        return true;
    }

    /**
     * 和父类中的方法一样，之所以在此处出现，是因为方法为私有，但是又需要用到.
     */
    private boolean checkHttpMethod(String currentMethod) {
        String[] configuredMethods = getHttpMethods();
        if (ObjectUtils.isEmpty(configuredMethods)) {
            return true;
        }
        for (String configuredMethod : configuredMethods) {
            if (configuredMethod.equalsIgnoreCase(currentMethod)) {
                return true;
            }
        }
        return false;
    }
}
