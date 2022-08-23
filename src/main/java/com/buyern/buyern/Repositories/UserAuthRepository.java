package com.buyern.buyern.Repositories;

import com.buyern.buyern.Models.User.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserAuthRepository extends JpaRepository<UserAuth, Long> {
    @Query("select u from user_auth u where u.email = ?1 and u.password = ?2")
    Optional<UserAuth> findByEmailAndPassword(@NonNull String email, @NonNull String password);

    Optional<UserAuth> findByEmail(@Nullable String email);
    Optional<UserAuth> findByuId(String userId);

    boolean existsByEmail(String email);

    @Transactional
    @Modifying
    @Query("update user_auth u set u.password = ?1 where u.id = ?2")
    int updatePasswordById(String password, Long id);

}