package xyz.anfun.customer_service.service;

import xyz.anfun.customer_service.entity.User;

import xyz.anfun.customer_service.service.BaseService;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.*;
import java.util.Optional;


/**
@author afungs
*/
public interface UserService extends BaseService<User, Long>  {
    public User findByUserName(String userName);


}
