package com.monco.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @Auther: monco
 * @Date: 2019/3/26 16:53
 * @Description: JWT令牌
 */
public class JwtAuthToken implements AuthenticationToken {

    private String token;

    public JwtAuthToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
