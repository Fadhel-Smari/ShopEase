/**
 * Contexte d'authentification global pour l'application.
 * 
 * Gère les informations de l'utilisateur connecté à partir du token JWT,
 * et fournit les fonctions associées (`setUser`, `logout`) via React Context.
 * 
 * 
 * @author Fadhel Smari
 */

import { createContext, useContext, useEffect, useState } from "react";
import { getToken, decodeToken, removeToken } from "../utils/tokenUtils";
import { useNavigate } from "react-router-dom";

// Création d'un contexte d'authentification pour partager l'état utilisateur dans l'application
const AuthContext = createContext();

/**
 * Composant fournisseur (provider) pour l'authentification.
 * 
 * Ce composant englobe toute l'application et fournit l'objet `user`,
 * ainsi que les fonctions associées (`setUser`, `logout`) à tous les composants enfants via le contexte.
 * 
 * @param {ReactNode} children - Composants enfants à envelopper avec le contexte d'authentification
 * @returns {JSX.Element} Le fournisseur de contexte avec les valeurs utilisateur
 */
export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const navigate = useNavigate();

  // Effet déclenché au chargement du composant pour récupérer les infos du token (si existant)
  useEffect(() => {
    const token = getToken();
    const payload = decodeToken(token);
    if (payload) {
      setUser({
        username: payload.sub,
        role: payload.role,
      });
    }
  }, []);

  /**
   * Fonction pour déconnecter l'utilisateur.
   * 
   * Elle supprime le token JWT, vide l'état utilisateur et redirige vers la page de connexion.
   */
  const logout = () => {
    removeToken();
    setUser(null);
    navigate("/login");
  };

  return (
    <AuthContext.Provider value={{ user, setUser, logout }}>
      {children}
    </AuthContext.Provider>
  );
};


export const useAuth = () => useContext(AuthContext);
export { AuthContext };
