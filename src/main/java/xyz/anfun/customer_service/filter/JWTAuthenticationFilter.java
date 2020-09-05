package xyz.anfun.customer_service.filter;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;
import xyz.anfun.customer_service.security.authentication.UserAuthenticationToken;
import xyz.anfun.customer_service.security.entity.JwtUser;
import xyz.anfun.customer_service.util.JSONUtils;
import xyz.anfun.customer_service.util.JwtTokenUtils;
import xyz.anfun.customer_service.util.Response;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        BufferedReader streamReader = new BufferedReader( new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8));
        StringBuilder responseStrBuilder = new StringBuilder();
        String inputStr;
        while ((inputStr = streamReader.readLine()) != null)
            responseStrBuilder.append(inputStr);
        JsonNode jsonNode = JSONUtils.jsonToObject(responseStrBuilder.toString());

        String type = jsonNode.get("type").asText();

        Map<String, String> map = new HashMap<>();

        if(type.equals("user")) {
            String userName = jsonNode.get("user_name").asText();
            String password = jsonNode.get("password").asText();
            if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)){
                throw new BadCredentialsException("参数错误");
            }
            return authenticationManager.authenticate(
                new UserAuthenticationToken(userName, password)
            );
        }
        throw new BadCredentialsException("不支持的登录方式");
    }

    // 成功验证后调用的方法
    // 如果验证成功，就生成token并返回
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        JwtUser jwtUser = (JwtUser) authResult.getPrincipal();
        boolean isRemember = true;

        String role = "";
        // 因为在JwtUser中存了权限信息，可以直接获取，由于只有一个角色就这么干了
        Collection<? extends GrantedAuthority> authorities = jwtUser.getAuthorities();
        for (GrantedAuthority authority : authorities){
            System.out.println(authority.getAuthority());
            role = authority.getAuthority();
        }
        // 根据用户名，角色创建token
        String token = JwtTokenUtils.createToken(jwtUser.getUsername(), jwtUser.getId(), role, isRemember);

        // 返回创建成功的token
        // 但是这里创建的token只是单纯的token
        // 按照jwt的规定，最后请求的格式应该是 `Bearer token`
//        response.setHeader("token", JwtTokenUtils.TOKEN_PREFIX + token);

        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        response.getWriter().write(JSONUtils.objectToString(new Response(200, JwtTokenUtils.TOKEN_PREFIX + token, "登陆成功!", null)));
    }

	// 这是验证失败时候调用的方法
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {

        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        response.getWriter().write(JSONUtils.objectToString(Response.errResponse(402,  failed.getMessage())));
    }
}
