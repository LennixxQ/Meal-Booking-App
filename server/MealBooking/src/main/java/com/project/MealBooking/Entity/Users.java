package com.project.MealBooking.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CurrentTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "users_table")
public class Users implements UserDetails {

    @Id
    @Column(name = "UserId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long UserId;

    @Column(name = "email", length = 50)
    @NotEmpty
    private String email;

    @Column(name = "password", length = 255)
    @NotNull
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "last_login")
    @CurrentTimestamp
    private Timestamp lastLogin;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    private UserRole role;

    @ElementCollection(targetClass = String.class)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "email"))
    @Column(name = "role")
    private Set<String> roles = new HashSet<>();


    @Column(name = "token", length = 255)
    private String user_token;

    @OneToMany(mappedBy = "userId", cascade = CascadeType.REMOVE) //This ensures that when a Users entity is deleted, associated MealBooking entities are also deleted.
    private List<MealBooking> mealBookings;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public enum UserRole{
        ROLE_ADMIN, ROLE_EMPLOYEE
    }
}