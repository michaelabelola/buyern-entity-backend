package com.buyern.buyern.Repositories;

import com.buyern.buyern.Models.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmailOrPhone(@Nullable String email, @Nullable String phone);

    Optional<User> findByEmail(String email);

    Optional<User> findByUid(String uid);

    Optional<User> findByIdOrUid(Long id, String uid);



    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

//    @Query("select u.id, u.firstName, u.lastName from Users u where u.id = ?1 order by u.lastName")
//    String findByIdOrderByLastNameC(Long id);
}