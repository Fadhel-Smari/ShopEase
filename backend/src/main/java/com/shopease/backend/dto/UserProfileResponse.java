package com.shopease.backend.dto;

/**
 * Classe représentant la réponse contenant le profil utilisateur.
 *
 * Contient les informations de base d’un utilisateur comme prénom, nom, nom d’utilisateur,
 * email et rôle associé.
 *
 * @author Fadhel Smari
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileResponse {

    private String firstname;
    private String lastname;
    private String username;
    private String email;
    private String role;

}

