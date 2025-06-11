package com.shopease.backend.config;

/**
 * Filtre d’authentification JWT qui intercepte chaque requête HTTP pour vérifier la présence d'un token JWT.
 *
 * Si un token est trouvé et valide, l'utilisateur est authentifié dans le contexte de sécurité Spring.
 * Ce filtre est exécuté une seule fois par requête.
 *
 * @author Fadhel Smari
 */

import com.shopease.backend.entity.User;
import com.shopease.backend.repository.UserRepository;
import com.shopease.backend.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    /**
     * Méthode appelée automatiquement pour chaque requête HTTP.
     *
     * Elle intercepte la requête, extrait le token JWT dans l’en-tête Authorization,
     * vérifie sa validité, puis configure le contexte de sécurité Spring si l’authentification est valide.
     *
     * @param request requête HTTP entrante
     * @param response réponse HTTP
     * @param filterChain chaîne de filtres à poursuivre après ce filtre
     * @throws ServletException exception liée au servlet
     * @throws IOException exception d'entrée/sortie
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // Récupère l’en-tête Authorization de la requête
        final String authHeader = request.getHeader("Authorization");

        // Si l'en-tête est absent ou ne commence pas par "Bearer ", on passe au filtre suivant
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Supprime le préfixe "Bearer " pour obtenir le token JWT brut
        final String token = authHeader.substring(7);

        // Extrait le nom d'utilisateur à partir du token
        final String username = jwtService.extractUsername(token);

        // Si un nom d'utilisateur est extrait et qu’aucune authentification n’est encore présente
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Recherche de l'utilisateur dans la base de données
            User user = userRepository.findByUsername(username);

            // Si l'utilisateur n'existe pas ou si le token est invalide, on ignore la requête
            if (user == null || !jwtService.isTokenValid(token, username)) {
                filterChain.doFilter(request, response);
                return;
            }

            // Création d'une autorité (rôle) sous forme de ROLE_NOM
            List<SimpleGrantedAuthority> authorities =
                    List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));

            // Création du jeton d'authentification avec les rôles (mais sans mot de passe)
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(user.getId(), null, authorities);

            // Associe des détails supplémentaires liés à la requête (ex : IP, session, etc.)
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // Définit le jeton d’authentification dans le contexte de sécurité Spring
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        // Passe au filtre suivant de la chaîne
        filterChain.doFilter(request, response);
    }
}


