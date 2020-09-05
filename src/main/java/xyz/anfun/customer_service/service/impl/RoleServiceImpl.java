package xyz.anfun.customer_service.service.impl;

import xyz.anfun.customer_service.repository.RoleRepository;
import xyz.anfun.customer_service.service.RoleService;
import xyz.anfun.customer_service.entity.Role;

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
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleRepository rep;


    public Role save(Role obj) {
        return rep.save(obj);
    }


    @Transactional
    public List<Role> saveAll(Iterable<Role> list) {
        return rep.saveAll(list);
    }


    public Role getOne(Long id) {
        return rep.getOne(id);
    }


    public Role findById(Long id) {
        Optional<Role> obj = rep.findById(id);
        return obj.isPresent() ? obj.get() : null;
    }


    public void deleteById(Long id) {
        rep.deleteById(id);
    }


    @Transactional
    public void deleteAll(List list) {
        rep.deleteAll(list);
    }


    public void delete(Role obj) {
        rep.delete(obj);
    }


    public boolean existsById(Long id) {
        return rep.existsById(id);
    }


    public long count() {
        return rep.count();
    }


    public List<Role> findAll() {
        return rep.findAll();
    }


    public List<Role> findAll(Role obj) {
        List<Role> list = rep.findAll(Example.of(obj));
        return list == null || list.size() < 1 ? null : list;
    }


    public List<Role> findAll(Sort sort) {
        return rep.findAll(sort);
    }


    public List<Role> findAllById(Iterable<Long> ids) {
        return rep.findAllById(ids);
    }


    public List<Role> findAll(Example<Role> e) {
        return rep.findAll(e);
    }


    public List<Role> findAll(Example<Role> e, Sort sort) {
        return rep.findAll(e, sort);
    }


    public Page<Role> findAll(Pageable page) {
        return rep.findAll(page);
    }


    public Page<Role> findAll(Example<Role> e, Pageable page) {
        return rep.findAll(e, page);
    }


    public Page<Role> findAll(Role obj, Pageable page) {
        return rep.findAll(Example.of(obj), page);
    }


}
