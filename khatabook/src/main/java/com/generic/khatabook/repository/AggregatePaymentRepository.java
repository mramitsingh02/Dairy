package com.generic.khatabook.repository;

import com.generic.khatabook.entity.AggrePayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AggregatePaymentRepository extends JpaRepository<AggrePayment, Long> {
}
