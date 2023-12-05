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
@Table(name = "MealBooking")
public class MealBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookingId")
    private Long bookingId;

    @ManyToOne
    @JoinColumn(referencedColumnName = "UserId")
    private Users UserId;

    @Column(name = "bookingDate")
    private Date bookingDate;


    @Column(name = "QrCode")
    private String QrCode;

    @Column(name = "timestamp")
    @CurrentTimestamp
    private Timestamp timestamp;
}

