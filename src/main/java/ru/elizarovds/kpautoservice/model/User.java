package ru.elizarovds.kpautoservice.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false, length = 100)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public enum Role {
        client, employee
    }

    /**
     * ==GETTERS==
     */

    public UUID getId() { return id; }
    
    public String getEmail() { return email; }
    
    public String getPasswordHash() { return passwordHash; }
    
    public Role getRole() { return role; }

    /**
     * ==SETTERS==
     */

    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setId(UUID id) { this.id = id; }

    public void setEmail(String email) { this.email = email; }

    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public void setRole(Role role) { this.role = role; }

    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}