package xyz.anfun.customer_service.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class SpringContextUtil implements ApplicationContextAware {
    /**
     * 获取spring容器，以访问容器中定义的其他bean
     */
    private static ApplicationContext context;
    /**
     * 实现ApplicationContextAware接口的回调方法，设置上下文环境
     *
     * @param context
     */
    @Override
    public void setApplicationContext(ApplicationContext context)
            throws BeansException {
        SpringContextUtil.context = context;
    }

    /**
     * @return ApplicationContext
     */
    public static ApplicationContext getApplicationContext() {
        return context;
    }
    /**
     * 获取对象
     * 这里重写了bean方法，起主要作用
     * @param beanName
     * @return Object 一个以所给名字注册的bean的实例
     * @throws BeansException
     */
    public static<T> T getBean(String beanName) throws BeansException{
        return (T) context.getBean(beanName);
    }

    //通过class获取Bean.
    public static <T> T getBean(Class<T> clazz){
        return getApplicationContext().getBean(clazz);
    }

    //通过name,以及Clazz返回指定的Bean
    public static <T> T getBean(String name,Class<T> clazz){
        return getApplicationContext().getBean(name, clazz);
    }

	public static void destroy() {
		context= null;
	}
    public static String getMessage(String key) {
        return context.getMessage(key, null, Locale.getDefault());
    }
}
