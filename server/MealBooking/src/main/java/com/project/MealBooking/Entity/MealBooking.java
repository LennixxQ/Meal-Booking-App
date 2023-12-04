package com.project.MealBooking.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CurrentTimestamp;

import java.sql.Date;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@Table(name = "meal_booking")
public class MealBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private Long bookingId;

//    @ManyToOne
//    @JoinColumn(name = "employee_id")
//    private Employee employee;

    @Column(name = "booking_date")
    private Date bookingDate;

    @Column(name = "meal_type", length = 10)
    private String mealType;

    @Column(name = "timestamp")
    @CurrentTimestamp
    private Timestamp timestamp;
}

