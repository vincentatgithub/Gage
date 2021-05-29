package cn.miroot.cloud.authorizationserver.authorization.provider;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author fupan
 */
@Service
public class GageUserDetailServiceImpl implements UserDetailsService {
    @Override
    public GageUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
