package com.buyern.buyern.Models.User;

import com.buyern.buyern.Enums.Role;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity(name = "user_auth")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class UserAuth {
    @Id
    @Column(nullable = false, unique = true)
    private Long id;
    @Column(nullable = false, unique = true)
    private String email;
    private String password;
    private boolean verified = false;
    private boolean disabled = false;
    private boolean expired = false;
    private boolean credentialExpired = false;
    private boolean locked = false;
    @Enumerated(EnumType.STRING)
    private Role role;
//    list of entity permissions : (TREE)
    @ManyToMany(cascade = CascadeType.DETACH)
    @JoinTable(
            name = "user_entity_permissions",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))
    @ToString.Exclude
    private List<EntityPermission> permissions;

    public UserAuth(@NonNull Long id, @NonNull String email, @NonNull String password, @NonNull Role role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserAuth userAuth = (UserAuth) o;
        return id != null && Objects.equals(id, userAuth.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
