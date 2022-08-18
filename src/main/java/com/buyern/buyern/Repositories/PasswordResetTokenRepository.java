package com.buyern.buyern.Repositories;

import com.buyern.buyern.Models.User.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    long deleteByEmail(String email);

    Optional<PasswordResetToken> findByEmail(String email);

    PasswordResetToken findByIdAndToken(Long id, String token);

    Optional<PasswordResetToken> findByToken(String token);

}