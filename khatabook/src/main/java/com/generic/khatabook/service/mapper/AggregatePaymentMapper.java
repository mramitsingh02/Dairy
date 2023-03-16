package com.generic.khatabook.service.mapper;

import com.generic.khatabook.entity.AggregatePayment;
import com.generic.khatabook.entity.TimePeriod;
import com.generic.khatabook.model.AggregatePaymentDTO;
import com.generic.khatabook.model.CustomerDTO;
import com.generic.khatabook.model.KhatabookDTO;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AggregatePaymentMapper {


    public AggregatePayment convertToEntity(final AggregatePaymentDTO dto, final KhatabookDTO khatabook, final CustomerDTO customer) {

        return AggregatePayment.builder().customerId(customer.customerId()).khatabookId(khatabook.khatabookId()).timePeriod(TimePeriod.of(dto.from(), dto.to())).build();
    }

    public AggregatePaymentDTO convertToDTO(final AggregatePayment dto) {
        if (Objects.isNull(dto)) {
            return null;
        }
        return new AggregatePaymentDTO(dto.getTimePeriod().from(), dto.getTimePeriod().to());
    }
}
