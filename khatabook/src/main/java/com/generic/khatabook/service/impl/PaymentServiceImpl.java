package com.generic.khatabook.service.impl;

import com.generic.khatabook.entity.Amount;
import com.generic.khatabook.entity.CustomerPayment;
import com.generic.khatabook.model.AmountDTO;
import com.generic.khatabook.model.CustomerDTO;
import com.generic.khatabook.model.KhatabookDTO;
import com.generic.khatabook.model.KhatabookPaymentSummary;
import com.generic.khatabook.model.PaymentDTO;
import com.generic.khatabook.model.PaymentStatistics;
import com.generic.khatabook.model.PaymentType;
import com.generic.khatabook.repository.PaymentRepository;
import com.generic.khatabook.service.PaymentService;
import com.generic.khatabook.service.mapper.AmountMapper;
import com.generic.khatabook.service.mapper.CustomerPaymentMapper;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDate;
import java.util.Set;
import java.util.function.BinaryOperator;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRepository myPaymentRepository;

    @Override
    public KhatabookPaymentSummary getPaymentDetailByKhatabookId(final String khatabookId) {

        val customersPayment = myPaymentRepository.findByKhatabookId(khatabookId);
        final PaymentStatistics paymentStatistics = getPaymentStatistics(customersPayment);
        return new KhatabookPaymentSummary(paymentStatistics, CustomerPaymentMapper.mapToPojos(customersPayment));
    }

    private PaymentStatistics getPaymentStatistics(final Set<CustomerPayment> customersPayment) {
        BinaryOperator<AmountDTO> addAmount = AmountMapper::add;
        final AmountDTO totalCredited = customersPayment.stream()
                .filter(x -> x.getPaymentType().equals(PaymentType.CREDIT.name()))
                .map(CustomerPayment::getAmount)
                .map(AmountMapper::dto)
                .reduce(AmountDTO.ZERO, addAmount);

        final AmountDTO totalDebited = customersPayment.stream()
                .filter(x -> x.getPaymentType().equals(PaymentType.DEBIT.name()))
                .map(CustomerPayment::getAmount)
                .map(AmountMapper::dto)
                .reduce(AmountDTO.ZERO, addAmount);
        final AmountDTO total = totalCredited.minus(totalDebited);
        return new PaymentStatistics(total, totalCredited, totalDebited);
    }

    @Override
    public boolean savePayment(final KhatabookDTO khatabookDTO, final CustomerDTO customerDTO, final PaymentDTO paymentDTO, final PaymentType paymentType) {

        log.info("Save Payment for khatabook {}", khatabookDTO.khatabookId());
        myPaymentRepository.save(CustomerPayment.builder()
                .khatabookId(khatabookDTO.khatabookId())
                .customerId(customerDTO.customerId())
                .amount(Amount.of(paymentDTO.amount().value(), paymentDTO.amount().unitOfMeasurement()))
                .paymentType(paymentType.name())
                .paymentOnDate(LocalDate.now(Clock.systemDefaultZone()))
                .build());


        return false;
    }

    @Override
    public KhatabookPaymentSummary getPaymentDetailForCustomer(final CustomerDTO customerRequest) {

        final Set<CustomerPayment> allRecordForCustomer = myPaymentRepository.findByKhatabookIdAndCustomerId(customerRequest.khatabookId(), customerRequest.customerId());

        return new KhatabookPaymentSummary(getPaymentStatistics(allRecordForCustomer), CustomerPaymentMapper.mapToPojos(allRecordForCustomer));
    }
}
