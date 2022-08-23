package com.buyern.buyern.Configs;

import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class Authorities {
    private List<CustomAuthority> customAuthorities;

    public void addAuthority(String authority) {
        customAuthorities.add(new CustomAuthority(authority));
    }

    public void addAuthorities(List<String> authorities) {
        customAuthorities = authorities.stream().map(CustomAuthority::new).collect(Collectors.toList());
    }
    public static List<CustomAuthority> from(List<String> authorities) {
        return authorities.stream().map(CustomAuthority::new).collect(Collectors.toList());
    }

    public void clear() {
        customAuthorities.clear();
    }
}

