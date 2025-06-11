package com.shopease.backend.config;

/**
 * Configuration de la sécurité de l'application.
 *
 * Cette classe définit :
 * - l'authentification basée sur JWT,
 * - la gestion des sessions sans état (stateless),
 * - les autorisations des requêtes HTTP,
 * - l'encodage des mots de passe avec BCrypt.
 *
 * @author Fadhel Smari
 */

import com.shopease.backend.entity.User;
import com.shopease.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private UserRepository userRepository;

    /**
     * Fournit un AuthenticationManager personnalisé pour gérer l'authentification avec email et mot de passe.
     *
     * @return l'instance d'AuthenticationManager
     */
    @Bean
    public AuthenticationManager authenticationManager() {
        return authentication -> {
            // Récupération du nom d'utilisateur
            String username = authentication.getName();
            // Récupération du mot de passe saisi
            String password = authentication.getCredentials().toString();

            // Recherche de l'utilisateur dans la base de données
            User user = userRepository.findByUsername(username);
            if (user == null) {
                // Si l'utilisateur n'existe pas, on lève une exception
                throw new RuntimeException("Nom d'utilisateur invalide.");
            }

            // Vérifie si le mot de passe fourni correspond à celui encodé en base
            if (!passwordEncoder().matches(password, user.getPassword())) {
                // Si le mot de passe est incorrect, on lève une exception
                throw new RuntimeException("Mot de passe incorrect.");
            }

            // Si tout est correct, on retourne un token d'authentification (sans rôles ici)
            return new UsernamePasswordAuthenticationToken(
                    user.getId(), null, null // pas d'autorité/role attribué ici
            );
        };
    }

    /**
     * Configure la chaîne de filtres de sécurité Spring.
     *
     * - Désactive CSRF (car on utilise JWT)
     * - Définit la gestion des sessions comme stateless
     * - Autorise librement les requêtes vers /api/auth/**
     * - Protège toutes les autres routes
     * - Ajoute le filtre JWT avant le filtre UsernamePasswordAuthenticationFilter
     *
     * @param http configuration HttpSecurity
     * @return la SecurityFilterChain configurée
     * @throws Exception en cas d’erreur de configuration
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // Désactivation de la protection CSRF car on utilise un token stateless
                .csrf(csrf -> csrf.disable())
                // Spécifie que la session ne sera jamais créée ni utilisée
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Définition des règles d'accès
                .authorizeHttpRequests(auth -> auth
                        // Autorise librement les endpoints d'authentification (register, login)
                        .requestMatchers("/api/auth/**").permitAll()
                        // Toute autre requête nécessite une authentification
                        .anyRequest().authenticated()
                )
                // Ajout du filtre JWT avant le filtre standard d’authentification
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                // Construction finale de la chaîne de sécurité
                .build();
    }

    /**
     * Fournit un encodeur de mots de passe basé sur l’algorithme BCrypt.
     *
     * @return une instance de PasswordEncoder (BCrypt)
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
