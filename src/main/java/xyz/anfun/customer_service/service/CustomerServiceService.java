package xyz.anfun.customer_service.service;

import xyz.anfun.customer_service.entity.CustomerService;

import xyz.anfun.customer_service.service.BaseService;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.*;
import java.util.Optional;


/**
@author afungs
*/
public interface CustomerServiceService extends BaseService<CustomerService, Long>  {

    CustomerService findByUserId(Long userId);

    CustomerService findByUserName(String userName);

    // 分配客服
    CustomerService assign(String userName);
}
