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

import com.shopease.backend.entity.User;
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
     * @param username le nom d'à inclure dans le token.
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
     * @param username les informations de l'utilisateur authentifié.
     * @return {@code true} si le token est valide, {@code false} sinon.
     */
    public boolean isTokenValid(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username)) && !isTokenExpired(token);
    }

    /**
     * Extrait le nom d'utilisateur (sujet) contenu dans le token JWT.
     *
     * @param token le token JWT à analyser.
     * @return le nom d'utilisateur extrait du token.
     */
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }
    /**
     * Extrait tous les claims (informations) contenus dans le token JWT.
     *
     * Cette méthode décode le token JWT en utilisant la clé de signature et
     * retourne le corps (payload) du token sous forme de Claims, contenant
     * des données comme le sujet, la date d'expiration, etc.
     *
     * @param token le token JWT à analyser
     * @return un objet {@link io.jsonwebtoken.Claims} contenant toutes les informations du token
     */
    private Claims extractAllClaims(String token) {
        // Crée un parser JWT avec la clé de signature secrète
        return Jwts.parser()
                // Définit la clé de signature utilisée pour vérifier l’authenticité du token
                .setSigningKey(getSigningKey())
                // Construit le parser
                .build()
                // Analyse le token JWT et retourne un objet Jws<Claims>
                .parseClaimsJws(token)
                // Récupère le corps du token (les claims réels)
                .getBody();
    }


    /**
     * Vérifie si le token JWT est expiré.
     *
     * @param token le token à analyser.
     * @return {@code true} si le token est expiré, {@code false} sinon.
     */
    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }
}