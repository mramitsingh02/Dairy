package com.generic.khatabook.specification.repository;

import com.generic.khatabook.specification.entity.CustomerSpecification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerSpecificationRepository extends JpaRepository<CustomerSpecification, String> {

    List<CustomerSpecification> findByCustomerId(String customerId);

    List<CustomerSpecification> findByKhatabookIdAndCustomerId(final String khatabookId, String customerId);
}