/**
 * Composant principal de l'application ShopEase.
 * 
 * Il configure la navigation (routing) avec React Router :
 * - Affiche une barre de navigation (Navbar)
 * - Affiche dynamiquement les pages selon l'URL (Home, Login, Register, NotFound)
 * - Affiche le pied de page (Footer)
 *
 * @component
 * @returns {JSX.Element} L'application complète avec le système de routes.
 */

import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Navbar from "./components/Navbar";
import Footer from "./components/Footer";
import Home from "./pages/Home";
import Login from "./pages/Login";
import Register from "./pages/Register";
import NotFound from "./pages/NotFound";

function App() {
  return (
    <Router>
      <div className="flex flex-col min-h-screen">
        <Navbar />
        <main className="flex-grow">
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/login" element={<Login />} />
            <Route path="/register" element={<Register />} />
            <Route path="*" element={<NotFound />} />
          </Routes>
        </main>
        <Footer />
      </div>
    </Router>
  );
}

export default App;
