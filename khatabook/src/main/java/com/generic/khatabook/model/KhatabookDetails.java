package com.generic.khatabook.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.util.Set;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonRootName(value = "customer")
@Relation(collectionRelation = "customers")
public final class KhatabookDetails extends RepresentationModel<KhatabookDetails> {
    private final String bookId;
    private final String khatabookId;
    private final int numberOfCustomers;
    private final Set<CustomerDTO> customers;
    private final KhatabookPaymentSummary paymentSummary;

    public KhatabookDetails(String bookId, String khatabookId, int numberOfCustomers, Set<CustomerDTO> customers, KhatabookPaymentSummary paymentSummary) {
        this.bookId = bookId;
        this.khatabookId = khatabookId;
        this.numberOfCustomers = numberOfCustomers;
        this.customers = customers;
        this.paymentSummary = paymentSummary;
    }


    public KhatabookDetails(final KhatabookDTO khatabookDTO, final Set<CustomerDTO> customers) {
        this(khatabookDTO.bookId(), khatabookDTO.khatabookId(), customers.size(), customers, KhatabookPaymentSummary.empty());
    }

    public KhatabookDetails(final KhatabookDTO khatabookDTO, final Set<CustomerDTO> customers, final KhatabookPaymentSummary khatabookPaymentSummary) {
        this(khatabookDTO.bookId(), khatabookDTO.khatabookId(), customers.size(), customers, khatabookPaymentSummary);
    }

    public KhatabookDetails(final KhatabookDTO khatabook, final CustomerDTO customer, final KhatabookPaymentSummary customerDairy) {
        this(khatabook.bookId(), khatabook.khatabookId(), 1, Set.of(customer), customerDairy);
    }


}
