# 🛍️ Frontend – ShopEase

## 🎯 Introduction

Le frontend de l'application **ShopEase** est développé avec **React** et stylisé avec **Tailwind CSS**.  
Il consomme les API REST sécurisées exposées par le backend Spring Boot, et offre une interface fluide et moderne pour :

- les clients (consultation des produits, panier, commandes, paiement)
- les administrateurs (gestion des utilisateurs, des produits et statistiques)

---

## ⚙️ Technologies utilisées

| Outil               | Version utilisée    | Description                                     |
|---------------------|---------------------|-------------------------------------------------|
| React               | 19.1.0              | Bibliothèque principale                         |
| Tailwind CSS        | 3.4.17              | Framework CSS utilitaire                        |
| React Router DOM    | 6.30.1              | Navigation et routage                           |
| Axios               | 1.10.0              | Requêtes HTTP vers l’API backend                |
| Redux Toolkit       | 2.8.2               | Gestion d’état globale (optionnel mais prévu)   |
| React Toastify      | 11.0.5              | Notifications utilisateur                       |
| Stripe.js           | 7.4.0               | Intégration du paiement Stripe                  |
|Visual Studio Code   | 1.101.2             |  Éditeur de code
---

## Étapes d’installation et configuration (Ubuntu)

### ✅ Prérequis déjà installés

- `Node.js v22.17.0`
- `npm v11.4.2`

---

### ✅ 1. Création du projet React (dans le dossier `frontend`)

```bash
npx create-react-app . --template cra-template
```

### ✅ 2. Nettoyage des fichiers inutiles
```bash
rm -rf src/App.test.js src/logo.svg src/reportWebVitals.js src/setupTests.js
```
### ✅ 3. Installation de Tailwind CSS (version 3.x)
```bash
npm install -D tailwindcss@3 autoprefixer postcss
npx tailwindcss init -p
```
Configuration du fichier tailwind.config.js :

```js
module.exports = {
  content: [
    "./src/**/*.{js,jsx,ts,tsx}",
  ],
  theme: {
    extend: {},
  },
  plugins: [],
}
```
Ajout des directives dans src/index.css :

```css
@tailwind base;
@tailwind components;
@tailwind utilities;
```
### ✅ 4. Installation des bibliothèques nécessaires
```bash
npm install axios react-router-dom@6 react-redux @reduxjs/toolkit react-toastify @stripe/stripe-js
```
### ✅ 5. Démarrage de l’application
```bash
npm start
```
➡️ L’application est disponible sur http://localhost:3000

## Étapes de Développement de l’interface frontend

# Étape 1 – Base du frontend : Layout + Navigation

## 🎯 Objectif

Mettre en place la structure de base de l’interface avec React + Tailwind :
- Navigation avec `react-router-dom`
- Pages publiques (Accueil, Connexion, Inscription)
- Composants globaux (`Navbar`, `Footer`)
- Structure Layout (responsive, avec Tailwind)

## Résultat

- Accès aux pages `/`, `/login`, `/register`, et page 404 (`*`)
- Barre de navigation fonctionnelle
- Layout cohérent et prêt pour les prochaines étapes

## 🧪 Test

Lancer le projet avec :

```bash
npm start
```


