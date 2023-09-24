package com.generic.khatabook.service;

import com.generic.khatabook.common.model.CustomerDTO;
import com.generic.khatabook.common.model.CustomerSpecificationDTO;
import com.generic.khatabook.common.model.KhatabookDTO;
import com.generic.khatabook.common.model.KhatabookPaymentSummary;
import com.generic.khatabook.common.model.KhatabookPaymentSummaryView;
import com.generic.khatabook.common.model.PaymentDTO;
import com.generic.khatabook.common.model.PaymentType;

public interface PaymentService {
    public KhatabookPaymentSummary getPaymentDetailByKhatabookId(String khatabookId);

    boolean savePayment(KhatabookDTO khatabookDTO, CustomerDTO customerDTO, PaymentDTO paymentDTO, final PaymentType paymentType);

    KhatabookPaymentSummary getPaymentDetailForCustomer(CustomerDTO customerRequest);

    KhatabookPaymentSummary getPaymentDetailForCustomer(CustomerDTO customerRequest,
                                                        final String sorting,
                                                        final String sortingBy, CustomerSpecificationDTO customerSpecification);

    KhatabookPaymentSummaryView getCustomerPaymentDetailView(CustomerDTO customerRequest,
                                                             CustomerSpecificationDTO customerSpecification);
}
