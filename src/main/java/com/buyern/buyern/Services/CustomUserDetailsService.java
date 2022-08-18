package com.buyern.buyern.Services;

import com.buyern.buyern.Enums.Role;
import com.buyern.buyern.Models.User.UserAuth;
import com.buyern.buyern.Repositories.UserAuthRepository;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Data
public class CustomUserDetailsService implements UserDetailsService {
    final UserAuthRepository userAuthRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserAuth userAuth = userAuthRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("User Email not found"));
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialNonExpired = true;
        boolean accountNonLocked = true;
        return new User(userAuth.getEmail(), userAuth.getPassword().toLowerCase(), enabled, accountNonExpired, credentialNonExpired,accountNonLocked,getAuthorities(List.of("ROLE_ADMIN")));
    }
    private static List<GrantedAuthority> getAuthorities (List<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }
}
