package org.iesalixar.daw2.danielquintero
        .dwese2627_ticket_logger_webapp_dqd.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(
            name = "username",
            nullable = false,
            unique = true,
            length = 40
    )
    private String username;


    @Column(
            name = "password_hash",
            nullable = false,
            length = 500
    )
    private String passwordHash;


    @Column(
            name = "active",
            nullable = false
    )
    private Boolean active = true;


    @Column(
            name = "account_non_locked",
            nullable = false
    )
    private Boolean accountNonLocked = true;


    @Column(
            name = "last_password_change"
    )
    private LocalDateTime lastPasswordChange;


    @Column(
            name = "password_expires_at"
    )
    private LocalDateTime passwordExpiresAt;


    @Column(
            name = "failed_login_attempts"
    )
    private Integer failedLoginAttempts = 0;


    @Column(
            name = "email_verified",
            nullable = false
    )
    private Boolean emailVerified = false;


    @Column(
            name = "must_change_password",
            nullable = false
    )
    private Boolean mustChangePassword = false;
}