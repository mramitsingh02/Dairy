package com.generic.khatabook.specification.repository;

import com.generic.khatabook.specification.entity.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecificationManagementRepository extends JpaRepository<Specification, String> {
}
