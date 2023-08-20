package com.generic.khatabook.specification.repository;

import com.generic.khatabook.specification.entity.ProductRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRatingRepository extends JpaRepository<ProductRating, Long> {

    List<ProductRating> findByProductId(String productId);

    List<ProductRating> findByCustomerId(String customerId);

}
