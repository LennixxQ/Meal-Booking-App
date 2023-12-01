package com.project.MealBooking.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Getter
@Setter
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id", length=10)
    private Long employeeId;

    @Column(name = "first_name", nullable = false, length = 255)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 255)
    private String lastName;

    @Column(name = "email", unique = true, nullable = false, length = 255)
    private String email;

    @Column(name = "position", length=255)
    private String position;

    @Column(name = "department", length=255)
    private String department;

    @Column(name = "hire_date")
    private Date hireDate;

    @Column(name ="birth_date")
    private Date birthDate;

    @Column(name = "address", length = 255)
    private String address;

    @Column(name = "city", length = 255)
    private String city;

    @Column(name = "state", length = 255)
    private String state;

    @Column(name = "zip_code", length = 20)
    private String zipCode;

    @Column(name = "country", length = 30)
    private String country;

    @Column(name = "phone_number", length =20)
    private String phoneNumber;

    @Column(name = "salary", precision = 10, scale = 2)
    private BigDecimal salary;


}
