package com.generic.khatabook.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "employee")
@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    //...

    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "emp_workstation",
            joinColumns =
                    {@JoinColumn(name = "employee_id", referencedColumnName = "id")},
            inverseJoinColumns =
                    {@JoinColumn(name = "workstation_id", referencedColumnName = "id")})
    private WorkStation workStation;

    //... getters and setters
}