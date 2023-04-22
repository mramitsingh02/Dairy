package com.generic.khatabook.specification.entity;

import com.generic.khatabook.specification.model.UnitOfMeasurement;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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
    private String khatabookId;
    private String customerId;
//    private String specificationType;
    private String specificationFor;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "customerSpecification")
    private List<CustomerProductSpecification> products;
    @CreationTimestamp
    private LocalDateTime createdOn;
    @UpdateTimestamp
    private LocalDateTime updatedOn;
    private LocalDateTime deletedOn;
}
