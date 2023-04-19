package com.generic.khatabook.service.impl;

import com.generic.khatabook.entity.Amount;
import com.generic.khatabook.entity.CustomerPayment;
import com.generic.khatabook.exceptions.AppEntity;
import com.generic.khatabook.exceptions.InvalidArgumentValueException;
import com.generic.khatabook.model.AmountDTO;
import com.generic.khatabook.model.CustomerDTO;
import com.generic.khatabook.model.KhatabookDTO;
import com.generic.khatabook.model.KhatabookPaymentSummary;
import com.generic.khatabook.model.PaymentDTO;
import com.generic.khatabook.model.PaymentStatistics;
import com.generic.khatabook.model.PaymentType;
import com.generic.khatabook.model.SummaryProperties;
import com.generic.khatabook.repository.PaymentRepository;
import com.generic.khatabook.service.PaymentService;
import com.generic.khatabook.service.mapper.AmountMapper;
import com.generic.khatabook.service.mapper.CustomerPaymentMapper;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.function.BinaryOperator;

import static java.util.Objects.isNull;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRepository myPaymentRepository;
    @Autowired
    private CustomerPaymentMapper customerPaymentMapper;


    @Autowired
    private PaymentLogic paymentLogic;

    @Override
    public KhatabookPaymentSummary getPaymentDetailByKhatabookId(final String khatabookId) {

        val customersPayment = myPaymentRepository.findByKhatabookId(khatabookId);
        final PaymentStatistics paymentStatistics = getPaymentStatistics(customersPayment);
        return new KhatabookPaymentSummary(paymentStatistics,
                                           CustomerPaymentMapper.mapToPojos(customersPayment));
    }

    private PaymentStatistics getPaymentStatistics(final Collection<CustomerPayment> customersPayment) {
        BinaryOperator<AmountDTO> addAmount = AmountMapper::add;
        final AmountDTO totalCredited = customersPayment.stream().filter(this::isCreditRecord).map(CustomerPayment::getAmount).map(
                AmountMapper::dto).reduce(AmountDTO.ZERO, addAmount);

        final AmountDTO totalDebited = customersPayment.stream().filter(this::isDebitRecord).map(CustomerPayment::getAmount).map(
                AmountMapper::dto).reduce(AmountDTO.ZERO, addAmount);
        final AmountDTO total = totalCredited.minus(totalDebited);

        if (customersPayment.isEmpty()) {
            return null;
        }

        return new PaymentStatistics(total, totalCredited, totalDebited);
    }

    private boolean isCreditRecord(final CustomerPayment x) {
        return x.getPaymentType().equals(PaymentType.CREDIT.name());
    }

    private boolean isDebitRecord(final CustomerPayment x) {
        return x.getPaymentType().equals(PaymentType.DEBIT.name());
    }

    @Override
    public boolean savePayment(final KhatabookDTO khatabookDTO,
                               final CustomerDTO customerDTO,
                               final PaymentDTO paymentDTO,
                               final PaymentType paymentType) {

        log.info("Save Payment for khatabook {}", khatabookDTO.khatabookId());


        PaymentDTO finalPayment = paymentLogic.calculateFinalPayment(customerDTO, paymentDTO);

        if (isNull(finalPayment.productId()) && isNull(finalPayment.amount())) {
            throw new InvalidArgumentValueException(AppEntity.AMOUNT, "pass the +ve value.");
        }
        final Amount amount = Amount.of(finalPayment.amount().value(), finalPayment.amount().unitOfMeasurement());
        final CustomerPayment customerPayment = CustomerPayment.builder().khatabookId(khatabookDTO.khatabookId()).customerId(
                                                                       customerDTO.customerId()).amount(amount).paymentType(paymentType.name())
                                                               .productId(paymentDTO.productId())
                                                               .paymentOnDate(LocalDateTime.now(
                                                                       Clock.systemDefaultZone())).build();


        myPaymentRepository.save(customerPayment);


        return true;
    }

    @Override
    public KhatabookPaymentSummary getPaymentDetailForCustomer(final CustomerDTO customerRequest) {


        return getPaymentDetailForCustomer(customerRequest, "asc", "date");

    }

    @Override
    public KhatabookPaymentSummary getPaymentDetailForCustomer(final CustomerDTO customerRequest,
                                                               final String sorting,
                                                               final String sortingBy) {
        final Collection<CustomerPayment> allRecordForCustomer = myPaymentRepository.findByKhatabookIdAndCustomerId(
                customerRequest.khatabookId(),
                customerRequest.customerId());
        if (isNull(allRecordForCustomer) || allRecordForCustomer.isEmpty()) {
            return null;
        }
        return new KhatabookPaymentSummary(getPaymentStatistics(allRecordForCustomer),
                                           CustomerPaymentMapper.mapToPojos(allRecordForCustomer,
                                                                            SummaryProperties.of(sorting, sortingBy))
                );
    }
}
