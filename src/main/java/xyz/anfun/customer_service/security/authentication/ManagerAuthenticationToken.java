package xyz.anfun.customer_service.security.authentication;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class ManagerAuthenticationToken extends UsernamePasswordAuthenticationToken {
    public ManagerAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }
}
