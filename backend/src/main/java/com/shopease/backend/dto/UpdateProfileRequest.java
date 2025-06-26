package com.shopease.backend.dto;

/**
 * Représente une requête de mise à jour du profil utilisateur.
 *
 * Cette classe contient les informations pouvant être modifiées par un utilisateur,
 * notamment son prénom, son nom de famille et son adresse courriel.
 *
 * @author Fadhel Smari
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProfileRequest {

    private String firstname;
    private String lastname;
    private String email;

}

