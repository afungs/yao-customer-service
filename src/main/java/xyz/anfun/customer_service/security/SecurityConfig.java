package xyz.anfun.customer_service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.header.HeaderWriterFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import xyz.anfun.customer_service.filter.CorsFilter;
import xyz.anfun.customer_service.filter.JWTAuthenticationFilter;
import xyz.anfun.customer_service.filter.JWTAuthorizationFilter;
import xyz.anfun.customer_service.security.handler.AjaxLogoutSuccessHandler;
import xyz.anfun.customer_service.security.provider.UserLoginAuthenticationProvider;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    // 因为UserDetailsService的实现类实在太多啦，这里设置一下我们要注入的实现类
    @Qualifier("userDetailsServiceImpl")
    private UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(UserLoginAuthenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .cors()
                .and()
                .authorizeRequests()
                .anyRequest().permitAll()
                .and()
                .formLogin()
                    .disable()
                .logout()
                    .logoutSuccessHandler(AjaxLogoutSuccessHandler())
                .and()
                // 跨域
//                .addFilterBefore(new CorsFilter(), HeaderWriterFilter.class)
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                // 不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }

    @Bean
    public AjaxLogoutSuccessHandler AjaxLogoutSuccessHandler(){
        return new AjaxLogoutSuccessHandler();
    }

    @Bean
    public UserLoginAuthenticationProvider UserLoginAuthenticationProvider() {
        return new UserLoginAuthenticationProvider();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*", "http://192.168.3.12:10086"));
        configuration.setAllowedMethods(Arrays.asList("PUT", "POST", "GET", "OPTIONS", "DELETE"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("Authorization"));
        configuration.setExposedHeaders(Arrays.asList("Authorization"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Override
    @Bean
    //注入authenticationManager
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

}
