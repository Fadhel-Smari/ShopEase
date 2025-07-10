/**
 * Module pour gérer le token JWT dans le localStorage du navigateur.
 * 
 * Ce module permet de sauvegarder, récupérer, supprimer et décoder
 * un token JWT utilisé pour l'authentification.
 * 
 * @author Fadhel Smari
 */


// Clé utilisée pour stocker le token dans le localStorage
const TOKEN_KEY = "shopease_token";

/**
 * Sauvegarde un token dans le localStorage sous la clé définie.
 * 
 * @param {string} token Le token JWT à sauvegarder
 */
export const saveToken = (token) => {
  // Enregistre le token dans le localStorage avec la clé TOKEN_KEY  
  localStorage.setItem(TOKEN_KEY, token);
};

/**
 * Récupère le token stocké dans le localStorage.
 * 
 * @returns {string|null} Le token JWT ou null si aucun token trouvé
 */
export const getToken = () => {
  return localStorage.getItem(TOKEN_KEY);
};

/**
 * Supprime le token stocké dans le localStorage.
 */
export const removeToken = () => {
  localStorage.removeItem(TOKEN_KEY);
};

/**
 * Décode le payload d'un token JWT sans vérifier la signature.
 * 
 * @param {string} token Le token JWT à décoder
 * @returns {Object|null} Le contenu décodé du payload ou null en cas d'erreur
 */
export const decodeToken = (token) => {
  try {
    // Sépare le token en trois parties et récupère la partie payload (2e segment)
    const payload = token.split(".")[1];
    // Décode la chaîne base64 du payload et la convertit en objet JSON
    return JSON.parse(atob(payload));
  } catch (err) {
    console.error("Erreur de décodage du token :", err);
    return null;
  }
};