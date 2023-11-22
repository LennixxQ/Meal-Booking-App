package com.project.MealBooking.Entity;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "password_change_history")
public class PasswordChangeHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "password_change_id")
    private Long passwordChangeId;

//    @ManyToOne
//    @JoinColumn(name = "email")
//    private User user;

    @Column(name = "old_password", length = 255)
    private String oldPassword;

    @Column(name = "new_password", length = 255)
    private String newPassword;

    @Column(name = "change_date")
    private Timestamp changeDate;

    @Column(name = "user_action", length = 20)
    private String userAction;

    public Long getPasswordChangeId() {
        return passwordChangeId;
    }

    public void setPasswordChangeId(Long passwordChangeId) {
        this.passwordChangeId = passwordChangeId;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public Timestamp getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Timestamp changeDate) {
        this.changeDate = changeDate;
    }

    public String getUserAction() {
        return userAction;
    }

    public void setUserAction(String userAction) {
        this.userAction = userAction;
    }
}

