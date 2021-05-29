package cn.miroot.cloud.authorizationserver.authorization.provider;

import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

/**
 * @author fupan
 */
@Service
public class GageClientDetailServiceImpl implements ClientDetailsService {
    @Override
    public GageClientDetails loadClientByClientId(String var1) throws ClientRegistrationException {
        return null;
    }
}
