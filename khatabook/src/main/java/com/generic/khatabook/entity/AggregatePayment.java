package com.generic.khatabook.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "aggregate_payment")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AggregatePayment {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;
    private String customerId;
    private String khatabookId;
    @Embedded
    private TimePeriod timePeriod;
    private LocalDateTime createdOn;
}