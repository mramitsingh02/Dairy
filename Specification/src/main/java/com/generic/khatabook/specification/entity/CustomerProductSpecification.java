package com.generic.khatabook.specification.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "customer_product_specifications")
public class CustomerProductSpecification {

    @Id
    @GeneratedValue
    private Long cid;
    private String productId;
    private Float quantity;
    @ManyToOne
    @JoinColumn(name = "customer_specification_id", nullable = false)
    private CustomerSpecification customerSpecification;

    private BigDecimal price;
    private Long startUnit;
    private Long endUnit;
    private String unitOfMeasurement;

    @CreationTimestamp
    private LocalDateTime createdOn;
    @UpdateTimestamp
    private LocalDateTime updatedOn;

}
