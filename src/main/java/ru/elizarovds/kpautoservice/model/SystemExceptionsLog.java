package ru.elizarovds.kpautoservice.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "system_exceptions_log")
public class SystemExceptionsLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(nullable = false, length = 30)
    private String source;

    @Column(length = 100)
    private String operation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "error_code", length = 50)
    private String errorCode;

    @Column(name = "error_message", nullable = false, columnDefinition = "TEXT")
    private String errorMessage;

    @Column(name = "stack_trace", columnDefinition = "TEXT")
    private String stackTrace;

    @Column(columnDefinition = "TEXT")
    private String payload;

    @Column(nullable = false)
    private Boolean handled = false;

    /**
     * ==GETTERS==
     */

    public Long getId() { return id; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public String getSource() { return source; }
    public String getOperation() { return operation; }
    public User getUser() { return user; }
    public String getErrorCode() { return errorCode; }
    public String getErrorMessage() { return errorMessage; }
    public String getStackTrace() { return stackTrace; }
    public String getPayload() { return payload; }
    public Boolean getHandled() { return handled; }

    /**
     * ==SETTERS==
     */

    public void setId(Long id) { this.id = id; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    public void setSource(String source) { this.source = source; }
    public void setOperation(String operation) { this.operation = operation; }
    public void setUser(User user) { this.user = user; }
    public void setErrorCode(String errorCode) { this.errorCode = errorCode; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
    public void setStackTrace(String stackTrace) { this.stackTrace = stackTrace; }
    public void setPayload(String payload) { this.payload = payload; }
    public void setHandled(Boolean handled) { this.handled = handled; }
}