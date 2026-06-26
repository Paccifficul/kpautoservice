package ru.elizarovds.kpautoservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.elizarovds.kpautoservice.model.SystemExceptionsLog;
import ru.elizarovds.kpautoservice.repository.SystemExceptionsLogRepository;
import ru.elizarovds.kpautoservice.repository.UserRepository;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private final SystemExceptionsLogRepository exceptionsLogRepository;
    private final UserRepository userRepository;

    @Autowired
    public GlobalExceptionHandler(SystemExceptionsLogRepository exceptionsLogRepository, UserRepository userRepository) {
        this.exceptionsLogRepository = exceptionsLogRepository;
        this.userRepository = userRepository;
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException e) {
        logException("api", getCurrentOperation(), "RUNTIME_ERROR", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", e.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgument(IllegalArgumentException e) {
        logException("api", getCurrentOperation(), "INVALID_ARGUMENT", e.getMessage(), e);
        return ResponseEntity.badRequest()
                .body(Map.of("error", e.getMessage()));
    }

    private void logException(String source, String operation, String errorCode, 
                            String errorMessage, Exception e) {
        SystemExceptionsLog log = new SystemExceptionsLog();
        log.setSource(source);
        log.setOperation(operation);
        log.setErrorCode(errorCode);
        log.setErrorMessage(errorMessage);
        log.setStackTrace(getStackTrace(e));
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            userRepository.findByEmail(auth.getName()).ifPresent(log::setUser);
        }
        
        exceptionsLogRepository.save(log);
    }

    private String getCurrentOperation() {
        return "API_REQUEST";
    }

    private String getStackTrace(Exception e) {
        return e.getClass().getSimpleName() + ": " + e.getMessage();
    }
}