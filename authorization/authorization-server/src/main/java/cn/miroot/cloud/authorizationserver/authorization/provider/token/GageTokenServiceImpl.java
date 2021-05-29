package cn.miroot.cloud.authorizationserver.authorization.provider.token;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.stereotype.Component;

/**
 * @author fupan
 */
@Component
public class GageTokenServiceImpl implements AuthorizationServerTokenServices, ResourceServerTokenServices,
        ConsumerTokenServices, InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @Override
    public OAuth2AccessToken createAccessToken(OAuth2Authentication oAuth2Authentication) throws AuthenticationException {
        return null;
    }

    @Override
    public OAuth2AccessToken refreshAccessToken(String s, TokenRequest tokenRequest) throws AuthenticationException {
        return null;
    }

    @Override
    public OAuth2AccessToken getAccessToken(OAuth2Authentication oAuth2Authentication) {
        return null;
    }

    @Override
    public boolean revokeToken(String s) {
        return false;
    }

    @Override
    public OAuth2Authentication loadAuthentication(String s) throws AuthenticationException, InvalidTokenException {
        return null;
    }

    @Override
    public OAuth2AccessToken readAccessToken(String s) {
        return null;
    }
}
