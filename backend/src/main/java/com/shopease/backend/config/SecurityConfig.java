package com.shopease.backend.config;

/**
 * Configuration de la sécurité pour l'application ShopEase.
 *
 * Cette classe configure les filtres de sécurité, la gestion de l'authentification,
 * le fournisseur d'authentification basé sur les utilisateurs personnalisés,
 * ainsi que l'encodage des mots de passe.
 *
 * @author Fadhel Smari
 */

import com.shopease.backend.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    public static final String AUTH = "/api/auth/**";
    public static final String PAYMENTS_WEBHOOK = "/api/payments/webhook";

    /**
     * Configure la chaîne de filtres de sécurité.
     *
     * - Désactive CSRF (utile pour les API REST stateless)
     * - Autorise librement toutes les requêtes vers /api/auth/**
     * - Exige l’authentification pour toute autre requête
     * - Utilise un AuthenticationProvider personnalisé
     * - Ajoute un filtre JWT avant le filtre d’authentification par username/password
     *
     * @param http l'objet HttpSecurity à configurer
     * @return la chaîne de filtres de sécurité
     * @throws Exception en cas d'erreur de configuration
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()) // Désactive la protection CSRF (utile pour les API REST)
                // Active CORS avec une configuration personnalisée
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(AUTH, PAYMENTS_WEBHOOK).permitAll() // Autorise l'accès libre aux endpoints d'auth
                        .anyRequest().authenticated() // Toutes les autres requêtes doivent être authentifiées
                )
                .authenticationProvider(authenticationProvider()) // Utilise un fournisseur d’authentification personnalisé
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // Ajoute le filtre JWT avant celui de Spring
                .build(); // Construit et retourne la chaîne de filtres
    }

    /**
     * Définit un fournisseur d’authentification basé sur la base de données.
     *
     * Utilise CustomUserDetailsService et BCryptPasswordEncoder pour vérifier les identifiants.
     *
     * @return une instance de DaoAuthenticationProvider
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(); // Crée une instance de fournisseur DAO
        authProvider.setUserDetailsService(userDetailsService); // Définit le service qui charge les utilisateurs
        authProvider.setPasswordEncoder(passwordEncoder()); // Définit le mécanisme d'encodage des mots de passe
        return authProvider; // Retourne le fournisseur configuré
    }

    /**
     * Fournit un encodeur de mot de passe utilisant l’algorithme BCrypt.
     *
     * @return un encodeur BCrypt
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Fournit le gestionnaire d’authentification basé sur la configuration Spring Security.
     *
     * @param config la configuration d’authentification
     * @return une instance d’AuthenticationManager
     * @throws Exception si la configuration échoue
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager(); // Retourne le gestionnaire d’authentification défini par Spring
    }

    /**
     * Déclare une configuration CORS personnalisée pour autoriser les requêtes entre le frontend et le backend.
     *
     * Cette méthode permet notamment :
     * - d'autoriser l'origine `http://localhost:3000` (frontend React en développement)
     * - de spécifier les méthodes HTTP permises
     * - de permettre l'envoi d'en-têtes comme Authorization (utile pour JWT)
     * - d'activer les cookies ou jetons avec `AllowCredentials`
     *
     * @return la configuration CORS appliquée à toutes les routes du backend
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:3000")); // autorise ton frontend
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        config.setAllowCredentials(true); // important pour les cookies/token si utilisés

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}