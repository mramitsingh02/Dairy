package com.generic.khatabook.service.mapper;

import com.generic.khatabook.common.model.Container;
import com.generic.khatabook.common.model.Mapper;
import com.generic.khatabook.entity.AggrePayment;
import com.generic.khatabook.model.AggregatePaymentDTO;
import com.generic.khatabook.model.CustomerDTO;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AggregatePaymentMapper implements Mapper<AggrePayment, AggregatePaymentDTO, Void> {

    @Override
    public AggrePayment mapToEntity(final AggregatePaymentDTO dto) {
        if (Objects.isNull(dto)) {
            return null;
        }
        return new AggrePayment(dto.from(), dto.to());
    }

    @Override
    public Container<AggregatePaymentDTO, Void> mapToContainer(final AggrePayment aggregatePayment) {
        return Container.of(mapToDTO(aggregatePayment));
    }

    @Override
    public AggregatePaymentDTO mapToDTO(final AggrePayment aggregatePayment) {
        if (Objects.isNull(aggregatePayment)) {
            return null;
        }
        return new AggregatePaymentDTO(String.valueOf(aggregatePayment.getId()));
//        return new AggregatePaymentDTO(aggregatePayment.getProductId(), aggregatePayment.getStartDate(), aggregatePayment.getEndDate());
    }

    public AggrePayment mapToEntity(final AggregatePaymentDTO dto, final CustomerDTO customer) {

        return AggrePayment.builder().build();
//        return AggregatePayment.builder().customerId(customer.customerId()).khatabookId(customer.khatabookId()).startDate(
//                dto.from()).endDate(dto.to()).productId(dto.productId()).build();
    }

}
