package ru.elizarovds.kpautoservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.elizarovds.kpautoservice.model.User;
import ru.elizarovds.kpautoservice.repository.UserRepository;

@Service
public class AuditContextService {
    private final JdbcTemplate jdbcTemplate;
    private final UserRepository userRepository;

    @Autowired
    public AuditContextService(JdbcTemplate jdbcTemplate, UserRepository userRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRepository = userRepository;
    }

    public void setCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            userRepository.findByEmail(auth.getName()).ifPresent(user -> {
                jdbcTemplate.execute("SET LOCAL \"app.user_id\" = '" + user.getId() + "'");
            });
        }
    }

    public void clearCurrentUser() {
        jdbcTemplate.execute("SET LOCAL \"app.user_id\" = ''");
    }
}