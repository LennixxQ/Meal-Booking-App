package com.project.MealBooking.Entity;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "log")
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private Long logId;

//    @ManyToOne
//    @JoinColumn(name = "booking_id")
//    private MealBooking mealBooking;

    @Column(name = "status", length = 20)
    private String status;

    @Column(name = "timestamp")
    private Timestamp timestamp;

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}

