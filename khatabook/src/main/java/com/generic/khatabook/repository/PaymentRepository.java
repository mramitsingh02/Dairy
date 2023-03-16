package com.generic.khatabook.repository;

import com.generic.khatabook.entity.CustomerPayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface PaymentRepository extends JpaRepository<CustomerPayment, Long> {
    Set<CustomerPayment> findByKhatabookId(String khatabookId);

    Set<CustomerPayment> findByKhatabookIdAndCustomerId(String khatabookId, String customerId);
}
