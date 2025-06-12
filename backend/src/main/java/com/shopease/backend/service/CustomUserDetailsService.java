package com.shopease.backend.service;

/**
 * Service chargé de charger les informations de l'utilisateur à partir de la base de données
 * pour les besoins de l'authentification avec Spring Security.
 *
 * Cette classe implémente l'interface {@link UserDetailsService} qui est utilisée par Spring Security
 * pour récupérer les détails de l'utilisateur lors du processus de connexion.
 *
 * @author Fadhel Smari
 */

import com.shopease.backend.entity.CustomUserDetails;
import com.shopease.backend.entity.User;
import com.shopease.backend.repository.UserRepository;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Charge un utilisateur en base de données à partir de son nom d'utilisateur.
     *
     * Cette méthode est appelée automatiquement par Spring Security lors de la tentative de connexion.
     *
     * @param username le nom d'utilisateur fourni lors de la connexion
     * @return un objet {@link UserDetails} contenant les informations de l'utilisateur
     * @throws UsernameNotFoundException si aucun utilisateur n'est trouvé avec ce nom
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Nom d'utilisateur introuvable : " + username);
        }

        return new CustomUserDetails(user);
    }
}
