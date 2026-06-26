package ru.elizarovds.kpautoservice.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "car_brands")
public class CarBrand {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true, length = 50)
    private String name;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
