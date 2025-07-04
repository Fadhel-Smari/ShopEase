/**
 * Point d’entrée principal de l’application React.
 *
 * Ce fichier :
 * - importe les dépendances nécessaires,
 * - sélectionne l’élément HTML racine,
 * - rend le composant <App /> dans le DOM via ReactDOM.
 */

import React from "react";
import ReactDOM from "react-dom/client";
import "./index.css";
import App from "./App";

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);
