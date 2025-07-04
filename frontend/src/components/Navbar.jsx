/**
 * Barre de navigation principale de l'application ShopEase.
 *
 * Affiche le nom de l'application et trois liens :
 * Accueil, Connexion et Inscription.
 *
 * @component
 * @returns {JSX.Element} Le composant Navbar
 */

import { Link } from "react-router-dom";

const Navbar = () => {
  return (
    <nav className="bg-blue-600 text-white px-4 py-3 flex justify-between items-center">
      <h1 className="text-xl font-bold">ShopEase</h1>
      <div className="space-x-4">
        <Link to="/" className="hover:underline">Accueil</Link>
        <Link to="/login" className="hover:underline">Connexion</Link>
        <Link to="/register" className="hover:underline">Inscription</Link>
      </div>
    </nav>
  );
};

export default Navbar;
