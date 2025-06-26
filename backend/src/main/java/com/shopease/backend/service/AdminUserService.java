package com.shopease.backend.service;

/**
 * Interface pour les opérations d'administration liées aux utilisateurs.
 *
 * Fournit les méthodes nécessaires pour consulter, créer, modifier ou supprimer
 * des comptes utilisateurs via le panneau d'administration.
 *
 * Cette interface est généralement implémentée par un service tel que {@code AdminUserServiceImpl}.
 *
 * @author Fadhel Smari
 */

import com.shopease.backend.dto.RegisterRequest;
import com.shopease.backend.dto.UpdateProfileRequest;
import com.shopease.backend.dto.UserAdminResponse;

import java.util.List;

public interface AdminUserService {

    List<UserAdminResponse> getAllUsers();

    UserAdminResponse createUser(RegisterRequest request);

    UserAdminResponse updateUser(Long userId, UpdateProfileRequest request);

    void deleteUser(Long userId);
}
