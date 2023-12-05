//package com.project.MealBooking.Entity;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//
//import java.sql.Timestamp;
//
//@Entity
//@Getter
//@Setter
//@Table(name = "password_change_history")
//public class PasswordChangeHistory {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "password_change_id")
//    private Long passwordChangeId;
//
//    @ManyToOne
//    @JoinColumn(name = "email", referencedColumnName = "email")
//    private Users users;
//
//    @Column(name = "old_password", length = 255)
//    private String oldPassword;
//
//    @Column(name = "new_password", length = 255)
//    private String newPassword;
//
//    @Column(name = "change_date")
//    private Timestamp changeDate;
//
//    @Column(name = "user_action", length = 20)
//    private String userAction;
//
//}
//
