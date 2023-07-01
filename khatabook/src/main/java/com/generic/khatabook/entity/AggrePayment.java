package com.generic.khatabook.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "ag_pm")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AggrePayment {
    @Id
    @GeneratedValue
    private Long id;
/*
    private String customerId;
    private String khatabookId;
    private String productId;
    @Column(name = "start_date")
    private LocalDateTime startDate;
    @Column(name ="end_date")
    private LocalDateTime endDate;
    @CreationTimestamp
    private LocalDateTime createdOn;
*/

    public AggrePayment(LocalDateTime from, LocalDateTime localDateTime) {

    }
}