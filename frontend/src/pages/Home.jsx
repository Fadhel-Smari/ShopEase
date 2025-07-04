/**
 * Page d’accueil de l’application ShopEase.
 *
 * @component
 * @returns {JSX.Element} Composant React qui affiche un message de bienvenue.
 */

const Home = () => {
  return (
    <div className="p-6">
      <h2 className="text-2xl font-bold mb-4">Bienvenue sur ShopEase !</h2>
      <p className="text-gray-600">
        Explorez notre sélection de produits. Connectez-vous pour découvrir encore plus !
      </p>
    </div>
  );
};

export default Home;
