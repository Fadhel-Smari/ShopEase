package com.shopease.backend.service;

/**
 * Service d'authentification pour la gestion de l'inscription et de la connexion des utilisateurs.
 * <p>
 * Ce service fournit deux fonctionnalités principales :
 * <ul>
 *     <li>Inscription d'un nouvel utilisateur (avec encodage du mot de passe et attribution d'un rôle CLIENT)</li>
 *     <li>Authentification d'un utilisateur existant (vérification du mot de passe et génération du token JWT)</li>
 * </ul>
 * </p>
 *
 * @author Fadhel Smari
 */

import com.shopease.backend.dto.AuthResponse;
import com.shopease.backend.dto.LoginRequest;
import com.shopease.backend.dto.RegisterRequest;
import com.shopease.backend.entity.User;
import com.shopease.backend.enums.Role;
import com.shopease.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Inscrit un nouvel utilisateur avec les données fournies dans la requête.
     * Le mot de passe est encodé, un rôle par défaut CLIENT est assigné,
     * puis un token JWT est généré pour cet utilisateur.
     *
     * @param request les informations d'inscription (nom, prénom, email, mot de passe, etc.)
     * @return une réponse contenant le token JWT généré
     */
    public AuthResponse register(RegisterRequest request) {
        User user = new User();
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.CLIENT);
        user.setEmail(request.getEmail());
        userRepository.save(user);

        String token = jwtService.generateToken(user.getUsername());
        return new AuthResponse(token);
    }

    /**
     * Authentifie un utilisateur en vérifiant son nom d'utilisateur et son mot de passe.
     * Si les identifiants sont valides, un token JWT est généré et renvoyé.
     *
     * @param request les informations de connexion (nom d'utilisateur et mot de passe)
     * @return une réponse contenant le token JWT généré
     * @throws RuntimeException si l'utilisateur n'est pas trouvé ou si le mot de passe est incorrect
     */
    public AuthResponse authenticate(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername());

        if (user == null) {
            throw new RuntimeException("Utilisateur introuvable");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Mot de passe incorrect");
        }

        String token = jwtService.generateToken(user.getUsername());
        return new AuthResponse(token);
    }
}


