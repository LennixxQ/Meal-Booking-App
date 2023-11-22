package com.project.MealBooking.Entity;
import jakarta.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name = "coupon")
public class Coupon {
    @Id
    @OneToOne
    @JoinColumn(name = "booking_id")
    private MealBooking mealBooking;

    @Column(name = "expiry_date")
    private Date expiryDate;

    @Column(name = "redemption_time")
    private Timestamp redemptionTime;
}

