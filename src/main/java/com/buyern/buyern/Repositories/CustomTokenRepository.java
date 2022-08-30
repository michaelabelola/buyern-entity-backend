package com.buyern.buyern.Repositories;

import com.buyern.buyern.Enums.TokenType;
import com.buyern.buyern.Models.User.CustomToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
public interface CustomTokenRepository extends JpaRepository<CustomToken, Long> {
    @Transactional
    @Modifying
    @Query("delete from tokens t where t.email = ?1")
    void deleteByEmail(String email);

    boolean existsByEmailAndType(String email, TokenType type);

    Optional<CustomToken> findByEmailAndToken(String email, String token);

}