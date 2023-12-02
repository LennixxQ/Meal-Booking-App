package com.project.MealBooking.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Getter
@Setter
@Entity(name = "roles")
public class Roles implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role_name", nullable = false)
    private String rolename;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users users;


    @Override
    public String getAuthority() {
        return rolename;
    }

    @Override
    public String toString() {
        return "Roles{" +
                "Name: ='" + rolename + '\'' +
                '}';
    }
}
