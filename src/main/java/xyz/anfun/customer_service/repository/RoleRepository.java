package xyz.anfun.customer_service.repository;

import org.springframework.stereotype.Repository;
import xyz.anfun.customer_service.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
@author afungs
*/
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}
