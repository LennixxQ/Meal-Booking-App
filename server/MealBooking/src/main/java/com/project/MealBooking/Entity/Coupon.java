package com.project.MealBooking.Entity;

import com.project.MealBooking.Entity.Enums.BookingStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CurrentTimestamp;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Coupon")
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Coupon_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "UserId", referencedColumnName = "UserId")
    private Users UserId;


    @Column(name = "bookingId")
    private Long bookingId;

    @Column(name = "Coupon_Number")
    private String couponNumber;

    @Column(name = "Coupon_Date")
    @CurrentTimestamp
    private LocalDate couponDate;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private BookingStatus status;

}