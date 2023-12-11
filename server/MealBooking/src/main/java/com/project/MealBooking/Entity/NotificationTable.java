package com.project.MealBooking.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CurrentTimestamp;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "NotificationTable")
@Builder
public class NotificationTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Notification_ID")
    private Long Id;

    @Column(name = "notification_date")
    @NotNull
    @CurrentTimestamp
    private LocalDate date;

    @Column(name = "role")
    @NotNull
    private String role;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "UserId", referencedColumnName = "UserId")
    private Users userId;

//    @Column(name = "user_id")
//    private Long userId;

    @Column(name = "Notification_Read")
    private boolean NotificationRead;

    @Column(name = "Noti_Message")
    private String message;
}
