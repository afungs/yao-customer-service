package xyz.anfun.customer_service.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import xyz.anfun.customer_service.repository.UserRepository;
import xyz.anfun.customer_service.service.UserService;
import xyz.anfun.customer_service.entity.User;

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
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository rep;

    @Autowired
    private PropertiesUtils propertiesUtils;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public User save(User obj) {
        return rep.save(obj);
    }

    @Transactional
    public List<User> saveAll(Iterable<User> list) {
        return rep.saveAll(list);
    }


    public User getOne(Long id) {
        return rep.getOne(id);
    }


    public User findById(Long id) {
        Optional<User> obj = rep.findById(id);
        return obj.isPresent() ? obj.get() : null;
    }


    public void deleteById(Long id) {
        rep.deleteById(id);
    }


    @Transactional
    public void deleteAll(List list) {
        rep.deleteAll(list);
    }


    public void delete(User obj) {
        rep.delete(obj);
    }


    public boolean existsById(Long id) {
        return rep.existsById(id);
    }


    public long count() {
        return rep.count();
    }


    public List<User> findAll() {
        return rep.findAll();
    }


    public List<User> findAll(User obj) {
        List<User> list = rep.findAll(Example.of(obj));
        return list == null || list.size() < 1 ? null : list;
    }


    public List<User> findAll(Sort sort) {
        return rep.findAll(sort);
    }


    public List<User> findAllById(Iterable<Long> ids) {
        return rep.findAllById(ids);
    }


    public List<User> findAll(Example<User> e) {
        return rep.findAll(e);
    }


    public List<User> findAll(Example<User> e, Sort sort) {
        return rep.findAll(e, sort);
    }


    public Page<User> findAll(Pageable page) {
        return rep.findAll(page);
    }


    public Page<User> findAll(Example<User> e, Pageable page) {
        return rep.findAll(e, page);
    }


    public Page<User> findAll(User obj, Pageable page) {
        return rep.findAll(Example.of(obj), page);
    }


    @Override
    public User findByUserName(String userName) {
        return rep.findByUserName(userName);
    }

    @Override
    public List<User> findUsersByCustomerServiceUserName(String customerServiceUserName) {
        BoundHashOperations<String, String, Object> rcs = redisTemplate.boundHashOps(propertiesUtils.getRedisCustomerServicesKey());
        Map<String, ArrayList<String>> data = null;
        try {
            data = JSONUtils.jsonToObject((String) rcs.get(customerServiceUserName), new TypeReference<HashMap<String, ArrayList<String>>>() {});
            List<User> users = new ArrayList<>();
            data.get("visitors").forEach(v -> {
                User user = rep.findByUserName(v);
                if (user != null){
                    users.add(user);
                }
            });
            return users;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }
}
