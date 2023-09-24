package com.generic.khatabook.rating.repository;

import com.generic.khatabook.rating.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductManagementRepository extends JpaRepository<Product, String> {

    List<Product> findByName(String name);

    List<Product> findByUnitOfMeasurement(final String unitOfMeasurement);
}