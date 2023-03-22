package com.generic.khatabook.specification.entity;

import com.generic.khatabook.specification.model.UnitOfMeasurement;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "customer_specifications")
public class CustomerSpecification {
    @Id
    private String id;
    private String name;
    private String description;
    //@Version
    private int version;
    private String customerId;
    private String khatabookId;
    private String specificationFor;
    private BigDecimal price;
    private String unitOfMeasurement;
}
