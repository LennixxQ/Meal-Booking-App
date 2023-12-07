package com.project.MealBooking.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CurrentTimestamp;

import java.sql.Timestamp;
import java.time.LocalDate;

@RequiredArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "MealBooking")
public class MealBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookingId")
    private Long bookingId;

    @Column(name = "bookingDate")
    private LocalDate bookingDate;

    @Column(name = "email")
    private String email;

    @Column(name = "UserId")
    private Long userId;

    @Column(name = "QrCode")
    private String qrCode;

    @Column(name = "timestamp")
    @CurrentTimestamp
    private Timestamp timestamp;

}

