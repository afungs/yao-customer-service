package xyz.anfun.customer_service.service.impl;

import xyz.anfun.customer_service.repository.UserRepository;
import xyz.anfun.customer_service.service.UserService;
import xyz.anfun.customer_service.entity.User;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.*;

import java.util.Optional;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author afungs
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository rep;


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
}
