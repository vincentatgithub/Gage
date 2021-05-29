package cn.miroot.cloud.authorizationserver.authorization.provider;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.TokenRequest;

/**
 * @author fupan
 */
public class GageTokenGranter implements TokenGranter {
    @Override
    public OAuth2AccessToken grant(String s, TokenRequest tokenRequest) {
        return null;
    }
}
