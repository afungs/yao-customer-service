package xyz.anfun.customer_service.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.anfun.customer_service.service.UserService;
import xyz.anfun.customer_service.util.JSONUtils;
import xyz.anfun.customer_service.util.Response;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/me")
    public Response me(Authentication authentication) throws JsonProcessingException {
        Response response = new Response();
        response.setStatus(200);
        response.setData(userService.findByUserName((String) authentication.getPrincipal()));
        return response;
    }

}
