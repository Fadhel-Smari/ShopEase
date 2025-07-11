/**
 * Composant React d'inscription utilisateur.
 *
 * Ce formulaire permet à un utilisateur de saisir ses informations personnelles 
 * pour créer un compte. En cas de succès, un message est affiché et l'utilisateur
 * est redirigé vers la page de connexion.
 * 
 * 
 * @author Fadhel Smari
 */


import { useState } from "react";
import { useNavigate } from "react-router-dom";
import authService from "../services/authService";
import { toast } from "react-toastify";

const Register = () => {
  // Permet de rediriger l'utilisateur après l'inscription
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    firstname: "",
    lastname: "",
    username: "",
    email: "",
    password: "",
    role: "CLIENT",
  });

  /**
   * Met à jour l'état `formData` à chaque saisie utilisateur.
   * Utilise le nom du champ comme clé pour mettre à jour la bonne valeur.
   * 
   * @param {Event} e - L'événement de changement du champ
   */
  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  /**
   * Soumet les données du formulaire à l'API d'inscription.
   * Affiche un message de succès ou d'erreur via un toast.
   * Redirige vers la page de connexion si l'inscription réussit.
   * 
   * @param {Event} e - L'événement de soumission du formulaire
   */
  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await authService.register(formData);
      toast.success("Inscription réussie !");
      navigate("/login");
    } catch (error) {
      toast.error(error?.response?.data?.message || "Erreur lors de l'inscription.");
    }
  };

  return (
    <div className="max-w-md mx-auto mt-10 bg-white p-6 rounded shadow">
      <h2 className="text-2xl font-semibold mb-4 text-center">Inscription</h2>
      <form onSubmit={handleSubmit} className="space-y-4">
        <input name="firstname" placeholder="Prénom" className="input" onChange={handleChange} required />
        <input name="lastname" placeholder="Nom" className="input" onChange={handleChange} required />
        <input name="username" placeholder="Nom d'utilisateur" className="input" onChange={handleChange} required />
        <input type="email" name="email" placeholder="Email" className="input" onChange={handleChange} required />
        <input type="password" name="password" placeholder="Mot de passe" className="input" onChange={handleChange} required />
        <button type="submit" className="w-full bg-blue-600 text-white py-2 rounded hover:bg-blue-700">
          Créer un compte
        </button>
      </form>
    </div>
  );
};

export default Register;
