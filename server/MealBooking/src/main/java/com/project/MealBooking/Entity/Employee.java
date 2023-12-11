package com.project.MealBooking.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id", length=10)
    private Long employeeId;

    @Column(name = "first_name", nullable = false, length = 30)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 30)
    private String lastName;

    @Column(name = "email", unique = true, nullable = false, length = 30)
    private String email;

    @ElementCollection(targetClass = String.class)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "email"))
    @Column(name = "role")
    private Set<String> roles = new HashSet<>();

    @Column(name = "position", length=20)
    private String position;

    @Column(name = "department", length=20)
    private String department;

    @Column(name = "hire_date")
    private Date hireDate;

    @Column(name ="birth_date")
    private Date birthDate;

    @Column(name = "address", length = 100)
    private String address;

    @Column(name = "city", length = 20)
    private String city;

    @Column(name = "state", length = 20)
    private String state;

    @Column(name = "zip_code", length = 20)
    private String zipCode;

    @Column(name = "country", length = 30)
    private String country;

    @Column(name = "phone_number", length =20)
    private String phoneNumber;

    @Column(name = "salary", precision = 10, scale = 2)
    private BigDecimal salary;


    public enum Role{
        ROLE_ADMIN,
        ROLE_EMPLOYEE
    }

}
