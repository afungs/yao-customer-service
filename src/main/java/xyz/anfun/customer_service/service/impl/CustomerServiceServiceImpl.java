package xyz.anfun.customer_service.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import xyz.anfun.customer_service.repository.CustomerServiceRepository;
import xyz.anfun.customer_service.service.CustomerServiceService;
import xyz.anfun.customer_service.entity.CustomerService;

import java.util.*;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.*;

import org.springframework.stereotype.Service;
import xyz.anfun.customer_service.util.JSONUtils;
import xyz.anfun.customer_service.util.PropertiesUtils;

import javax.annotation.Resource;

/**
 * @author afungs
 */
@Service
public class CustomerServiceServiceImpl implements CustomerServiceService {

    @Autowired
    private CustomerServiceRepository rep;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private PropertiesUtils propertiesUtils;

    public CustomerService save(CustomerService obj) {
        return rep.save(obj);
    }

    @Transactional
    public List<CustomerService> saveAll(Iterable<CustomerService> list) {
        return rep.saveAll(list);
    }


    public CustomerService getOne(Long id) {
        return rep.getOne(id);
    }


    public CustomerService findById(Long id) {
        Optional<CustomerService> obj = rep.findById(id);
        return obj.isPresent() ? obj.get() : null;
    }


    public void deleteById(Long id) {
        rep.deleteById(id);
    }


    @Transactional
    public void deleteAll(List list) {
        rep.deleteAll(list);
    }


    public void delete(CustomerService obj) {
        rep.delete(obj);
    }

    public boolean existsById(Long id) {
        return rep.existsById(id);
    }


    public long count() {
        return rep.count();
    }


    public List<CustomerService> findAll() {
        return rep.findAll();
    }


    public List<CustomerService> findAll(CustomerService obj) {
        List<CustomerService> list = rep.findAll(Example.of(obj));
        return list == null || list.size() < 1 ? null : list;
    }


    public List<CustomerService> findAll(Sort sort) {
        return rep.findAll(sort);
    }


    public List<CustomerService> findAllById(Iterable<Long> ids) {
        return rep.findAllById(ids);
    }


    public List<CustomerService> findAll(Example<CustomerService> e) {
        return rep.findAll(e);
    }


    public List<CustomerService> findAll(Example<CustomerService> e, Sort sort) {
        return rep.findAll(e, sort);
    }


    public Page<CustomerService> findAll(Pageable page) {
        return rep.findAll(page);
    }


    public Page<CustomerService> findAll(Example<CustomerService> e, Pageable page) {
        return rep.findAll(e, page);
    }


    public Page<CustomerService> findAll(CustomerService obj, Pageable page) {
        return rep.findAll(Example.of(obj), page);
    }

    @Override
    public CustomerService findByUserId(Long userId) {
        return rep.findByUserId(userId);
    }

    @Override
    public CustomerService findByUserName(String userName) {
        return rep.myFindByUserName(userName);
    }

    @Override
    public CustomerService assign(String userName) {
        BoundHashOperations<String, String, Object> ruser = redisTemplate.boundHashOps(propertiesUtils.getRedisUsersKey());
        // 如果用户已经被分配过了 就直接返回客服对象
        if (!StringUtils.isEmpty(ruser.get(userName))){
            return this.findByUserName((String) ruser.get(userName));
        }

        BoundHashOperations<String, String, Object> rcs = redisTemplate.boundHashOps(propertiesUtils.getRedisCustomerServicesKey());

        CustomerService cs = rep.myFindVisitors();
        String key = "visitors";
        if (cs == null){
            cs = rep.myFindQueueUp();
            key = "queue_up";
        }

        try {
            Map<String, ArrayList<String>> data =  JSONUtils.jsonToObject((String) rcs.get(cs.getUser().getUserName()), new TypeReference<HashMap<String, ArrayList<String>>>() {});
            data.get(key).forEach(System.out::println);
            // 如果该用户没有被分配到这个用户下就建立关系
            if (!data.get(key).contains(userName)){
                data.get(key).add(userName);
                rcs.put(cs.getUser().getUserName(), JSONUtils.objectToString(data));
            }
            ruser.put(userName, cs.getUser().getUserName());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return cs;
    }

}
