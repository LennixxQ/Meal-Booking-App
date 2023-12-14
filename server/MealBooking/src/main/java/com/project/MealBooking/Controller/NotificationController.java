package com.project.MealBooking.Controller;

import com.project.MealBooking.Configuration.JwtService;
import com.project.MealBooking.DTO.NotificationResponse;
import com.project.MealBooking.Entity.NotificationTable;
import com.project.MealBooking.Entity.Users;
import com.project.MealBooking.Exception.ResourceNotFoundException;
import com.project.MealBooking.Repository.NotificationRepository;
import com.project.MealBooking.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/mealBooking")
public class NotificationController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @GetMapping("/show-notification")
    public ResponseEntity<List<NotificationResponse>> sendNotification(@RequestHeader("Authorization") String token) throws Exception {
        String jwtToken = token.substring(7);
        Long UserId = Long.valueOf(jwtService.extractUserId(jwtToken));

        Users users = userRepository.findById(UserId)
                .orElseThrow(() -> new ResourceNotFoundException("User Cannot be find"));

        List<NotificationTable> notificationTable = notificationRepository.findAllByUserId(users);

        if (notificationTable != null && !notificationTable.isEmpty()) {
            List<NotificationResponse> notificationDtos = notificationTable.stream()
                    .map(notification -> new NotificationResponse(
                            notification.getUserId().getUserId(),
                            notification.getNotificationId(),
                            notification.getMessage()
                    ))
                    .collect(Collectors.toList());
            System.out.printf("Number of Notification: "+notificationDtos.size());

            return ResponseEntity.ok(notificationDtos);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/delete-notification/{NotificationId}")
    public ResponseEntity<String> deleteNotification(@PathVariable Long NotificationId){
            var id = notificationRepository.findByNotificationId(NotificationId);
            if (id != null){
                notificationRepository.delete(id);
                return ResponseEntity.ok().body("Notification deleted");
            }
        else {
                    throw new ResourceNotFoundException("Notification Cannot found");
                }
    }
}
