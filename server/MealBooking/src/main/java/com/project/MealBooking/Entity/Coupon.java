package com.project.MealBooking.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserId")
    private Users users;

    @Column(name = "Coupon_Number")
    private String couponNumber;

    @Column(name = "Coupon_Date")
    private LocalDate couponDate;

    @Column(name = "Redeemed")
    private Boolean redeemed;

    // getters and setters

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