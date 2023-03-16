package com.generic.khatabook.service.impl;

import com.generic.khatabook.entity.AggregatePayment;
import com.generic.khatabook.model.AggregatePaymentDTO;
import com.generic.khatabook.model.CustomerDTO;
import com.generic.khatabook.model.KhatabookDTO;
import com.generic.khatabook.repository.AggregatePaymentRepository;
import com.generic.khatabook.service.AggregatePaymentService;
import com.generic.khatabook.service.mapper.AggregatePaymentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class AggregatePaymentServiceImpl implements AggregatePaymentService {

    @Autowired
    private AggregatePaymentRepository myAggregatePaymentRepository;
    @Autowired
    private AggregatePaymentMapper myAggregatePaymentMapper;


    @Override
    public void paymentAggregate(final KhatabookDTO khatabook, final CustomerDTO customer, final AggregatePaymentDTO payment) {
        myAggregatePaymentRepository.save(myAggregatePaymentMapper.convertToEntity(payment, khatabook, customer));
    }

    @Override
    public AggregatePaymentDTO getLastAggregation(final KhatabookDTO khatabook, final CustomerDTO customer) {
        final AggregatePayment entity = myAggregatePaymentRepository.findOne(Example.of(AggregatePayment.builder().khatabookId(khatabook.khatabookId())
                .customerId(customer.customerId())
                .build())).orElse(null);
        assert entity != null;
        return myAggregatePaymentMapper.convertToDTO(entity);
    }
}
