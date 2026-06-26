package ru.elizarovds.kpautoservice.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @Column(name = "full_name", nullable = false, length = 100)
    private String fullName;

    @Column(nullable = false, length = 20)
    private String phone;

    @Column(name = "registration_date", nullable = false)
    private LocalDate registrationDate = LocalDate.now();

    /**
     * ==GETTERS==
     */

    public UUID getId() { return id; }
    public User getUser() { return user; }
    public String getFullName() { return fullName; }
    public String getPhone() { return phone; }
    public LocalDate getRegistrationDate() { return registrationDate; }

    /**
     * ==SETTERS==
     */

    public void setId(UUID id) { this.id = id; }
    public void setUser(User user) { this.user = user; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setRegistrationDate(LocalDate registrationDate) { this.registrationDate = registrationDate; }
}