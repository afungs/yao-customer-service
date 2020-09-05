package xyz.anfun.customer_service.util;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@Component
@PropertySource("classpath:application.yml")
public class PropertiesUtils {
    @Value("${upload-path}")
    private String fileRoot;

    @Value("${web-url}")
    private String webUrl;

    @Value("${redis.customer-service-key}")
    private String redisCustomerServicesKey;

    @Value("${redis.users-key}")
    private String redisUsersKey;
}
