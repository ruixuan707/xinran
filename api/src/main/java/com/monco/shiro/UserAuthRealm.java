package com.monco.shiro;

import com.monco.core.entity.User;
import com.monco.core.manager.UserManager;
import com.monco.core.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * EmployeeAuthRealm
 *
 * @author Lijin
 * @version 1.0.0
 */
@Slf4j
@Service
public class UserAuthRealm extends AuthorizingRealm {

    @Lazy
    @Autowired
    private UserService userService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtAuthToken;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String token = (String) auth.getCredentials();
        String username = JwtComponent.getUsername(token);
        Long version = JwtComponent.getVersion(token);
        if (StringUtils.isBlank(username) || version == null) {
            throw new AuthenticationException("token 不合法");
        }
        User user = userService.getUserByUsername(username);
        if (user == null) {
            throw new AuthenticationException("token 不正确");
        }
        if (!JwtComponent.verify(token, username, user.getVersion(), user.getPassword())) {
            throw new AuthenticationException("token 不正确");
        }
        UserManager.remove();
        UserManager.set(user);
        return new SimpleAuthenticationInfo(token, token, "UserAuthRealm");
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }
}
