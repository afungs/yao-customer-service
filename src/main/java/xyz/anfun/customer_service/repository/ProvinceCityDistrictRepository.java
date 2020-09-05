package xyz.anfun.customer_service.repository;

import org.springframework.stereotype.Repository;
import xyz.anfun.customer_service.entity.ProvinceCityDistrict;
import org.springframework.data.jpa.repository.JpaRepository;

/**
@author afungs
*/
@Repository
public interface ProvinceCityDistrictRepository extends JpaRepository<ProvinceCityDistrict, Long> {

}
