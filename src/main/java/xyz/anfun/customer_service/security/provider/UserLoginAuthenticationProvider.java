package xyz.anfun.customer_service.security.provider;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import xyz.anfun.customer_service.security.authentication.UserAuthenticationToken;
import xyz.anfun.customer_service.security.entity.JwtUser;

import java.util.Collection;

@Slf4j
@Component
public class UserLoginAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	@Qualifier("userDetailsServiceImpl")
	private UserDetailsService userDetailsService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		// http请求的账户密码
		String username = authentication.getName();
		String password = (String) authentication.getCredentials();

		// 数据库根据用户名查询
		JwtUser userDetails = (JwtUser) userDetailsService.loadUserByUsername(username);

		if (userDetails == null || !userDetails.getPassword().equals(password)) {
			throw new BadCredentialsException("用户名或密码错误");
		}

		Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();

		return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), authorities);
	}

	/**
	 * 是否支持处理当前Authentication对象类似
	 */
	@Override
	public boolean supports(Class<?> authentication) {
		return UserAuthenticationToken.class.isAssignableFrom(authentication);
	}
}
