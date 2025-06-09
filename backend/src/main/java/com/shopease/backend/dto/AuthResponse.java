package com.shopease.backend.dto;

/**
 * Représente la réponse retournée après une authentification réussie.
 *
 * Cette classe contient principalement le jeton JWT (JSON Web Token)
 * généré après une inscription ou une connexion réussie.
 *
 * @author Fadhel Smari
 */

public class AuthResponse {
    private String token;

    public AuthResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}


