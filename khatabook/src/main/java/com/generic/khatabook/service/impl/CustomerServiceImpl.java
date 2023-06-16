package com.generic.khatabook.service.impl;

import com.generic.khatabook.common.model.Container;
import com.generic.khatabook.entity.Customer;
import com.generic.khatabook.entity.CustomerProduct;
import com.generic.khatabook.exceptions.AppEntity;
import com.generic.khatabook.exceptions.NotFoundException;
import com.generic.khatabook.model.CustomerDTO;
import com.generic.khatabook.model.CustomerUpdatable;
import com.generic.khatabook.model.Product;
import com.generic.khatabook.model.ProductDTO;
import com.generic.khatabook.repository.CustomerProductRepository;
import com.generic.khatabook.repository.CustomerRepository;
import com.generic.khatabook.service.CustomerService;
import com.generic.khatabook.service.ProductService;
import com.generic.khatabook.service.mapper.CustomerMapper;
import com.generic.khatabook.service.mapper.ProductMapper;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
    private CustomerProductRepository myCustomerProductRepository;


    @Autowired
    private ProductService myProductService;


    @Autowired
    private CustomerMapper myCustomerMapper;

    //Observability is the ability to measure the internal state of a system only by its external outputs
    //https://www.baeldung.com/spring-boot-3-observability
    @Autowired
    private ObservationRegistry myRegistry;

    @Override
    public boolean isValid(CustomerDTO customerDTO) {
        return myCustomerMapper.mapToDTO(myCustomerRepository.findByMsisdn(customerDTO.msisdn())) != null;
    }

    @Override
    public Container<CustomerDTO, CustomerUpdatable> getByCustomerId(final String customerId) {
        Customer customer = myCustomerRepository.findById(customerId).orElse(null);
        List<CustomerProduct> customerProducts = myCustomerProductRepository.findByCustomerId(customerId);
        if (customerProducts != null) {
            for (CustomerProduct customerProduct : customerProducts) {
                ProductDTO product = myProductService.getCustomerProduct(Product.of(customerProduct.getProductId()));
                customerProduct.setProductName(product.name());
            }
            customer.setProducts(customerProducts);
        }


        return myCustomerMapper.mapToContainer(customer);
    }

    private CustomerDTO getObservation(final String matrixName, final Supplier<CustomerDTO> supplierTask) {
        return Observation.createNotStarted(matrixName, myRegistry).observe(supplierTask);
    }

    @Override
    public CustomerDTO getByMsisdn(String msisdn) {
        return myCustomerMapper.mapToDTO(myCustomerRepository.findByMsisdn(msisdn));
    }

    @Override
    public CustomerDTO saveAndUpdate(CustomerDTO customerDTO) {
        Customer entity = myCustomerMapper.mapToEntity(customerDTO);
        if (customerDTO.products() != null) {
            entity.setProducts(customerDTO.products().stream().map(myProductService::getCustomerProduct).map(x -> ProductMapper.mapToProduct(x, entity)).toList());
        }
        return myCustomerMapper.mapToDTO(myCustomerRepository.save(entity));
    }


    @Override
    public CustomerDTO delete(String customerId, String msisdn) {
        Customer customer;
        if (customerId != null) {
            customer = myCustomerRepository.findById(customerId).orElseThrow(() -> new NotFoundException(AppEntity.ID, customerId));
        } else {
            customer = myCustomerRepository.findByMsisdn(msisdn);
        }
        myCustomerRepository.delete(customer);

        return myCustomerMapper.mapToDTO(customer);
    }

    @Override
    public Set<CustomerDTO> getAll(final String khatabookId) {
        return myCustomerRepository.findByKhatabookId(khatabookId).stream().map(myCustomerMapper::mapToDTO).collect(Collectors.toSet());
    }

    @Override
    public CustomerDTO getCustomerByMsisdn(final String khatabookId, final String msisdn) {
        return myCustomerMapper.mapToDTO(myCustomerRepository.findByKhatabookIdAndMsisdn(khatabookId, msisdn));
    }

}
