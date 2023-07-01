package com.generic.khatabook.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "customer_specifications"

)
public class CustomerSpecification {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String customerSpecificationId;
    private String specificationName;
    private String description;
    @Version
    private int version;
    private String khatabookId;
    private String customerId;
    private String specificationFor;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "customerSpecification")
    private List<CustomerProductSpecification> customerProductSpecifications;
    @CreationTimestamp
    private LocalDateTime createdOn;
    @UpdateTimestamp
    private LocalDateTime updatedOn;
    private LocalDateTime deletedOn;
}
