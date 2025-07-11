/**
 * Composant React utilisé pour restreindre l’accès à certaines routes
 * selon l'état d’authentification et le rôle de l’utilisateur.
 * 
 * Si l’utilisateur n’est pas connecté ou s’il n’a pas le rôle requis, 
 * il est redirigé automatiquement.
 * 
 * @author Fadhel Smari
 */
import { Navigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

/**
 * Composant qui protège une route selon l’authentification et le rôle.
 *
 * @param {JSX.Element} children - Le composant à afficher si l'utilisateur est autorisé
 * @param {Array} allowedRoles - Les rôles autorisés à accéder à cette route
 */
const ProtectedRoute = ({ children, allowedRoles }) => {
  const { user } = useAuth();

  if (!user) {
    // Non connecté : redirection vers la page de connexion
    return <Navigate to="/login" replace />;
  }

  if (allowedRoles && !allowedRoles.includes(user.role)) {
    // Rôle non autorisé : redirection vers la page d'accueil
    return <Navigate to="/" replace />;
  }

  return children;
};

export default ProtectedRoute;
