package com.generic.khatabook.rating.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;

@Entity
@Table(name = "products",
        uniqueConstraints = {@UniqueConstraint(name = "product_name", columnNames = "name")}

)
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotEmpty
    private String name;
    @PositiveOrZero(message = "quantity should be zero or positive number")
    private int quantity;
    @PositiveOrZero(message = "price should be zero or positive number")
    private BigDecimal price;
    @NotEmpty
    private String unitOfMeasurement;

}
