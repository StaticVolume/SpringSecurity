package ru.springBoot311.SpringBoot.sources.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.springBoot311.SpringBoot.sources.model.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MyUserDetails implements UserDetails {
    private User user;

    public MyUserDetails(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(this.user.getRoles());
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
     return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getName();
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
