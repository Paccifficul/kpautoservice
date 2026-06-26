package ru.elizarovds.kpautoservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.elizarovds.kpautoservice.model.Client;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<Client, UUID> {
    Optional<Client> findByUserId(UUID userId);
    Optional<Client> findByUserEmail(String email);
}