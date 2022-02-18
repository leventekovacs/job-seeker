package com.jobseeker.repository;

import com.jobseeker.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    boolean existsByUuid(UUID uuid);
    Optional<Client> findByEmail(String s);
}
