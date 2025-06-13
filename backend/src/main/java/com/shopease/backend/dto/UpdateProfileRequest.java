package com.shopease.backend.dto;

/**
 * Représente une requête de mise à jour du profil utilisateur.
 *
 * Cette classe contient les informations pouvant être modifiées par un utilisateur,
 * notamment son prénom, son nom de famille et son adresse courriel.
 *
 * @author Fadhel Smari
 */

public class UpdateProfileRequest {

    private String firstname;
    private String lastname;
    private String email;

    public UpdateProfileRequest() {}

    public UpdateProfileRequest(String firstname, String lastname, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

