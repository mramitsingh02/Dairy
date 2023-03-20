package com.generic.khatabook.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "khatabooks")
public class Khatabook {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String bookId;
    private String khatabookId;
    private String msisdn;
    private String partnerName;
    private String partnerDescription;
    private String status;
    @CreationTimestamp
    private LocalDateTime createdOn;
    @UpdateTimestamp
    private LocalDateTime updatedOn;
    private LocalDateTime deletedOn;

}
