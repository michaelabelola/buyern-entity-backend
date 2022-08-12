package com.buyern.buyern.Models.User;

import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.*;

@Entity(name = "user_auth")
@Data
public class UserAuth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    private Long userId;
    private String email;
    private String password;
//    list of entity permissions : (TREE)
    private String permissions;

}
