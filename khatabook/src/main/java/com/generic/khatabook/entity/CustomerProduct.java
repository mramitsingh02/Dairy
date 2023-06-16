package com.generic.khatabook.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "customer_products")
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class CustomerProduct {
    @Id
    @GeneratedValue
    private Long Id;
    private String productId;
    @Transient
    private String productName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_product_id")
    private Customer customer;

    public String getHumanreadableName() {
        return productName + " (" + productId + ")";
    }

}
