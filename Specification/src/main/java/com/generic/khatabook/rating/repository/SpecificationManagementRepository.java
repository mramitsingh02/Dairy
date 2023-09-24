package com.generic.khatabook.rating.repository;

import com.generic.khatabook.rating.entity.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecificationManagementRepository extends JpaRepository<Specification, String> {
}
