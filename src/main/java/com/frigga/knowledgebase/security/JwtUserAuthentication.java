package com.frigga.knowledgebase.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;

public class JwtUserAuthentication extends AbstractAuthenticationToken {

    private final String userId;

    public JwtUserAuthentication(String userId) {
        super(AuthorityUtils.NO_AUTHORITIES);
        this.userId = userId;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return userId;
    }
}
