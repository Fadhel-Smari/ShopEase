package com.shopease.backend.config;

/**
 * Configuration de la sécurité pour l'application ShopEase.
 *
 * Définit la chaîne de filtres de sécurité, la gestion de session,
 * le gestionnaire d'authentification et l'encodage des mots de passe.
 *
 * @author Fadhel Smari
 */

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    /**
     * Configure la chaîne de filtres de sécurité pour les requêtes HTTP.
     *
     * @param http l'objet HttpSecurity utilisé pour configurer la sécurité web
     * @return la chaîne de filtres configurée
     * @throws Exception en cas d’erreur lors de la configuration
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Désactive la protection CSRF (utile pour les API REST stateless)
        http.csrf(AbstractHttpConfigurer::disable)
                // Configuration des autorisations des requêtes HTTP
                .authorizeHttpRequests(auth -> auth
                        // Autorise librement toutes les requêtes vers /api/auth/**
                        .requestMatchers("/api/auth/**").permitAll()
                        // Exige une authentification pour toute autre requête
                        .anyRequest().authenticated()
                )

                // Configure la politique de session pour ne pas stocker l’état de session (stateless)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        // Construit et retourne la chaîne de filtres
        return http.build();
    }

    /**
     * Fournit un encodeur de mot de passe utilisant l'algorithme BCrypt.
     *
     * @return une instance de PasswordEncoder (BCrypt)
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        // Utilise BCrypt pour encoder les mots de passe
        return new BCryptPasswordEncoder();
    }

    /**
     * Fournit un gestionnaire d’authentification basé sur la configuration Spring Security.
     *
     * @param config la configuration d'authentification de Spring
     * @return l'AuthenticationManager utilisé par Spring Security
     * @throws Exception si le gestionnaire ne peut pas être obtenu
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        // Récupère et retourne le gestionnaire d’authentification à partir de la configuration
        return config.getAuthenticationManager();
    }
}