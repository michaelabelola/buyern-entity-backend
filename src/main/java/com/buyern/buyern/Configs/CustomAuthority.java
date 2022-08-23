package com.buyern.buyern.Configs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public
class CustomAuthority implements GrantedAuthority {
    String value;

    @Override
    public String getAuthority() {
        return value;
    }
}
