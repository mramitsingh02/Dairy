package com.generic.khatabook.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {
    @Id
    private String customerId;
    private String khatabookId;
    private String msisdn;
    private String firstName;
    private String lastName;
    @CreationTimestamp
    private LocalDateTime createdOn;
    @UpdateTimestamp
    private LocalDateTime updatedOn;
    private LocalDateTime deletedOn;
    private String specificationId;
    @OneToMany(mappedBy = "productId", cascade = CascadeType.ALL/*, fetch = FetchType.EAGER*/)
    @LazyCollection(value = LazyCollectionOption.FALSE)
    private List<CustomerProduct> products = new ArrayList<>();

}
