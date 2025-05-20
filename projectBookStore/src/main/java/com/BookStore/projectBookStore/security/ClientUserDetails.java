package com.BookStore.projectBookStore.security;

import com.BookStore.projectBookStore.entities.Client;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

public class ClientUserDetails implements UserDetails {

    private final String username;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;
    private final String clientName;

    public ClientUserDetails(Client client) {
        this.username = client.getEmail();
        this.password = client.getPassword();
        this.clientName = client.getName();
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + client.getRole().name());
        this.authorities = Set.of(authority);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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

    // Método adicional para obtener el nombre real del cliente
    public String getClientName() {
        return clientName;
    }
}
