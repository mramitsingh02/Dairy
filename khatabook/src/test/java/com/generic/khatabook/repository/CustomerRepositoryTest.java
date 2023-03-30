package com.generic.khatabook.repository;

import com.generic.khatabook.entity.Customer;
import com.generic.khatabook.entity.Khatabook;
import io.micrometer.observation.ObservationRegistry;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class CustomerRepositoryTest {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private KhatabookRepository khatabookRepository;

    @MockBean
    private ObservationRegistry myRegistry;

    private Customer customer;
    private Khatabook khatabook;

    @BeforeEach
    public void setUp() {
        khatabook = new Khatabook(null, UUID.randomUUID().toString(),UUID.randomUUID().toString(),
                                  UUID.randomUUID().toString(),UUID.randomUUID().toString(),
                                  UUID.randomUUID().toString(),"Y", LocalDateTime.now(Clock.systemDefaultZone()),
                                  null, null);

        customer = new Customer(UUID.randomUUID().toString(), UUID.randomUUID().toString(), "msisdn", "Amit", "Singh",
                                LocalDateTime.now(Clock.systemDefaultZone()), null, null, null, null);
    }

    @Test
    void createCustomer() {
        final Khatabook savedKhatabook = khatabookRepository.save(khatabook);
        customer.setKhatabookId(savedKhatabook.getKhatabookId());
        final Customer savedCustomer = customerRepository.save(customer);
        assertNotNull(savedCustomer.getCustomerId());
    }

    @AfterEach
    public void tearDown() {
        customerRepository.deleteAll();
        customer = null;
    }

}