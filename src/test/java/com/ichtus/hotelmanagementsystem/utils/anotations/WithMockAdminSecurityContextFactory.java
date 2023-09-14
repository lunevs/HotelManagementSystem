package com.ichtus.hotelmanagementsystem.utils.anotations;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

final class WithMockAdminSecurityContextFactory implements WithSecurityContextFactory<WithMockAdmin> {

    private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();

    @Override
    public SecurityContext createSecurityContext(WithMockAdmin withMockAdmin) {
        String username = StringUtils.hasLength(withMockAdmin.username()) ? withMockAdmin.username() : withMockAdmin.value();
        Assert.notNull(username, () -> withMockAdmin + " cannot have null username on both username and value properties");
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (String authority : withMockAdmin.authorities()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(authority));
        }
        if (grantedAuthorities.isEmpty()) {
            for (String role : withMockAdmin.roles()) {
                Assert.isTrue(!role.startsWith("ROLE_"), () -> "roles cannot start with ROLE_ Got " + role);
                grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role));
            }
        }
        else if (!(withMockAdmin.roles().length == 1 && "ADMIN".equals(withMockAdmin.roles()[0]))) {
            throw new IllegalStateException("You cannot define roles attribute " + Arrays.asList(withMockAdmin.roles())
                    + " with authorities attribute " + Arrays.asList(withMockAdmin.authorities()));
        }
        User principal = new User(username, withMockAdmin.password(), true, true, true, true, grantedAuthorities);
        Authentication authentication = UsernamePasswordAuthenticationToken.authenticated(principal,
                principal.getPassword(), principal.getAuthorities());
        SecurityContext context = this.securityContextHolderStrategy.createEmptyContext();
        context.setAuthentication(authentication);
        return context;
    }
}
