package com.project.MealBooking.Entity;
import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "users")
public class Users {

    @Id
    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "last_login")
    private Timestamp lastLogin;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employeeId;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Timestamp getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Timestamp lastLogin) {
        this.lastLogin = lastLogin;
    }

}
