package com.generic.khatabook.service.impl;

import com.generic.khatabook.entity.Customer;
import com.generic.khatabook.exceptions.AppEntity;
import com.generic.khatabook.exceptions.NotFoundException;
import com.generic.khatabook.model.CustomerDTO;
import com.generic.khatabook.repository.CustomerRepository;
import com.generic.khatabook.service.CustomerService;
import com.generic.khatabook.service.mapper.CustomerMapper;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private EntityManagerFactory myEntityManagerFactory;

    @Autowired
    private CustomerRepository myCustomerRepository;
    @Autowired
    private CustomerMapper myCustomerMapper;

    //Observability is the ability to measure the internal state of a system only by its external outputs
    //https://www.baeldung.com/spring-boot-3-observability
    @Autowired
    private ObservationRegistry myRegistry;

    @Override
    public boolean isValid(CustomerDTO customerDTO) {
        return myCustomerMapper.mapToPojo(myCustomerRepository.existsByMsisdn(customerDTO.msisdn())) != null;
    }

    @Override
    public CustomerDTO getByCustomerId(final String customerId) {
        return getObservation("getByCustomerId", () -> myCustomerMapper.mapToPojo(myCustomerRepository.findByCustomerId(customerId)));
    }

    private CustomerDTO getObservation(final String matrixName, final Supplier<CustomerDTO> supplierTask) {
        return Observation.createNotStarted(matrixName, myRegistry).observe(supplierTask);
    }

    @Override
    public CustomerDTO getByMsisdn(String msisdn) {
        return myCustomerMapper.mapToPojo(myCustomerRepository.findByMsisdn(msisdn));
    }

    @Override
    public void create(CustomerDTO customerDTO) {
        myCustomerRepository.save(myCustomerMapper.mapToDTO(customerDTO));

    }

    @Override
    public CustomerDTO update(CustomerDTO customerDTO) {
        return myCustomerMapper.mapToPojo(myCustomerRepository.save(myCustomerMapper.mapToDTO(customerDTO)));
    }

    @Override
    public CustomerDTO delete(Long id, String msisdn) {
        Customer customer;
        if (id != null) {
            customer = myCustomerRepository.findById(id).orElseThrow(() -> new NotFoundException(AppEntity.ID, id));
        } else {
            customer = myCustomerRepository.findByMsisdn(msisdn);
        }
        myCustomerRepository.delete(customer);

        return myCustomerMapper.mapToPojo(customer);
    }

    @Override
    public Set<CustomerDTO> getAll(final String khatabookId) {
        return myCustomerRepository.findByKhatabookId(khatabookId).stream().map(myCustomerMapper::mapToPojo).collect(Collectors.toSet());
    }

    @Override
    public CustomerDTO getCustomerByMsisdn(final String khatabookId, final String msisdn) {
        return myCustomerMapper.mapToPojo(myCustomerRepository.findByKhatabookIdAndMsisdn(khatabookId, msisdn));
    }

}
