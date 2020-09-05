package xyz.anfun.customer_service.security.authentication;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class UserAuthenticationToken extends UsernamePasswordAuthenticationToken {
    public UserAuthenticationToken(Object principal, Object credentials){
        super(principal,credentials);
    }
}
