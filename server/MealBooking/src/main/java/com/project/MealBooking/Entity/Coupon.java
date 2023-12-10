package com.project.MealBooking.Entity;

import com.project.MealBooking.Entity.Enums.BookingStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CurrentTimestamp;

import java.time.LocalDate;
import java.util.Random;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "Coupons")
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Coupon_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "UserId", referencedColumnName = "UserId")
    private Users users;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "bookingId", referencedColumnName = "bookingId")
    private MealBooking bookingId;

    @Column(name = "Coupon_Number")
    private String couponNumber;

    @Column(name = "Coupon_Date")
    @CurrentTimestamp
    private LocalDate couponDate;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    public Coupon() {
        this.couponNumber = generateRandomCouponNumber(6);
    }

    private String generateRandomCouponNumber(int length) {
        String allowedChars = "1234567890QWERTYUIOPASDFGHJKLZXCVBNM";
        StringBuilder stringBuilder = new StringBuilder(length);
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(allowedChars.length());
            stringBuilder.append(allowedChars.charAt(randomIndex));
        }

        return stringBuilder.toString();
    }

}