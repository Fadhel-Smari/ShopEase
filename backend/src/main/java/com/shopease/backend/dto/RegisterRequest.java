package com.shopease.backend.dto;

/**
 * Représente une requête d'enregistrement d'utilisateur dans l'application ShopEase.
 * Cette classe est utilisée pour transférer les données saisies par l'utilisateur
 * lors de l'inscription (prénom, nom, nom d'utilisateur, courriel et mot de passe).
 *
 * @author Fadhel Smari
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private String firstname;
    private String lastname;
    private String username;
    private String email;
    private String password;


}