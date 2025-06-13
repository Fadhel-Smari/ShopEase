package com.shopease.backend.config;

/**
 * Filtre d'authentification JWT qui intercepte chaque requête HTTP entrante,
 * extrait et valide le token JWT dans l'en-tête Authorization, puis configure
 * le contexte de sécurité avec l'utilisateur authentifié si le token est valide.
 *
 * @author Fadhel Smari
 */

import com.shopease.backend.service.CustomUserDetailsService;
import com.shopease.backend.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private CustomUserDetailsService userDetailsService;

    /**
     * Filtre chaque requête HTTP pour gérer l'authentification via JWT.
     *
     * @param request la requête HTTP entrante
     * @param response la réponse HTTP associée
     * @param filterChain la chaîne de filtres à exécuter
     * @throws ServletException si une erreur liée au servlet survient
     * @throws IOException si une erreur d'entrée/sortie survient
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // Récupère l'en-tête "Authorization" de la requête HTTP
        final String authHeader = request.getHeader("Authorization");

        final String jwt;

        final String username;

        // Vérifie si l'en-tête Authorization est null ou ne commence pas par "Bearer "
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            // Si le token est absent ou mal formé, continue la chaîne de filtres sans authentification
            filterChain.doFilter(request, response);
            // Termine l'exécution de ce filtre
            return;
        }

        // Extrait le JWT en supprimant le préfixe "Bearer "
        jwt = authHeader.substring(7);

        // Utilise le service JWT pour extraire le nom d'utilisateur du token
        username = jwtService.extractUsername(jwt);

        // Vérifie que le nom d'utilisateur est non null et qu'aucune authentification n'est encore présente dans le contexte
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Charge les détails de l'utilisateur à partir du service UserDetails
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            // Vérifie que le token JWT est valide pour cet utilisateur
            if (jwtService.isTokenValid(jwt, userDetails)) {

                // Crée un objet d'authentification basé sur le nom d'utilisateur, sans mot de passe, avec les autorités de l'utilisateur
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());

                // Ajoute des informations complémentaires spécifiques à la requête dans le token d'authentification
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request));

                // Configure le contexte de sécurité Spring avec le token d'authentification
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Passe la requête et la réponse au filtre suivant dans la chaîne
        filterChain.doFilter(request, response);
    }
}