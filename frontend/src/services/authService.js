/**
 * Module gérant les appels API d'authentification pour l'application.
 * 
 * Fournit les fonctions pour s'inscrire, se connecter et se déconnecter.
 * Utilise le module `api` pour faire les requêtes HTTP vers le backend.
 *
 * @author Fadhel Smari
 */

import api from "./api";

const AUTH_API = "/auth";

/**
 * Enregistre un nouvel utilisateur en envoyant ses données au backend.
 * @param {Object} userData - données du nouvel utilisateur (email, username, password, etc.)
 * @returns {Promise<Object>} - réponse contenant les infos de l'utilisateur
 */
export const register = async (userData) => {
  const response = await api.post(`${AUTH_API}/register`, userData);
  return response.data;
};

/**
 * Connecte un utilisateur avec ses identifiants et stocke le token JWT localement.
 * @param {Object} credentials - informations de connexion (email et mot de passe)
 * @returns {Promise<Object>} - réponse contenant le token et infos utilisateur
 */
export const login = async (credentials) => {
  const response = await api.post(`${AUTH_API}/login`, credentials);
  const token = response.data.token;

  if (token) {
    localStorage.setItem("token", token);
  }

  return response.data;
};

/**
 * Supprime le token JWT stocké localement, déconnectant ainsi l'utilisateur.
 */
export const logout = () => {
  localStorage.removeItem("token");
};
