package org.khr.anzenauth.base.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * 设置Anonymous注解允许匿名访问的url
 *
 * @author kk
 */
@Configuration
public class PermitAllUrlProperties implements InitializingBean, ApplicationContextAware {

    private static final Pattern PATH_VARIABLE_PATTERN = Pattern.compile("\\{(.*?)}");

    private ApplicationContext applicationContext;

    @Setter
    @Getter
    private List<String> urls = new ArrayList<>();

    private static final String ASTERISK = "*";

    @Override
    public void afterPropertiesSet() {
        RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = mapping.getHandlerMethods();

        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethods.entrySet()) {
            RequestMappingInfo info = entry.getKey();
            HandlerMethod method = entry.getValue();

            boolean isAnonymous = isAnonymous(method);
            if (isAnonymous && info.getPathPatternsCondition() != null) {
                info.getPathPatternsCondition().getPatternValues().forEach(url ->
                    urls.add(replacePathVariables(url))
                );
            }
        }
    }

    /**
     * 判断方法或类上是否有 @Anonymous 注解
     */
    private boolean isAnonymous(HandlerMethod method) {
        return Optional.ofNullable(AnnotationUtils.findAnnotation(method.getMethod(), Anonymous.class)).isPresent() ||
            Optional.ofNullable(AnnotationUtils.findAnnotation(method.getBeanType(), Anonymous.class)).isPresent();
    }

    /**
     * 替换 URL 中的 path variable 为 *
     */
    private String replacePathVariables(String url) {
        return PATH_VARIABLE_PATTERN.matcher(url).replaceAll(ASTERISK);
    }

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        this.applicationContext = context;
    }
}
