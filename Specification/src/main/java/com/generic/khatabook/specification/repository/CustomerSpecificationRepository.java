package com.generic.khatabook.specification.repository;

import com.generic.khatabook.specification.entity.CustomerSpecification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerSpecificationRepository extends JpaRepository<CustomerSpecification, Long> {

}