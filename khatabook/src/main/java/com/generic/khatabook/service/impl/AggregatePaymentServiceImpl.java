package com.generic.khatabook.service.impl;

import com.generic.khatabook.entity.AggregatePayment;
import com.generic.khatabook.entity.CustomerPayment;
import com.generic.khatabook.model.AggregatePaymentDTO;
import com.generic.khatabook.model.CustomerDTO;
import com.generic.khatabook.model.KhatabookDTO;
import com.generic.khatabook.repository.AggregatePaymentRepository;
import com.generic.khatabook.repository.PaymentRepository;
import com.generic.khatabook.service.AggregatePaymentService;
import com.generic.khatabook.service.mapper.AggregatePaymentMapper;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class AggregatePaymentServiceImpl implements AggregatePaymentService {

    private final AggregatePaymentRepository myAggregatePaymentRepository;
    private final AggregatePaymentMapper myAggregatePaymentMapper;
    private final PaymentRepository paymentRepository;
    private final PaymentLogic paymentLogic;

    public AggregatePaymentServiceImpl(final AggregatePaymentRepository myAggregatePaymentRepository,
                                       final AggregatePaymentMapper myAggregatePaymentMapper,
                                       final PaymentRepository paymentRepository,
                                       final PaymentLogic paymentLogic) {
        this.myAggregatePaymentRepository = myAggregatePaymentRepository;
        this.myAggregatePaymentMapper = myAggregatePaymentMapper;
        this.paymentRepository = paymentRepository;
        this.paymentLogic = paymentLogic;
    }


    @Override
    public void paymentAggregate(final KhatabookDTO khatabook, final CustomerDTO customer, final AggregatePaymentDTO payment) {

      /*  final List<CustomerPayment> customerAllPayment = paymentRepository.findByKhatabookIdAndCustomerIdAndPaymentOnDateBetween(
                customer.khatabookId(),
                customer.customerId(),
                LocalDateTime.of(payment.from(), LocalTime.MIN),
                LocalDateTime.of(payment.to(),
                                 LocalTime.MAX));

*/
        myAggregatePaymentRepository.save(myAggregatePaymentMapper.mapToEntity(payment, customer));
    }

    @Override
    public AggregatePaymentDTO getLastAggregation(final KhatabookDTO khatabook, final CustomerDTO customer) {
        final AggregatePayment entity = myAggregatePaymentRepository.findOne(Example.of(AggregatePayment.builder().khatabookId(khatabook.khatabookId())
                .customerId(customer.customerId())
                .build())).orElse(null);
        assert entity != null;
        return myAggregatePaymentMapper.mapToDTO(entity);
    }
}
