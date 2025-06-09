package com.shopease.backend.service;

/**
 * Service responsable de la génération, validation et extraction d'informations à partir des tokens JWT
 * dans l'application ShopEase.
 * <p>
 * Ce service utilise la bibliothèque JJWT pour signer et analyser les JSON Web Tokens (JWT),
 * en s'appuyant sur une clé secrète codée en HMAC-SHA256.
 * </p>
 *
 * @author Fadhel Smari
 */

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    /**
     * Durée de validité du token JWT en millisecondes (ici : 24 heures).
     */
    private static final long EXPIRATION_TIME = 86400000; // 24h
    /**
     * Clé secrète utilisée pour signer les tokens JWT (doit être ≥ 256 bits pour HS256).
     */
    private final String SECRET_KEY = "monsupersecretjwtclé256bitssecuremonsupersecretjwtclé256bitssecure"; // ≥ 256 bits

    /**
     * Génère une clé de signature HMAC à partir de la chaîne secrète.
     *
     * @return une instance {@link Key} pour signer les tokens.
     */
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    /**
     * Génère un token JWT basé sur le nom d'utilisateur fourni.
     *
     * @param username le nom d'utilisateur à inclure dans le token.
     * @return une chaîne JWT signée.
     */
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Vérifie si le token JWT est valide pour l'utilisateur fourni.
     *
     * @param token le token JWT à valider.
     * @param userDetails les informations de l'utilisateur authentifié.
     * @return {@code true} si le token est valide, {@code false} sinon.
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username != null && username.equals(userDetails.getUsername()));
    }

    /**
     * Extrait le nom d'utilisateur (sujet) contenu dans le token JWT.
     *
     * @param token le token JWT à analyser.
     * @return le nom d'utilisateur extrait du token.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extrait une information (claim) spécifique du token JWT à l'aide d'un résolveur.
     *
     * @param token le token JWT à analyser.
     * @param resolver une fonction qui définit quel claim extraire.
     * @param <T> le type du claim extrait.
     * @return la valeur extraite du claim.
     */
    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = Jwts.parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return resolver.apply(claims);
    }

    /**
     * Vérifie si le token JWT est expiré.
     *
     * @param token le token à analyser.
     * @return {@code true} si le token est expiré, {@code false} sinon.
     */
    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }
}