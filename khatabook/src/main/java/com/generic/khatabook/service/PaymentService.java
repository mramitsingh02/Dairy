package com.generic.khatabook.service;

import com.generic.khatabook.model.*;

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
