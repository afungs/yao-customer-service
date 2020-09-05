package xyz.anfun.customer_service.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import xyz.anfun.customer_service.entity.User;
import xyz.anfun.customer_service.repository.UserRepository;
import xyz.anfun.customer_service.security.entity.JwtUser;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public JwtUser loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(s);
        if (user == null)
            return null;
        return new JwtUser(user);
    }

}
