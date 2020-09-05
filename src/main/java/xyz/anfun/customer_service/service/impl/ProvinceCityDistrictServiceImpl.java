package xyz.anfun.customer_service.service.impl;

import xyz.anfun.customer_service.repository.ProvinceCityDistrictRepository;
import xyz.anfun.customer_service.service.ProvinceCityDistrictService;
import xyz.anfun.customer_service.entity.ProvinceCityDistrict;

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
public class ProvinceCityDistrictServiceImpl implements ProvinceCityDistrictService {

    @Resource
    private ProvinceCityDistrictRepository rep;


    public ProvinceCityDistrict save(ProvinceCityDistrict obj) {
        return rep.save(obj);
    }


    @Transactional
    public List<ProvinceCityDistrict> saveAll(Iterable<ProvinceCityDistrict> list) {
        return rep.saveAll(list);
    }


    public ProvinceCityDistrict getOne(Long id) {
        return rep.getOne(id);
    }


    public ProvinceCityDistrict findById(Long id) {
        Optional<ProvinceCityDistrict> obj = rep.findById(id);
        return obj.isPresent() ? obj.get() : null;
    }


    public void deleteById(Long id) {
        rep.deleteById(id);
    }


    @Transactional
    public void deleteAll(List list) {
        rep.deleteAll(list);
    }


    public void delete(ProvinceCityDistrict obj) {
        rep.delete(obj);
    }


    public boolean existsById(Long id) {
        return rep.existsById(id);
    }


    public long count() {
        return rep.count();
    }


    public List<ProvinceCityDistrict> findAll() {
        return rep.findAll();
    }


    public List<ProvinceCityDistrict> findAll(ProvinceCityDistrict obj) {
        List<ProvinceCityDistrict> list = rep.findAll(Example.of(obj));
        return list == null || list.size() < 1 ? null : list;
    }


    public List<ProvinceCityDistrict> findAll(Sort sort) {
        return rep.findAll(sort);
    }


    public List<ProvinceCityDistrict> findAllById(Iterable<Long> ids) {
        return rep.findAllById(ids);
    }


    public List<ProvinceCityDistrict> findAll(Example<ProvinceCityDistrict> e) {
        return rep.findAll(e);
    }


    public List<ProvinceCityDistrict> findAll(Example<ProvinceCityDistrict> e, Sort sort) {
        return rep.findAll(e, sort);
    }


    public Page<ProvinceCityDistrict> findAll(Pageable page) {
        return rep.findAll(page);
    }


    public Page<ProvinceCityDistrict> findAll(Example<ProvinceCityDistrict> e, Pageable page) {
        return rep.findAll(e, page);
    }


    public Page<ProvinceCityDistrict> findAll(ProvinceCityDistrict obj, Pageable page) {
        return rep.findAll(Example.of(obj), page);
    }


}
