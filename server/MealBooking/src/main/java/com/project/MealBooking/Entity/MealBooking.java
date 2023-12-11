package com.project.MealBooking.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CurrentTimestamp;

import java.sql.Timestamp;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Entity
@Getter
@Builder
@Table(name = "MealBooking")
public class MealBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookingId")
    private Long bookingId;

    @Column(name = "bookingDate")
    private LocalDate bookingDate;

    @Column(name = "email")
    @NotNull
    private String email;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "UserId", referencedColumnName = "UserId")
    private Users userId;

    @Column(name = "timestamp")
    @CurrentTimestamp
    private Timestamp timestamp;

}