package cn.miroot.cloud.authorizationserver.authorization.config;

import cn.miroot.cloud.authorizationserver.authorization.provider.GageClientDetailServiceImpl;
import cn.miroot.cloud.authorizationserver.authorization.provider.GageTokenGranter;
import cn.miroot.cloud.authorizationserver.authorization.provider.GageUserDetailServiceImpl;
import cn.miroot.cloud.authorizationserver.authorization.provider.token.GageAccessTokenConverter;
import cn.miroot.cloud.authorizationserver.authorization.provider.token.GageTokenServiceImpl;
import cn.miroot.cloud.authorizationserver.authorization.provider.token.store.GageTokenStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

import javax.annotation.Resource;

/**
 * @author fupan
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
//    @Resource
//    private AuthenticationManager authenticationManager;
    @Resource
    private GageAccessTokenConverter accessTokenConverter;
    @Resource
    private GageClientDetailServiceImpl gageClientDetailService;
    @Resource
    private GageUserDetailServiceImpl userDetailService;
    @Resource
    private GageTokenServiceImpl tokenService;
    @Resource
    private GageTokenStore tokenStore;

    @Autowired
    public void wriedGageTokenServiceImpl(GageTokenServiceImpl var1) {
        this.tokenService = var1;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer var1) throws Exception {
        var1.withClientDetails(gageClientDetailService);
    }


    @Override
    public void configure(AuthorizationServerEndpointsConfigurer var1) {
        var1
                .userDetailsService(userDetailService)
                .accessTokenConverter(accessTokenConverter)
                .tokenStore(tokenStore)
                .tokenServices(tokenService)
                .tokenGranter(new GageTokenGranter());
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer var1) {
        var1.allowFormAuthenticationForClients()
                .checkTokenAccess("permitAll()")
                .tokenKeyAccess("isAuthenticated()");
    }

}
