package com.generic.khatabook.specification.services;

import com.generic.khatabook.common.model.Container;
import com.generic.khatabook.common.model.Containers;
import com.generic.khatabook.specification.model.CustomerSpecificationDTO;
import com.generic.khatabook.specification.model.CustomerSpecificationUpdatable;
import com.generic.khatabook.specification.repository.CustomerSpecificationRepository;
import com.generic.khatabook.specification.services.mapper.CustomerSpecificationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerSpecificationServiceImpl implements CustomerSpecificationService {

    private CustomerSpecificationRepository myCustomerSpecificationRepository;
    private CustomerSpecificationMapper myMapper;

    @Autowired
    public CustomerSpecificationServiceImpl(final CustomerSpecificationRepository thatCustomerSpecificationRepository,
                                            final CustomerSpecificationMapper customerSpecificationMapper) {
        this.myCustomerSpecificationRepository = thatCustomerSpecificationRepository;
        myMapper = customerSpecificationMapper;
    }

    @Override
    public Containers<CustomerSpecificationDTO, CustomerSpecificationUpdatable> getByCustomerId(final String customerId) {
        return myMapper.mapToContainers(myCustomerSpecificationRepository.findByCustomerId(customerId));
    }

    @Override
    public Containers<CustomerSpecificationDTO, CustomerSpecificationUpdatable> getCustomerSpecification(final String khatabookId,
                                                                                                         final String customerId) {
        return myMapper.mapToContainers(myCustomerSpecificationRepository.findByKhatabookIdAndCustomerId(khatabookId,
                                                                                                         customerId));
    }

    @Override
    public Container<CustomerSpecificationDTO, CustomerSpecificationUpdatable> getCustomerSpecification(final String specificationId) {
        return myMapper.mapToContainer(myCustomerSpecificationRepository.findById(specificationId).orElse(null));
    }

    @Override
    public CustomerSpecificationDTO update(final CustomerSpecificationDTO dto) {
        return myMapper.mapToDTO(myCustomerSpecificationRepository.save(myMapper.mapToEntity(dto)));
    }

    @Override
    public void delete(final CustomerSpecificationDTO customerSpecificationDTO) {
        myCustomerSpecificationRepository.delete(myMapper.mapToEntity(customerSpecificationDTO));
    }

    @Override
    public Container<CustomerSpecificationDTO, CustomerSpecificationUpdatable> get(final String id) {
        return myMapper.mapToContainer(myCustomerSpecificationRepository.findById(id).orElse(null));
    }

    @Override
    public CustomerSpecificationDTO save(final CustomerSpecificationDTO dto) {
        return myMapper.mapToDTO(myCustomerSpecificationRepository.save(myMapper.mapToEntity(dto)));
    }

}
