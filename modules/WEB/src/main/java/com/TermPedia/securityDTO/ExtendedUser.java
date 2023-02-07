package com.TermPedia.securityDTO;

import com.TermPedia.dto.users.UserPublicData;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class ExtendedUser implements UserDetails {
    private final Integer userID;
    private final String login;
    Collection<SimpleGrantedAuthority> roles;

    public ExtendedUser(UserPublicData data) {
        this.userID = data.userID();
        this.login = data.login();
        this.roles = data.roles().stream().map(SimpleGrantedAuthority::new).toList();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
