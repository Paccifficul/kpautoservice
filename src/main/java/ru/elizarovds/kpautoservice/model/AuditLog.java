package ru.elizarovds.kpautoservice.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "audit_log")
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "table_name", nullable = false, length = 50)
    private String tableName;

    @Column(nullable = false, length = 10)
    private String operation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "old_data", columnDefinition = "jsonb")
    private String oldData;

    @Column(name = "new_data", columnDefinition = "jsonb")
    private String newData;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime timestamp;

    /**
     * ==GETTERS==
     */

    public Long getId() { return id; }
    public String getTableName() { return tableName; }
    public String getOperation() { return operation; }
    public User getUser() { return user; }
    public String getOldData() { return oldData; }
    public String getNewData() { return newData; }
    public LocalDateTime getTimestamp() { return timestamp; }

    /**
     * ==SETTERS==
     */

    public void setId(Long id) { this.id = id; }
    public void setTableName(String tableName) { this.tableName = tableName; }
    public void setOperation(String operation) { this.operation = operation; }
    public void setUser(User user) { this.user = user; }
    public void setOldData(String oldData) { this.oldData = oldData; }
    public void setNewData(String newData) { this.newData = newData; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}