/**
 * Composant React pour la page de connexion utilisateur.
 *
 * Ce composant gère le formulaire de connexion, utilise le service d'authentification,
 * met à jour le contexte global d'authentification, et redirige l'utilisateur après une connexion réussie.
 *
 * 
 * @author Fadhel Smari
 */

import React, { useState, useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import { login as loginService } from '../services/authService';
import { AuthContext } from '../context/AuthContext';
import { toast } from 'react-toastify';

const Login = () => {
  // État local pour stocker les identifiants de connexion (username et mot de passe)
  const [credentials, setCredentials] = useState({ username: '', password: '' });

  // Hook pour naviguer vers une autre page après la connexion
  const navigate = useNavigate();

  // Récupère la méthode login du contexte d'authentification
  const { login } = useContext(AuthContext);

  /**
   * Gère les changements dans les champs du formulaire
   * @param {object} e - L'événement déclenché lors de la saisie utilisateur
   */
  const handleChange = (e) => {
    // Met à jour l'état credentials en conservant les anciennes valeurs
    setCredentials({ ...credentials, [e.target.name]: e.target.value });
  };

  /**
   * Gère la soumission du formulaire
   * - Appelle l'API pour authentifier l'utilisateur
   * - Met à jour le contexte d'authentification
   * - Redirige vers la page d'accueil si succès, ou affiche une erreur sinon
   *
   * @param {object} e - L'événement de soumission du formulaire
   */
  const handleSubmit = async (e) => {
    e.preventDefault(); // Empêche le rechargement de la page
    try {
      const userData = await loginService(credentials); // Appel de l'API backend avec les identifiants
      login(userData); // Mise à jour du contexte d'authentification avec les données reçues
      toast.success('Connexion réussie ✅'); // Notification de succès
      navigate('/Profile'); // Redirection vers la page d'accueil
    } catch (error) {
      toast.error('Échec de la connexion ❌'); // Notification d'erreur
    }
  };

  // Rendu JSX du composant : formulaire de connexion
  return (
    <div className="max-w-md mx-auto mt-20 p-6 border rounded shadow-md bg-white">
      <h2 className="text-2xl font-semibold mb-4 text-center">Connexion</h2>
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          name="username"
          placeholder="Nom d'utilisateur"
          value={credentials.username}
          onChange={handleChange}
          className="w-full p-2 mb-4 border rounded"
          required
        />
        <input
          type="password"
          name="password"
          placeholder="Mot de passe"
          value={credentials.password}
          onChange={handleChange}
          className="w-full p-2 mb-4 border rounded"
          required
        />
        <button
          type="submit"
          className="w-full bg-blue-600 text-white py-2 rounded hover:bg-blue-700"
        >
          Se connecter
        </button>
      </form>
    </div>
  );
};

export default Login;
