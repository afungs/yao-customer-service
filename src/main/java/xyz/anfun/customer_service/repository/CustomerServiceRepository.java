package xyz.anfun.customer_service.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import xyz.anfun.customer_service.entity.CustomerService;
import org.springframework.data.jpa.repository.JpaRepository;

/**
@author afungs
*/
@Repository
public interface CustomerServiceRepository extends JpaRepository<CustomerService, Long> {

    public CustomerService findByUserId(Long userId);

    @Query("select c " +
            "from User u ,CustomerService c " +
            "where u.userName = :username and u.id = c.user.id")
    public CustomerService myFindByUserName(@Param("username") String userName);

    // 查找有空闲的客服
    @Query(value = "select cs " +
            "from CustomerService cs " +
            "where cs.online = 1 and cs.visitorsNum < cs.autoReceiveCustomerNum")
    public CustomerService myFindVisitors();

    @Query(value = "select * " +
            "from cs_customer_services " +
            "where online = 1 and queue_up_num = (" +
            "SELECT " +
            "MIN(queue_up_num) " +
            "FROM " +
            "cs_customer_services) limit 1", nativeQuery = true)
    public CustomerService myFindQueueUp();
}
