package com.shopease.backend.controller;

/**
 * Contrôleur REST qui expose les services d'enregistrement et de connexion.
 *
 * Permet aux utilisateurs de créer un compte ou de se connecter via des requêtes HTTP POST.
 *
 * Chemin d'accès de base : /api/auth
 *
 * @author Fadhel Smari
 */

import com.shopease.backend.dto.AuthResponse;
import com.shopease.backend.dto.LoginRequest;
import com.shopease.backend.dto.RegisterRequest;
import com.shopease.backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * Enregistre un nouvel utilisateur.
     *
     * @param request les informations d'inscription (nom, email, mot de passe, etc.)
     * @return la réponse avec le jeton JWT et les détails de l'utilisateur
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    /**
     * Authentifie un utilisateur avec son username et son mot de passe.
     *
     * @param request les informations de connexion (username et mot de passe)
     * @return la réponse avec le jeton JWT et les détails de l'utilisateur
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }
}


