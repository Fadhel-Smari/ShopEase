package com.shopease.backend.dto;

/**
 * Classe représentant la réponse contenant le profil utilisateur.
 *
 * Contient les informations de base d’un utilisateur comme prénom, nom, nom d’utilisateur,
 * email et rôle associé.
 *
 * @author Fadhel Smari
 */

public class UserProfileResponse {

    private String firstname;
    private String lastname;
    private String username;
    private String email;
    private String role;

    public UserProfileResponse() {}

    public UserProfileResponse(String firstname, String lastname, String username, String email, String role) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.email = email;
        this.role = role;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

