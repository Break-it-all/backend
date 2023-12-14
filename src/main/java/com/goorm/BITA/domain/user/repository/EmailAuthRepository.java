package com.goorm.BITA.domain.user.repository;

import com.goorm.BITA.domain.user.domain.EmailAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EmailAuthRepository extends JpaRepository<EmailAuth, Long> {
    EmailAuth findByEmail(String email);
    EmailAuth findByEmailToken(UUID emailToken);
}
