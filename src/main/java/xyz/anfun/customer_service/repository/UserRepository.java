package xyz.anfun.customer_service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import xyz.anfun.customer_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
@author afungs
*/
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserName(String userName);

    User findByWeChatOpenId(String weChatOpenId);

    Page<User> findAllByRoleId(long role_id, Pageable pageable);

    User findById(long id);

    User findByUserNameAndPassword(String userName, String password);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update User u set u.status = ?2 where u.id = ?1")
    int setStatus(long id, int status);
}
