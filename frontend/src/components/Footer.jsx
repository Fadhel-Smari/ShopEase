/**
 * Composant Footer de l'application ShopEase.
 *
 * Affiche un pied de page simple avec un texte de copyright.
 *
 * @component
 * @returns {JSX.Element} Le pied de page de l'application.
 */

const Footer = () => {
  return (
    <footer className="bg-gray-100 text-center py-4 mt-8">
      <p className="text-sm text-gray-500">&copy; 2025 ShopEase. Tous droits réservés.</p>
    </footer>
  );
};

export default Footer;
