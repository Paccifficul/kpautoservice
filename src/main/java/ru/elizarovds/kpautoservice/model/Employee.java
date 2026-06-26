package ru.elizarovds.kpautoservice.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Column(nullable = false, length = 50)
    private String position;

    @Column(name = "hire_date", nullable = false)
    private LocalDate hireDate;

    /**
     * ==GETTERS==
     */

    public UUID getId() { return id; }
    public User getUser() { return user; }
    public String getFullName() { return fullName; }
    public String getPosition() { return position; }
    public LocalDate getHireDate() { return hireDate; }

    /**
     * ==SETTERS==
     */

    public void setId(UUID id) { this.id = id; }
    public void setUser(User user) { this.user = user; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setPosition(String position) { this.position = position; }
    public void setHireDate(LocalDate hireDate) { this.hireDate = hireDate; }
}