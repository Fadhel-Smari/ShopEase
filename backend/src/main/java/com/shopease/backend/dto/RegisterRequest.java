package com.shopease.backend.dto;

/**
 * Représente une requête d'enregistrement d'utilisateur dans l'application ShopEase.
 * Cette classe est utilisée pour transférer les données saisies par l'utilisateur
 * lors de l'inscription (prénom, nom, nom d'utilisateur, courriel et mot de passe).
 *
 * @author Fadhel Smari
 */

public class RegisterRequest {
    private String firstname;
    private String lastname;
    private String username;
    private String email;
    private String password;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}