package ru.elizarovds.kpautoservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.elizarovds.kpautoservice.model.AuditLog;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    List<AuditLog> findByTableName(String tableName);
    
    List<AuditLog> findByOperation(String operation);
    
    @Query("SELECT a FROM AuditLog a WHERE a.timestamp BETWEEN :start AND :end")
    List<AuditLog> findByTimestampBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
    
    @Query("SELECT a FROM AuditLog a WHERE a.user.email = :email")
    List<AuditLog> findByUserEmail(@Param("email") String email);
}