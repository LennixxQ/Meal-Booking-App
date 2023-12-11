package com.project.MealBooking.DTO;


import lombok.*;

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationResponse {
    Long userId;
    String notificationMessage;

}
