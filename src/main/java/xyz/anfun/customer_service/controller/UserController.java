package xyz.anfun.customer_service.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.anfun.customer_service.service.UserService;
import xyz.anfun.customer_service.util.JSONUtils;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/me")
    public String me(Authentication authentication) throws JsonProcessingException {
        return JSONUtils.objectToString(userService.findByUserName((String) authentication.getPrincipal()));
    }

}
