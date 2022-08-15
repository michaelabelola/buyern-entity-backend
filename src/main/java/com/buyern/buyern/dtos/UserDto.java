package com.buyern.buyern.dtos;

import com.buyern.buyern.Models.User.User;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

@Data
public class UserDto implements Serializable {

    private Long id;
    @NotBlank(message = "First name is mandatory")
    private String firstName;
    @NotBlank(message = "Last name is mandatory")
    private String lastName;
    @NotBlank(message = "email name is mandatory")
    private String email;
    @NotBlank(message = "phone name is mandatory")
    private String phone;
    @NotBlank(message = "Date of birth name is mandatory")
    private Date dob;
    @NotBlank(message = "address is mandatory")
    private String address;
    private String address2;
    @NotBlank(message = "state is mandatory")
    private Long state;
    @NotBlank(message = "Country is mandatory")
    private Long country;
    private Date timeRegistered;

    public User toModel(){
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
}
