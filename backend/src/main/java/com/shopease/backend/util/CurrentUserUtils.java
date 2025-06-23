package com.shopease.backend.util;

/**
 * Utilitaire pour récupérer les informations de l'utilisateur actuellement authentifié.
 *
 * Cette classe fournit une méthode pour obtenir l'identifiant (ID) de l'utilisateur
 * connecté en utilisant l'objet {@link Authentication} fourni par Spring Security.
 *
 * @author Fadhel Smari
 */

import com.shopease.backend.entity.User;
import com.shopease.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;


@Component
@RequiredArgsConstructor
public class CurrentUserUtils {

    private final UserRepository userRepository;

    /**
     * Récupère l'identifiant de l'utilisateur connecté à partir du contexte d'authentification.
     *
     * @param authentication objet fourni par Spring Security représentant l'utilisateur connecté
     * @return l'ID de l'utilisateur
     * @throws UsernameNotFoundException si aucun utilisateur correspondant n’est trouvé
     */
    public Long getUserId(Authentication authentication) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Utilisateur non trouvé : " + username);
        }
        return user.getId();
    }
}

