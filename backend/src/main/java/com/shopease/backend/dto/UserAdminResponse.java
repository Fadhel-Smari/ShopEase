package com.shopease.backend.dto;

import com.shopease.backend.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO pour exposer les informations utilisateur côté administration.
 * Contient les données essentielles visibles par un administrateur.
 *
 * @author Fadhel Smari
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAdminResponse {
    private Long id;
    private String firstname;
    private String lastname;
    private String username;
    private String email;
    private Role role;
}