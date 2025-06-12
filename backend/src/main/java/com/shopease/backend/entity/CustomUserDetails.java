package com.shopease.backend.entity;

/**
 * Implémentation personnalisée de l'interface {@link UserDetails} utilisée par Spring Security.
 * Cette classe encapsule un objet {@link User} et expose ses données à Spring Security
 * pour l’authentification et l’autorisation.
 *
 * @author Fadhel Smari
 */

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {

    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    /**
     * Retourne la liste des rôles (autorités) attribués à l'utilisateur.
     * Ici, un seul rôle est attribué et préfixé par "ROLE_" comme exigé par Spring Security.
     *
     * @return une collection contenant le rôle de l'utilisateur
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(
                new SimpleGrantedAuthority("ROLE_" + user.getRole().name())
        );
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
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
