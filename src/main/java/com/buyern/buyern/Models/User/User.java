package com.buyern.buyern.Models.User;

import com.buyern.buyern.Models.Location.City;
import com.buyern.buyern.Models.Location.Country;
import com.buyern.buyern.Models.Location.State;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity(name = "Users")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    private String email;
    private String phone;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    private Date dob;
    @Column(nullable = false)
    private String address;
    private String address2;
    @OneToOne
    private City city;
    @OneToOne
    private State state;
    @OneToOne
    private Country country;
    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(nullable = false)
    private Date timeRegistered;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
