package cn.miroot.cloud.authorizationserver.authorization.config;

import cn.miroot.cloud.authorizationserver.authorization.provider.GageTokenGranter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;

import javax.annotation.Resource;

/**
 * @author fupan
 */
@Configuration
public class InitializingBeanConfig {
    @Resource
    private CompositeTokenGranter compositeTokenGranter;

    @Bean
    public CompositeTokenGranter compositeTokenGranter() {
        compositeTokenGranter.addTokenGranter(new GageTokenGranter());
        return null;
    }
}
