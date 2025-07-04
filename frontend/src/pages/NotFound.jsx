/**
 * Composant React affichant une page d'erreur 404.
 *
 * Ce composant s'affiche lorsque l'utilisateur tente d'accéder à une route inexistante.
 *
 * @component
 * @returns {JSX.Element} Une section avec un message "404 - Page non trouvée"
 */

const NotFound = () => {
  return (
    <div className="p-6 text-center">
      <h2 className="text-2xl font-bold text-red-600">404</h2>
      <p className="text-gray-600">Page non trouvée</p>
    </div>
  );
};

export default NotFound;
