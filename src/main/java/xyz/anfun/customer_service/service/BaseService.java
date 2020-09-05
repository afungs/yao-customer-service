package xyz.anfun.customer_service.service;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface BaseService<T, ID> {
    public T save(T obj);

    public List<T> saveAll(Iterable<T> list);

    public T getOne(ID id);

    public T findById(ID id);

    public void deleteById(ID id);

    public void deleteAll(List list);

    public void delete(T obj);

    public boolean existsById(ID id);

    public long count();

    public List<T> findAll();
    public List<T> findAll(T obj);
    public List<T> findAllById(Iterable<ID> ids);
    public List<T> findAll(Example<T> e);
    public List<T> findAll(Example<T> e, Sort sort);
    public Page<T> findAll(Pageable page);
    public Page<T> findAll(Example<T> e, Pageable page);
    public Page<T> findAll(T obj, Pageable page);
}
