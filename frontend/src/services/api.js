/**
 * Fichier de configuration Axios pour les requêtes HTTP vers l'API backend.
 *
 * Ce module configure l'instance Axios avec une URL de base, un en-tête de type JSON
 * et un intercepteur qui ajoute automatiquement le jeton JWT présent dans le localStorage.
 *
 * @author Fadhel Smari
 */


import axios from "axios";


// Crée une instance Axios préconfigurée
const api = axios.create({
  // Définit l'URL de base pour toutes les requêtes HTTP  
  baseURL: "http://localhost:8080/api",
  // Spécifie que les données envoyées sont au format JSON
  headers: {
    "Content-Type": "application/json",
  },
});

// Ajoute un intercepteur à l'instance Axios pour injecter le token JWT dans chaque requête sortante
api.interceptors.request.use((config) => {
  // Récupère le jeton JWT depuis le localStorage  
  const token = localStorage.getItem("token");
  if (token) {
    // Ajoute le jeton dans l'en-tête Authorization
    config.headers.Authorization = `Bearer ${token}`;
  }
  // Retourne la configuration modifiée de la requête
  return config;
});

export default api;
