package ru.elizarovds.kpautoservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.elizarovds.kpautoservice.model.SystemExceptionsLog;

import java.util.List;

@Repository
public interface SystemExceptionsLogRepository extends JpaRepository<SystemExceptionsLog, Long> {
    List<SystemExceptionsLog> findByHandledFalse();
    List<SystemExceptionsLog> findBySource(String source);
}