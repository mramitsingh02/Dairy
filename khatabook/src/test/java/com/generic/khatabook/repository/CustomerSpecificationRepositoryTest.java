package com.generic.khatabook.repository;

import com.generic.khatabook.entity.CustomerProductSpecification;
import com.generic.khatabook.entity.CustomerSpecification;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
class CustomerSpecificationRepositoryTest {

    @Autowired
    CustomerSpecificationRepository customerSpecificationRepository;

    @Autowired
    TestEntityManager entityManager;

    @Test
    void shouldSuccessfulInsertSuccessfulCustomerSpecification() {

        CustomerProductSpecification milkProd = CustomerProductSpecification.builder()
                .productId("milkId")
                .price(BigDecimal.valueOf(40))
                .quantity(5.0f)
                .unitOfMeasurement("L")
                .build();
        CustomerProductSpecification sugerProd = CustomerProductSpecification.builder()
                .productId("sugerId")
                .price(BigDecimal.valueOf(40))
                .quantity(3.0f)
                .unitOfMeasurement("L")
                .build();
        CustomerProductSpecification roomProd = CustomerProductSpecification.builder()
                .productId("roomId")
                .price(BigDecimal.valueOf(5000))
                .quantity(2.0f)
                .unitOfMeasurement("I")
                .build();
        CustomerProductSpecification meterProd = CustomerProductSpecification.builder()
                .productId("milkId")
                .price(BigDecimal.valueOf(10))
                .quantity(1.0f)
                .unitOfMeasurement("R")
                .build();


        CustomerSpecification customerSpecification = CustomerSpecification.builder()
                .customerSpecificationId("customerSpecificationId")
                .customerId("customerId")
                .khatabookId("khatabookId")
                .createdOn(LocalDateTime.now())
                .description("Amit Specification")
                .customerProductSpecifications(List.of(milkProd, sugerProd, roomProd, meterProd))
                .build();
        customerSpecificationRepository.save(customerSpecification);
        List<CustomerSpecification> custSpec = customerSpecificationRepository.findByCustomerId("customerId");
        assertNotNull(custSpec);
        CustomerSpecification customerSpecification1 = custSpec.get(0);
        assertEquals(customerSpecification1.getCustomerId(), customerSpecification.getCustomerId());
        assertEquals(customerSpecification1.getCustomerId(), customerSpecification.getCustomerId());

        customerSpecification1.setSpecificationName("New Update");
        customerSpecificationRepository.save(customerSpecification1);
        custSpec = customerSpecificationRepository.findByCustomerId("customerId");
        assertNotNull(custSpec);

    }


}