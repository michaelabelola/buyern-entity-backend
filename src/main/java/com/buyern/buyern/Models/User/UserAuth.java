package com.buyern.buyern.Models.User;

import com.buyern.buyern.Enums.Role;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.*;
import java.util.List;

@Entity(name = "user_auth")
@Data
public class UserAuth {
    @Id
    @Column(nullable = false, unique = true)
    private Long id;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false, unique = true)
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
//    list of entity permissions : (TREE)
    @ManyToMany(cascade = CascadeType.DETACH)
    @JoinTable(
            name = "user_entity_permissions",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))
    private List<EntityPermission> permissions;

}
