package com.buyern.buyern.dtos;

import com.buyern.buyern.Models.User.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
public class UserDto implements Serializable {

    private Long id;
    @NotBlank(message = "First name is mandatory")
    private String firstName;
    @NotBlank(message = "Last name is mandatory")
    private String lastName;
    @NotBlank(message = "email is mandatory")
    @Email(message = "email is mandatory")
    private String email;
    @NotBlank(message = "phone is mandatory")
    private String phone;
    @NotNull(message = "Date of birth is mandatory")
    private Date dob;
    @NotBlank(message = "address is mandatory")
    private String address;
    private String address2;
    @NotNull(message = "State is mandatory")
    private Long state;
    @NotNull(message = "Country is mandatory")
    private Long country;
    private Long city;
    private Date timeRegistered;

    public User toModel() {
        User user = new User();
        user.setId(getId());
        user.setFirstName(getFirstName());
        user.setLastName(getLastName());
        user.setEmail(getEmail());
        user.setPhone(getPhone());
        user.setDob(getDob());
        user.setAddress(getAddress());
        user.setAddress2(getAddress2());
        user.setState(getState());
        user.setCountry(getCountry());
        user.setTimeRegistered(getTimeRegistered());
        return user;
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class UserSignUpDTO extends UserDto {
        @NotNull(message = "Password is mandatory")
        private String password;
        @NotNull(message = "Profile Image is mandatory")
        private MultipartFile profileImage;
    }
}
