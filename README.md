# 🛍️ ShopEase Plateforme e-commerce fullstack Java Spring Boot + React

ShopEase est une plateforme e-commerce moderne développée avec des technologies backend robustes telles que **Java**, **Spring Boot**, **Spring Security**, **Hibernate**, et **PostgreSQL**. Ce projet a pour but de créer une application complète de vente en ligne avec des fonctionnalités avancées côté client et administrateur.

---

## ✅ Technologies utilisées (partie backend)

- **Java 21**
- **Spring Boot 3.5.0**
- **Spring Web**
- **Spring Security**
- **Spring Data JPA**
- **PostgreSQL**
- **Maven**
- **IntelliJ IDEA**
- **Git & GitHub**
- **Ubuntu Linux**

---

## 📁 Structure du projet

```bash
/shopease
│
├── backend/         # Projet Spring Boot (déjà initialisé)
├── frontend/        # Projet React (à venir)
├── k8s/             # Fichiers Kubernetes (à venir)
├── docker/          # Dockerfiles & docker-compose (à venir)
├── ci-cd/           # Pipelines CI/CD (à venir)
└── README.md        # Documentation du projet
```

---

## ⚙️ Étapes réalisées

### 1. Initialisation du projet backend
- Généré via [Spring Initializr](https://start.spring.io)
- Configuration avec Maven, Java 21, et les dépendances nécessaires :
  - Spring Web
  - Spring Data JPA
  - Spring Security
  - PostgreSQL Driver
  - Validation
  - DevTools

### 2. Structure du dépôt GitHub
- Création du dossier racine `ShopEase/`
- Initialisation Git
- Ajout du projet Spring Boot dans le dossier `backend/`
- Premier commit et push vers GitHub

---

## 🧱 Structure du backend (Spring Boot)

L'application suit une architecture modulaire organisée selon les bonnes pratiques de développement backend Java avec Spring Boot.

```bash
backend/
└── src/
    └── main/
        ├── java/
        │   └── com/
        │       └── shopease/
        │           ├── config/          # Configurations (sécurité, CORS, JWT, etc.)
        │           ├── controller/      # Contrôleurs REST
        │           ├── dto/             # Objets de transfert de données (DTO)
        │           ├── entity/          # Entités JPA (modèles de base de données)
        │           ├── exception/       # Gestion des exceptions personnalisées
        │           ├── repository/      # Interfaces d'accès aux données (JpaRepository)
        │           ├── security/        # Gestion de la sécurité (JWT, filtres, etc.)
        │           ├── service/         # Logique métier
        │           └── ShopEaseApplication.java  # Classe principale
        └── resources/
            ├── application.properties  # Configuration de l'application (base de données, port, etc.)
            ├── static/                 # (Optionnel) Fichiers statiques
            └── templates/              # (Optionnel) Vues si utilisation de Thymeleaf
```

---

## 🗄️ Configuration de la base de données PostgreSQL

L'application utilise PostgreSQL comme système de gestion de base de données relationnelle.

### 🧱 Étape 1 : Installation de PostgreSQL (Ubuntu)

```bash
sudo apt update
sudo apt install postgresql postgresql-contrib
```
---

### ▶️ Étape 2 : Lancer et vérifier PostgreSQL

```bash
sudo systemctl status postgresql
sudo systemctl start postgresql  # (si nécessaire)
```
### 🧑‍💻 Étape 3 : Création de la base de données et de l'utilisateur

```bash
sudo -i -u postgres
psql
```
Dans le shell psql :
```sql
CREATE DATABASE shopease_db;
CREATE USER admin WITH PASSWORD 'admin';
GRANT ALL PRIVILEGES ON DATABASE shopease_db TO admin;
\q
exit

```

### ⚙️ Étape 4 : Configuration dans application.properties

# Port du serveur
server.port=8080

# Configuration PostgreSQL
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/shopease_db
spring.datasource.username=admin
spring.datasource.password=admin
```
# Configuration Hibernate
```properties
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```
---
✅ Cette configuration permet à l'application Spring Boot de se connecter à la base shopease_db avec l'utilisateur admin.

---

## 👮‍♂️ Permissions PostgreSQL pour l'utilisateur `admin`

Avant de manipuler des données via Spring Boot, il est important de s'assurer que l'utilisateur `admin` dispose de tous les droits nécessaires sur la base `shopease_db`.

### 🧾 Étapes à suivre

#### 1. Se connecter en tant que superutilisateur PostgreSQL :

```bash
sudo -i -u postgres
psql -d shopease_db
```

#### 2. Accorder les droits nécessaires à l'utilisateur admin :

- Autoriser la création d’objets dans le schéma public
```sql
GRANT USAGE, CREATE ON SCHEMA public TO admin;
```
- Accorder les privilèges sur toutes les tables existantes
```sql
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO admin;
```
- Accorder aussi les droits sur les séquences (auto-incréments)
```sql
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO admin;
```

---

## 👤 Création et test de l'entité `User` dans le backend

Pour valider la connexion entre Spring Boot et PostgreSQL, nous avons créé une entité `User` simple, ainsi que les composants nécessaires pour manipuler cette entité via une API REST.

### Structure principale créée :

- **Entité `User`** : représente un utilisateur avec les champs `id`, `username`, `password` et `email`.
- **Repository** : interface `UserRepository` pour les opérations CRUD sur la base.
- **Service** : classe `UserService` pour la logique métier, notamment la gestion des utilisateurs.
- **Controller REST** : `UserController` exposant des endpoints pour créer et récupérer des utilisateurs.

### Fonctionnalités testées :

- Récupérer la liste des utilisateurs via une requête GET.
- Créer un nouvel utilisateur via une requête POST en envoyant un JSON contenant les informations utilisateur.

### Exemple d’appel POST pour créer un utilisateur :

```json
{
  "username": "fadhel",
  "password": "123456",
  "email": "fadhel@example.com"
}
```
---
✅ Cette étape permet de vérifier que la connexion Spring Boot / PostgreSQL fonctionne parfaitement et que les opérations CRUD simples sont opérationnelles.

---

## 🛒 Création et test de l'entité Produits dans le backend

Ce module permet de gérer les produits de la boutique en ligne : ajout, consultation, modification et suppression.

### 🧱 Structure mise en place

- `entity/Product.java` : entité représentant un produit avec les champs :
  - `id`, `name`, `description`, `price`, `imageUrl`, `stock`
- `repository/ProductRepository.java` : interface d’accès aux données via Spring Data JPA.
- `service/ProductService.java` : couche métier pour la gestion des produits.
- `controller/ProductController.java` : API REST pour exposer les opérations CRUD sur les produits.

### 🔌 Endpoints disponibles

- `GET /api/products` → Liste tous les produits
- `GET /api/products/{id}` → Récupère un produit par ID
- `POST /api/products` → Crée un nouveau produit
- `PUT /api/products/{id}` → Met à jour un produit
- `DELETE /api/products/{id}` → Supprime un produit

### 🧪 Exemple JSON pour POST /api/products

```json
{
  "name": "T-shirt oversize",
  "description": "T-shirt en coton biologique avec coupe ample",
  "price": 29.99,
  "imageUrl": "https://example.com/images/tshirt.png",
  "stock": 100
}
```
---
✅ Une fois un produit créé, il est stocké dans la table products de la base de données shopease_db.

---

## 🗂️ Création et test de l'entité Catégories de produits dans le backend 

Ce module permet de gérer les catégories de produits, et d’associer chaque produit à une catégorie. Il est essentiel pour structurer le catalogue et permettre des filtres plus tard.

### 🧱 Structure mise en place

- `entity/Category.java` : entité représentant une catégorie avec les champs :
  - `id`, `name`
- `entity/Product.java` : ajout d’un champ `category` de type `Category` avec `@ManyToOne`
- `repository/CategoryRepository.java` : interface d’accès aux catégories
- `service/CategoryService.java` : logique métier pour les catégories
- `controller/CategoryController.java` : endpoints REST pour gérer les catégories

### 🔌 Endpoints disponibles

#### 🔹 Catégories

- `GET /api/categories` → Liste toutes les catégories
- `GET /api/categories/{id}` → Récupère une catégorie par ID
- `POST /api/categories` → Crée une nouvelle catégorie
- `DELETE /api/categories/{id}` → Supprime une catégorie

#### 🔹 Produits

- `POST /api/products` → Crée un produit **avec une catégorie**

### 🧪 Exemples de tests avec Postman

#### 1. Créer une catégorie

**POST** `/api/categories`

```json
{
  "name": "T-shirts"
}
```
➡️ Réponse :

```json
{
  "id": 1,
  "name": "T-shirts"
}
```
#### 2. Créer un produit avec une catégorie
POST /api/products

```json
{
  "name": "T-shirt bleu oversize",
  "description": "T-shirt ample en coton biologique",
  "price": 24.99,
  "imageUrl": "https://example.com/images/tshirt-blue.png",
  "stock": 80,
  "category": {
    "id": 1
  }
}
```
➡️ Le produit sera lié à la catégorie "T-shirts" (ID 1).
---

✅ Cette étape permet désormais d’organiser les produits par catégorie dans la base de données shopease_db.

---

## 🔐 Module d’authentification

Ce module permet la gestion des utilisateurs avec rôles (CLIENT ou ADMIN), l’inscription, la connexion, et la future génération de **JWT** pour sécuriser les accès aux API.

### ⚙️ Dépendances ajoutées dans `pom.xml`

- JSON Web Token (JJWT) :
  * jjwt-api
  * jjwt-impl
  * jjwt-jackson

### 🧱 Étapes réalisées

#### 🔸 1. Ajout du rôle utilisateur

- Création d’un fichier `Role.java` dans le package `enums` :
  
```java
public enum Role {
    ADMIN,
    CLIENT
}
```
- Ajout d’un champ role dans l’entité User :

```java
@Enumerated(EnumType.STRING)
private Role role;
```

#### 🔸 2. Création des classes DTO (Data Transfer Objects) pour la gestion d'authentification

Afin de structurer proprement les échanges de données entre le frontend et le backend, j'ai créé un package `dto` contenant les classes suivantes :

- `RegisterRequest` : contient les informations d'inscription envoyées par l'utilisateur (`firstname`, `lastname`, `username`, `email`, `password`).
- `LoginRequest` : contient les identifiants de connexion (`username`, `password`).
- `AuthResponse` : contient la réponse après authentification réussie (token JWT).

#### 🎯 Objectifs de cette approche :
- Séparer la logique métier (`User`) des objets échangés via l'API (meilleure lisibilité et évolutivité).
- Protéger les données sensibles (ne jamais exposer directement l’entité `User`).
- Faciliter la validation des champs utilisateur avec Spring Boot.
- Permettre une plus grande flexibilité dans l’évolution du frontend et du backend.

✅ Cette approche respecte les bonnes pratiques d’architecture en Java/Spring, notamment la séparation des responsabilités (SoC) et l'encapsulation des données.

#### 🔸 3. Création des Services JWT (gestion de tokens JWT) et Auth (uthentification)

Cette étape met en place le cœur du système d’authentification basé sur **JWT**. Elle permet de :
- Générer un token sécurisé lors de l’inscription ou de la connexion
- Vérifier la validité d’un token (signature, expiration, correspondance avec l’utilisateur)

### 🔧 Composants ajoutés

#### ✅ `JwtService.java`

- Génère un token JWT signé pour un utilisateur, en incluant son `username` dans le payload.
- Extrait l’identité (nom d’utilisateur) à partir du token (`extractUsername()`).
- Vérifie si le token est expiré ou toujours valide.
- Utilise l’algorithme **HMAC-SHA256** avec une clé secrète pour signer les tokens.  

Ce service est centralisé afin de **séparer la logique cryptographique** du reste du code métier.

#### ✅ `AuthService.java`
- **Inscription (`register`)** :
  - Reçoit les données du DTO `RegisterRequest`
  - Encode le mot de passe avec `PasswordEncoder`
  - Crée un utilisateur avec un rôle par défaut (ex. : `CLIENT`)
  - Sauvegarde l’utilisateur
  - Génère un JWT et le renvoie dans un objet `AuthResponse`

- **Connexion (`authenticate`)** :
  - Vérifie les identifiants : `mot de passe` et `username`
  - Génère un JWT et le renvoie dans `AuthResponse`
  
#### 🔸 4. Création de controlleur AuthController

Exposer les endpoints HTTP permettant aux clients de s’inscrire ou de se connecter à la plateforme **ShopEase**. Ces opérations utilisent le service `AuthService` et retournent un **token JWT** en réponse.

### 🌐 Composant ajouté

#### ✅ `AuthController.java`
- Contrôleur REST accessible via `/api/auth`
- Deux endpoints disponibles :

| Méthode | URL               | Description            |
|---------|-------------------|------------------------|
| POST    | `/register`       | Inscription d’un utilisateur |
| POST    | `/login`          | Authentification d’un utilisateur |

- Chaque requête utilise un DTO (`RegisterRequest` ou `LoginRequest`) et retourne un `AuthResponse` contenant le **JWT**

#### 🔸 5. Configuration de la sécurité avec Spring Security

Configurer la sécurité de l’application pour :
- Autoriser librement les endpoints d’authentification (`/api/auth/**`)
- Protéger toutes les autres routes, nécessitant une authentification
- Désactiver la gestion de session (stateless API REST)
- Désactiver CSRF (non nécessaire pour API REST)
- Configurer l’encodeur de mot de passe (BCrypt)

### 🔧 Composant ajouté

#### ✅ `SecurityConfig.java`
- Définit un `SecurityFilterChain` avec les règles de sécurité
- Utilise les nouvelles méthodes recommandées dans Spring Security 6.2+
- Fournit un bean `PasswordEncoder` pour encoder les mots de passe
- Fournit un bean `AuthenticationManager` nécessaire pour l’authentification dans le service

#### 🔸 6. Gestion de l’utilisateur authentifié avec Spring Security

### 📌 Objectif
Dans le cadre de l'authentification sécurisée avec Spring Security, deux classes sont nécessaires :

- Une classe `CustomUserDetails` qui implémente `UserDetails`, utilisée pour représenter un utilisateur connecté dans le contexte de sécurité.
- Une classe `CustomUserDetailsService` qui implémente `UserDetailsService` et permet à Spring de charger un utilisateur depuis la base de données à partir de son nom d'utilisateur.

---

### 🎯 Pourquoi ne pas utiliser directement l’entité `User` ?

- **Séparation des responsabilités** : `User` est une entité JPA persistée en base, tandis que `CustomUserDetails` est utilisée uniquement dans le contexte de Spring Security.
- **Souplesse et maintenabilité** : toute la logique de sécurité reste découplée de la couche de persistance.
- **Meilleures pratiques** : éviter de polluer les entités métiers avec des dépendances du framework de sécurité.

---

### ✅ Fonctionnalités ajoutées

- Création de la classe `CustomUserDetails` :
  - Implémente `UserDetails`
  - Fournit les informations nécessaires à l’authentification : `username`, `password`, rôles (`ROLE_ADMIN`, `ROLE_CLIENT`)
 
- Création de la classe `CustomUserDetailsService` :
  - Implémente `UserDetailsService`
  - Charge un utilisateur depuis la base via `UserRepository`
  - Vérifie si l’utilisateur existe et renvoie un objet `CustomUserDetails`
  
#### 🔸 7. Création du filtre JWT

Ajouter un filtre personnalisé pour :
- Intercepter chaque requête HTTP
- Vérifier la présence d’un token JWT valide dans le header `Authorization`
- Extraire le nom d'utilisateur (email) du token
- Charger l’utilisateur depuis la base de données
- Authentifier l’utilisateur dans le contexte de Spring Security

### 🔧 Composant ajouté

#### ✅ `JwtAuthenticationFilter.java` (dans le package `config`)
- Extends `OncePerRequestFilter` pour garantir une exécution unique par requête
- Vérifie la présence d’un header `Authorization: Bearer <token>`
- Utilise le `JwtService` pour extraire et valider le token
- Charge l’utilisateur depuis la BD avec `UserRepository`
- Authentifie l’utilisateur dans Spring Security (`SecurityContextHolder`)

#### 🔸 8. Configuration de la sécurité avec le filtre JWT

### ⚙️ Modifications apportées à `SecurityConfig.java` pour la gestion du JWT

- Ajout du **filtre `JwtAuthenticationFilter`** dans la chaîne de sécurité, placé **avant** `UsernamePasswordAuthenticationFilter`.
- Intégration d’un **`AuthenticationProvider` personnalisé** (`DaoAuthenticationProvider`) configuré avec :
  - le service `CustomUserDetailsService` pour charger les utilisateurs
  - un `PasswordEncoder` utilisant l’algorithme `BCrypt`
- Passage de la politique de session à **stateless** avec `SessionCreationPolicy.STATELESS` pour refléter le fonctionnement des JWT (aucune session stockée côté serveur).
- Conservation des accès libres à la route `/api/auth/**` (inscription et connexion).
- Protection de toutes les autres routes par authentification JWT.

## 🧪 Tests – Authentification via Postman

### 📌 Objectif
Tester les routes principales d’authentification :
- `/api/auth/register` : inscription
- `/api/auth/login` : connexion
- Accès à une route protégée avec un token JWT


### 1️⃣ Inscription (POST /api/auth/register)

**URL :**
```bash
POST http://localhost:8080/api/auth/register
```
**Body (JSON) :**
```json
{
  "firstname": "Fadhel",
  "lastname": "Smari",
  "username": "fadhel123",
  "email": "fadhel@example.com",
  "password": "123456",
  "role": "CLIENT"
}
```
**Réponse :**
```json
{
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJmYWRoZWwxMjMiLCJpYXQiOjE3NDk0MDQyNjEsImV4cCI6MTc0OTQ5MDY2MX0.EikfjsVUHDtjPQAUou-EvdIXP_jRS8evpdQ-agVtVhw"
}
```

### 2️⃣ Connexion (POST /api/auth/login)
**URL :**
```bash
POST http://localhost:8080/api/auth/login
```
**Body (JSON) :**

```json
{
  "username": "fadhel123",
  "password": "123456"
}
```
**Réponse :**
```json
{
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJmYWRoZWwxMjMiLCJpYXQiOjE3NDk0MDQyNzEsImV4cCI6MTc0OTQ5MDY3MX0.eaO2jJwvUTVfcEx7XyYU25AgIRhyqjAi45TxgQUbvSo"
}
```
### 3️⃣ Accès à une route protégée (GET /api/users par exemple)
**URL :**

```bash
GET http://localhost:8080/api/users
```
**Headers :**

```makefile
Authorization: Bearer <token>
```
✅ Remplace <token> par le token obtenu lors du login.

**Résultat attendu :**

Si le token est valide → accès autorisé.

Sinon → réponse 403 (forbidden) ou 401 (unauthorized).

---

## 👤 Module : Gestion du profil utilisateur

### 🎯 Objectif
Ce module permet à un utilisateur connecté de :
- Consulter ses informations personnelles
- Mettre à jour ses données (prénom, nom, email)

Il repose sur l’identification de l’utilisateur à partir du JWT contenu dans le `SecurityContext`.

---

### 🧱 Étape 1 : Création des DTOs du profil utilisateur

#### ✅ `UpdateProfileRequest`
- Sert de **corps de la requête** lors d'une mise à jour de profil
- Contient les champs :
  - `firstname`
  - `lastname`
  - `email`

#### ✅ `UserProfileResponse`
- Sert de **corps de réponse** après consultation ou mise à jour du profil
- Contient les champs :
  - `firstname`
  - `lastname`
  - `username`
  - `email`
  - `role`

---

### 🧱 Étape 2 : Ajout des fonctionnalités de gestion de profil dans `UserService`

Le service `UserService` gère la logique métier liée au profil utilisateur connecté.

#### Méthodes principales :
- `getUserProfile()` :  
  Récupère les données du profil en fonction du nom d’utilisateur authentifié (via JWT).
- `updateUserProfile(UpdateProfileRequest updateRequest)` :  
  Met à jour le prénom, nom et email de l’utilisateur connecté, puis sauvegarde en base.

---

### 🧱 Étape 3 : Ajout des endpoints de gestion de profil dans `UserController`

#### 📌 Objectif

Cette étape consiste à exposer deux nouvelles API REST dans le contrôleur `UserController` pour permettre à l'utilisateur connecté de consulter et modifier son profil.

#### 🔍 Détails

- **GET /api/users/profile** :  
  Récupère les informations du profil de l'utilisateur authentifié.  
  Le contrôleur ne reçoit pas le nom d'utilisateur, il délègue au service qui utilise le contexte de sécurité Spring Security pour identifier l'utilisateur.

- **PUT /api/users/profile** :  
  Met à jour les informations du profil de l'utilisateur connecté à partir des données fournies dans la requête.  
  Là aussi, la récupération de l'identité se fait dans le service.

## 🧪 Tests – Gestion du profil utilisateur via Postman

### 📌 Objectif
Tester les endpoints liés à la gestion du profil utilisateur, en s'assurant que :
- L'utilisateur authentifié peut consulter son profil via `GET /api/users/profile`
- L'utilisateur authentifié peut modifier son profil via `PUT /api/users/profile`
- Les modifications sont bien prises en compte dans la base de données
- L’accès aux routes protégées nécessite un token JWT valide


### 1️⃣ Consultation du profil (GET /api/users/profile)

**URL :**
```bash
GET http://localhost:8080/api/users/profile
```

**Headers :**
```mikefile
Authorization: Bearer <token_jwt_valide>
```

**Réponse :**
```json
{
  "firstname": "Fadhel",
  "lastname": "Smari",
  "username": "fadhel123",
  "email": "fadhel@example.com",
  "role": "CLIENT"
}
```


### 2️⃣ Mise à jour du profil (PUT /api/users/profile)
```bash
PUT http://localhost:8080/api/users/profile
```
**Headers :**
```mikefile
Authorization: Bearer <token_jwt_valide>
```

**Body (JSON) :**

```json
{
  "firstname": "FadhelModifié",
  "lastname": "SmariModifié",
  "email": "fadhelmodifie@example.com"
}
```
**Réponse :**
```json
{
  "firstname": "FadhelModifié",
  "lastname": "SmariModifié",
  "username": "fadhel123",
  "email": "fadhelmodifie@example.com",
  "role": "CLIENT"
}
```
---

## 🔎 Module : Recherche avancée de produits avec filtres dynamiques (JPA Specifications)

### 📌 Introduction
Ce module introduit une **recherche multi-critères avancée** dans l’API des produits à l’aide de `Specifications` de Spring Data JPA.  
Il permet d’enchaîner dynamiquement des conditions (nom, catégorie, prix, stock) sans devoir écrire manuellement des requêtes SQL complexes.

---

### 🎯 Pourquoi utiliser `Specifications` ?

- 🔁 Tous les filtres sont **optionnels**
- 🧩 Requête SQL générée dynamiquement à partir des critères fournis
- 🧼 Séparation claire de la logique métier
- ✅ Recommandé pour des API REST robustes

---

### 🧱 Étape 1 : Création de la classe `ProductSpecification` – Recherche multi-critères dynamique

### 🛠️ Filtres pris en charge :

- 🔍 `name` (nom du produit, recherche floue)
- 📂 `categoryId` (filtrage par catégorie)
- 💰 `minPrice` / `maxPrice` (intervalle de prix)
- 📦 `inStock` (produits en stock)

## 🧱 Étape 2 : Mise à jour de `ProductRepository` – Support des Specifications

### 📌 Objectif
Adapter le dépôt `ProductRepository` pour permettre l’utilisation des critères dynamiques définis dans `ProductSpecification`.

---

### 🎯 Détail des modifications

- Le dépôt `ProductRepository` hérite maintenant de l’interface `JpaSpecificationExecutor<Product>`.
- Cela permet d’utiliser la méthode `findAll(Specification spec)` avec des critères composables dynamiquement.
- Aucun code supplémentaire requis : Spring Data JPA s’occupe de tout.

## 🧱 Étape 3 : Création des DTO pour le module Produits

Dans cette étape, nous avons créé deux DTO principaux pour le module Produits :

- `ProductFilterRequest` : représente les critères de recherche et de filtrage que l’utilisateur peut soumettre pour rechercher des produits (ex : nom, catégorie, fourchette de prix).
- `ProductResponse` : structure les données retournées par l’API pour chaque produit, en incluant les informations pertinentes comme le nom, la description, le prix, le stock, et la catégorie.

## 🧱 Étape 4 : Ajout de la logique métier dans ProductService
📌 Objectif
Compléter la logique métier du module produits en utilisant les DTO pour exposer les données, et intégrer la recherche avancée dynamique à l’aide des Specifications JPA.

✅ Modifications apportées
- Conversion vers `ProductResponse` dans toutes les méthodes de lecture
    - Remplace les entités exposées directement par des DTOs (List<ProductResponse>, ProductResponse).

- Gestion de l’exception `ResourceNotFoundException` pour `getProductById`

- Ajout de la méthode `searchProducts()` avec support de :

    - Filtrage par nom (name)

    - Filtrage par categoryId

    - Filtrage par plage de prix (minPrice, maxPrice)

- Méthode privée `mapToProductResponse(Product)` pour centraliser la conversion vers le DTO

## 🧱 Étape 5 : Mise à jour de ProductController avec les DTOs et filtres dynamiques
📌 Objectif
Remplacer les retours d’entités Product par des DTOs ProductResponse, ajouter le support des filtres via ProductFilterRequest, et simplifier les retours (plus de Optional).

✅ Changements effectués
💡 Utilisation de ProductResponse dans les méthodes getAllProducts() et getProductById(Long id)

🆕 Ajout d’un endpoint POST /api/products/search pour permettre la recherche de produits avec des filtres dynamiques :

- nom partiel (name)
- catégorie (categoryId)
- plage de prix (minPrice, maxPrice)

🧪 Tests – Module Produits via Postman
📌 Objectif
Vérifier le bon fonctionnement des endpoints liés aux produits :

1️⃣ Rechercher des produits avec filtres (POST /api/products/search)

**URL :**

```http
POST http://localhost:8080/api/products/search
```
**Body (JSON) :**

```json
{
  "name": "chaussure",
  "categoryId": 2,
  "minPrice": 50,
  "maxPrice": 150
}
```
🧠 Tous les champs sont optionnels.

✅ Résultat attendu : Liste filtrée de ProductResponse selon les critères.


# 🛡️ Sécurisation des routes REST avec les rôles (CLIENT / ADMIN)

## 🎯 Objectif

Restreindre l'accès aux endpoints REST selon les rôles des utilisateurs (`CLIENT` ou `ADMIN`) à l’aide des annotations `@PreAuthorize`.  
Cela permet de protéger certaines routes sensibles (ex. : création, suppression d’utilisateurs) tout en permettant l’accès au profil utilisateur pour les utilisateurs connectés.

---

## ✅ Étape 1 : Sécurisation du contrôleur `UserController`

### 📌 Modifications effectuées :

1. **Activation de la sécurité par méthode :**
   - Ajout de l’annotation `@EnableMethodSecurity` dans la classe `SecurityConfig`.

2. **Ajout des rôles avec `@PreAuthorize` dans `UserController` :**
   - Seuls les `ADMIN` peuvent accéder aux méthodes globales (`getAllUsers`, `createUser`).
   - Les utilisateurs ayant les rôles `CLIENT` ou `ADMIN` peuvent consulter ou modifier leur **profil**.

### 🔐 Détail des autorisations appliquées :

| Méthode                         | Endpoint                    | Accès autorisé à              |
|----------------------------------|------------------------------|-------------------------------|
| `GET`  `/api/users`              | Liste des utilisateurs       | `ADMIN` uniquement            |
| `POST` `/api/users`              | Création d’un utilisateur    | `ADMIN` uniquement            |
| `GET`  `/api/users/profile`      | Voir son propre profil       | `CLIENT` ou `ADMIN`           |
| `PUT`  `/api/users/profile`      | Modifier son propre profil   | `CLIENT` ou `ADMIN`           |

---

## 🧪 Tests via Postman

- ✅ Accès à `/api/users/profile` avec un `JWT` de rôle `CLIENT` → **autorisé**
- ❌ Accès à `/api/users` avec un `JWT` de rôle `CLIENT` → **interdit (403)**
- ✅ Accès à `/api/users` avec un `JWT` de rôle `ADMIN` → **autorisé**


## ✅ Étape 2 : Sécurisation du contrôleur `ProductController`

### 📌 Objectif

Restreindre l’accès aux fonctionnalités de gestion des produits selon les rôles :

- `CLIENT` : peut uniquement consulter les produits ou effectuer une recherche.
- `ADMIN` : peut créer, modifier ou supprimer des produits.

---

### 🔐 Règles de sécurité appliquées à `ProductController` :

| Méthode HTTP | Endpoint                    | Description                          | Accès autorisé à     |
|--------------|------------------------------|--------------------------------------|-----------------------|
| `GET`        | `/api/products`              | Lister tous les produits             | `CLIENT`, `ADMIN`     |
| `GET`        | `/api/products/{id}`         | Consulter un produit par ID          | `CLIENT`, `ADMIN`     |
| `POST`       | `/api/products/search`       | Recherche filtrée                    | `CLIENT`, `ADMIN`     |
| `POST`       | `/api/products`              | Créer un nouveau produit             | `ADMIN` uniquement     |
| `PUT`        | `/api/products/{id}`         | Modifier un produit existant         | `ADMIN` uniquement     |
| `DELETE`     | `/api/products/{id}`         | Supprimer un produit                 | `ADMIN` uniquement     |

---

## 🧪 Tests via Postman

- ✅ Accès à `GET /api/products` avec un JWT de rôle `CLIENT` → **autorisé**
- ✅ Accès à `GET /api/products/{id}` avec un JWT de rôle `CLIENT` → **autorisé**
- ✅ Accès à `POST /api/products/search` avec un JWT de rôle `CLIENT` → **autorisé**

- ❌ Accès à `POST /api/products` avec un JWT de rôle `CLIENT` → **interdit (403)**
- ❌ Accès à `PUT /api/products/{id}` avec un JWT de rôle `CLIENT` → **interdit (403)**
- ❌ Accès à `DELETE /api/products/{id}` avec un JWT de rôle `CLIENT` → **interdit (403)**

- ✅ Accès complet à toutes les routes `/api/products/**` avec un JWT de rôle `ADMIN` → **autorisé**


## ✅ Étape 3 : Sécurisation du contrôleur `CategoryController`

### 📌 Objectif

Restreindre l’accès aux fonctionnalités de gestion des catégories selon les rôles :

- `CLIENT` : peut uniquement consulter les catégories.
- `ADMIN` : peut créer ou supprimer une catégorie.

---

### 🔐 Règles de sécurité appliquées à `CategoryController` :

| Méthode HTTP | Endpoint                | Description             | Accès autorisé à     |
|--------------|------------------------|-------------------------|---------------------|
| `GET`        | `/api/categories`      | Lister toutes les catégories | `CLIENT`, `ADMIN`   |
| `GET`        | `/api/categories/{id}` | Consulter une catégorie par ID | `CLIENT`, `ADMIN`   |
| `POST`       | `/api/categories`      | Créer une nouvelle catégorie | `ADMIN` uniquement  |
| `DELETE`     | `/api/categories/{id}` | Supprimer une catégorie | `ADMIN` uniquement  |

---
### 🧪 Tests recommandés (Postman)

- ✅ CLIENT peut consulter toutes les catégories et une catégorie par ID

- ❌ CLIENT ne peut pas créer ni supprimer une catégorie → 403 Forbidden

- ✅ ADMIN a un accès complet à toutes les routes catégories


# 🛒 Module Panier

## 🎯 Introduction

Le module **Panier** permet aux utilisateurs de gérer leur panier d'achat dans l'application ShopEase. Ce module offre les fonctionnalités suivantes :

- Ajouter un produit au panier
- Modifier la quantité d’un produit dans le panier
- Supprimer un produit du panier
- Consulter le contenu du panier avec les totaux

Le module est construit autour de l'entité `CartItem` qui représente un article dans le panier d’un utilisateur.

---

## ✅ Étape 1 : Création de l'entité `CartItem`

L'entité `CartItem` correspond à un article dans le panier d’un utilisateur. Chaque article est lié à un utilisateur et à un produit spécifique, avec une quantité et un total calculé (prix unitaire × quantité).

### 📌 Principales propriétés de `CartItem` :

- `id` : identifiant unique de l'article dans le panier
- `user` : l’utilisateur auquel appartient cet article de panier
- `product` : le produit sélectionné
- `quantity` : la quantité choisie par l'utilisateur
- `totalPrice` : le prix total pour cet article (prix unitaire multiplié par la quantité)

## ✅ Étape 2 : Création de l'interface CartItemRepository

## 🎯 Objectif

Mettre en place l'interface `CartItemRepository` pour gérer la persistance des éléments du panier (`CartItem`) en base de données.
Cette interface étend `JpaRepository` afin de bénéficier des méthodes CRUD standard fournies par Spring Data JPA.


### 📌 Modifications effectuées :

- Création de l'interface `CartItemRepository` dans le package `repository`.
- Extension de `JpaRepository<CartItem, Long>` pour la gestion des opérations CRUD.

## ✅ Étape 3 : Création des DTOs pour la gestion du panier

## 🎯 Objectif

Créer les Data Transfer Objects (DTO) pour la gestion des interactions avec le panier :

- **CartItemRequest** : Représente les données reçues pour ajouter ou modifier un article dans le panier (ex. : id du produit, quantité).
- **CartItemResponse** : Structure les données envoyées pour un article du panier (ex. : id, nom produit, quantité, prix).
- **CartResponse** : Contient l’état complet du panier, incluant la liste des articles et le total.

---

### 📌 Modifications effectuées :

- Ajout de la classe `CartItemRequest` dans `dto/` pour recevoir les données client.
- Ajout de la classe `CartItemResponse` dans `dto/` pour renvoyer les détails d’un item du panier.
- Ajout de la classe `CartResponse` dans `dto/` pour représenter la vue globale du panier.

## ✅ Étape 4 : Développement du service CartService

## 🎯 Objectif

Définir et implémenter la logique métier pour la gestion du panier :
- ajout d’articles,
- suppression,
- mise à jour des quantités,
- récupération de l’état complet du panier.

Ces fonctionnalités utilisent `CartItemRepository` pour l’accès aux données.

---

### 📌 Modifications effectuées :

- Création de l’interface `CartService` avec les méthodes métier clés.
- Implémentation concrète dans `CartServiceImpl`.
- Utilisation de `CartItemRepository` pour manipuler les données en base.

## ✅ Étape 5 : Création du contrôleur `CartController`

## 🎯 Objectif

Exposer les opérations du panier via une API RESTful accessible aux utilisateurs authentifiés.  
Permettre à un utilisateur connecté (ayant le rôle `CLIENT`) de :

- consulter son panier,
- ajouter un produit au panier,
- mettre à jour la quantité d’un produit,
- supprimer un produit du panier.

### 📌 Modifications effectuées :

1. Création de la classe `CartController.java` dans `controller/`
2. Protection des routes avec `@PreAuthorize("hasRole('CLIENT')")`
3. Injection du `CartService` pour gérer les opérations métier.

---
# 🧪 Tests – Module Panier via Postman

## 📌 Objectif

Vérifier le bon fonctionnement des endpoints liés à la gestion du panier pour un utilisateur connecté (`CLIENT`), notamment :

- ✅ Ajout de produits au panier  
- ✅ Mise à jour de la quantité d’un produit  
- ✅ Consultation du panier  
- ✅ Suppression d’un article du panier

> ⚠️ Tous les appels nécessitent un **JWT valide** dans l'en-tête `Authorization`.

---

## ✅ 1️⃣ Ajouter un produit au panier

**URL :**

```http
POST http://localhost:8080/api/cart/add
```
**Headers :**

```pgsql
Authorization: Bearer <JWT_TOKEN>
Content-Type: application/json
```
**Body (JSON) :**

```json
{
  "productId": 1,
  "quantity": 2
}
```
## ✅ 2️⃣ Ajouter un deuxième produit au panier
**URL :**

```http
POST http://localhost:8080/api/cart/add
```
**Headers :**

```pgsql
Authorization: Bearer <JWT_TOKEN>
Content-Type: application/json
```

**Body (JSON) :**

```json
{
  "productId": 2,
  "quantity": 1
}
```

## ✅ 3️⃣ Récupérer le contenu du panier
**URL :**

```http
GET http://localhost:8080/api/cart
```
**Headers :**

```makefile
Authorization: Bearer <JWT_TOKEN>
```

## ✅ 4️⃣ Mettre à jour la quantité d’un article
**URL :**

```http
PUT http://localhost:8080/api/cart/1/quantity/4
```
Remplace 1 par l’ID réel du cartItem à modifier.

**Headers :**

```makefile
Authorization: Bearer <JWT_TOKEN>
```

## ✅ 5️⃣ Supprimer un article du panier
**URL :**

```http
DELETE http://localhost:8080/api/cart/1
```
Remplace 1 par l’ID réel du cartItem à supprimer.

**Headers :**

```makefile
Authorization: Bearer <JWT_TOKEN>
```

# 📦 Module Commandes

Le module Commandes gère la création, le suivi et l’historique des commandes passées par les utilisateurs.
Il permet de stocker les informations relatives à chaque commande, notamment l’utilisateur, les produits commandés, les quantités, les statuts et les dates.

## ✅ Étape 1 : Enumération et Entités `Order` et `OrderItem`

## 🎯 Objectif

- Définir les statuts possibles d'une commande avec l'`enum OrderStatus`.
- Créer les entités JPA `Order` et `OrderItem` avec les relations nécessaires :
  - Un utilisateur peut avoir plusieurs commandes (`Order`).
  - Une commande contient plusieurs items (`OrderItem`).
  - Chaque `OrderItem` est lié à un produit spécifique.

## ⚙️ Détails techniques

- `OrderStatus` : enum avec les valeurs `PENDING`, `CONFIRMED`, `SHIPPED`, `DELIVERED`.
- `Order` :
  - Identifiant, utilisateur, date de commande, statut, montant total.
  - Liste des `OrderItem`.
- `OrderItem` :
  - Identifiant, référence vers la commande, produit, quantité, prix unitaire.
  
## ✅ Étape 2 : DTOs

## 🎯 Objectif

- Créer les objets de transfert (DTO) pour la commande et ses items.
- Faciliter l’échange de données entre le frontend et le backend sans exposer directement les entités JPA.

## Détails des DTOs

- `OrderRequest` : Requête pour créer une commande, contient la liste des `OrderItemRequest`.
- `OrderItemRequest` : Requête pour un item avec `productId` et `quantity`.
- `OrderResponse` : Réponse complète avec id, utilisateur, date, statut, total et liste d’items.
- `OrderItemResponse` : Réponse pour un item, avec id produit, nom, quantité, prix.

## ✅ Étape 3 : Repositories

## 🎯 Objectif

Créer les interfaces JPA pour accéder à la base de données et gérer les entités :

- `Order` : pour la commande complète.
- `OrderItem` : pour les produits inclus dans une commande.

## Détails
`OrderRepository` :
- Hérite de `JpaRepository<Order, Long>`.
- Ajout d'une méthode personnalisée :
  - `List<Order> findByUserId(Long userId)` : permet d’obtenir toutes les commandes d’un utilisateur.

`OrderItemRepository` :
- Hérite de `JpaRepository<OrderItem, Long>`.
- Gère les accès aux items de commande.

## ✅ Étape 4 : Service & Implémentation du module Commandes

## 🎯 Objectif

Développer la couche métier du module commandes pour :
- Créer une commande à partir du panier de l'utilisateur
- Récupérer l’historique de commandes
- Consulter une commande spécifique
- Supprimer une commande si elle est encore modifiable (statut DRAFT ou PENDING)
- Mettre à jour le statut de la commande (ex. : passer de DRAFT à PENDING)

---

## 📌 Classes créées

### ✅ `OrderService.java` (interface)
Définit les méthodes principales :
- `OrderResponse createOrder(Long userId)`
- `List<OrderResponse> getOrdersByUser(Long userId)`
- `OrderResponse getOrderById(Long orderId, Long userId)`
- `void deleteOrder(Long orderId, Long userId)`
- `OrderResponse updateOrderStatus(Long orderId, OrderStatus newStatus, Long userId)`

### ✅ `OrderServiceImpl.java` (implémentation)
Implémente la logique métier :

#### 📦 Création de commande
- Génère une commande à partir des articles du panier.
- Calcule le total.
- Sauvegarde la commande et les articles associés.
- Vide le panier de l'utilisateur après commande.

#### 🔍 Lecture
- Récupération des commandes d’un utilisateur.
- Détail d’une commande spécifique si elle lui appartient.

#### ❌ Suppression
- Autorisée uniquement si le statut de la commande est `DRAFT` ou `PENDING`.

#### 🔁 Mise à jour du statut
- Possible uniquement tant que la commande n’est pas confirmée ou payée.

---

## ⚠️ Gestion des exceptions

- `ResourceNotFoundException` : utilisateur, commande ou panier inexistant
- `ForbiddenActionException` : tentative d’accès à une commande d’un autre utilisateur
- `BadRequestException` : tentative de suppression ou modification d’une commande confirmée

---

## ✅ Étape 5 : Création du contrôleur OrderController

## 🎯 Objectif

Exposer les endpoints REST pour permettre aux utilisateurs **authentifiés (role CLIENT)** de :
- Créer une commande à partir de son panier
- Consulter ses commandes (liste ou par ID)
- Supprimer une commande si elle est au statut DRAFT ou PENDING
- Modifier le statut d'une commande avant traitement

---

## 📌 Endpoints ajoutés :

| Méthode | Endpoint | Description |
|--------|----------|-------------|
| POST   | `/api/orders` | Créer une nouvelle commande à partir du panier |
| GET    | `/api/orders` | Obtenir toutes les commandes de l'utilisateur |
| GET    | `/api/orders/{orderId}` | Détails d'une commande spécifique |
| DELETE | `/api/orders/{orderId}` | Supprimer une commande (si modifiable) |
| PUT    | `/api/orders/{orderId}/status?status=...` | Mettre à jour le statut |

---

## 🔐 Sécurité :
- Tous les endpoints sont restreints au rôle `CLIENT` via `@PreAuthorize("hasRole('CLIENT')")`.

---


# 🧪 Tests – Module Commandes via Postman

## 🛠️ Préparation : Base de données de test (`data.sql`)

Pour faciliter les tests, une base de données de démonstration a été initialisée automatiquement grâce à un fichier `data.sql`.  
Ce fichier insère des données représentatives dans les tables suivantes :

- 👤 **4 utilisateurs** (1 ADMIN, 3 CLIENTS)
- 🗂️ **6 catégories** (Électronique, Vêtements, etc.)
- 🛍️ **24 produits** répartis dans les catégories
- 🛒 **Paniers** contenant des articles pour chaque client
- 📦 **Commandes** avec plusieurs articles pour chaque client

Ces données permettent de simuler des cas réels de création, consultation, mise à jour ou suppression de commandes.

---

## 📌 Objectif des tests

Tester les endpoints REST liés aux commandes (`/api/orders`) :

- ✅ Créer une commande à partir du panier
- 📥 Récupérer toutes les commandes d’un client
- 🔍 Consulter une commande spécifique par son ID
- ❌ Supprimer une commande (si encore au statut `DRAFT` ou `PENDING`)
- 🔄 Mettre à jour le statut d’une commande (ex. : de `DRAFT` à `PENDING`)

---

✅ Exécution recommandée
- Connectez-vous avec l’un des clients (par exemple ali123, sara456, mehdi789)

- Récupérez leur token via /api/auth/login

- Testez les commandes selon leurs paniers et commandes pré-existants

## 1️⃣ Créer une commande

**URL :**
```http
POST http://localhost:8080/api/orders
```
**Headers :**

```pgsql
Authorization: Bearer <token_du_client>
Content-Type: application/json
```
**Body :**

```json
// Aucun corps requis
```

**Description :**
Crée une commande à partir des articles présents dans le panier de l’utilisateur connecté.

2️⃣ Récupérer les commandes du client connecté
**URL :**

```http
GET http://localhost:8080/api/orders
```
**Headers :**

```makefile
Authorization: Bearer <token_du_client>
```
**Description :**
Retourne la liste des commandes effectuées par l’utilisateur.

3️⃣ Obtenir une commande par ID
**URL :**

```http
GET http://localhost:8080/api/orders/{orderId}
```
**Exemple :**

```http
GET http://localhost:8080/api/orders/1
```
**Headers :**

```makefile
Authorization: Bearer <token_du_client>
```
**Description :**
Retourne les détails de la commande spécifiée, si elle appartient à l’utilisateur.

4️⃣ Supprimer une commande
**URL :**

```http
DELETE http://localhost:8080/api/orders/{orderId}
```
**Exemple :**

```http
DELETE http://localhost:8080/api/orders/3
```
**Headers :****

```makefile
Authorization: Bearer <token_du_client>
```
**Description :**
Supprime la commande si son statut est DRAFT ou PENDING.
Sinon, une erreur sera retournée.

5️⃣ Mettre à jour le statut d’une commande
**URL :**

```http
PUT http://localhost:8080/api/orders/{orderId}/status?status=PENDING
```
**Exemple :**

```http
PUT http://localhost:8080/api/orders/2/status?status=PENDING
```
Headers :

```makefile
Authorization: Bearer <token_du_client>
```
**Description :**
Met à jour le statut d’une commande (DRAFT → PENDING ou PAID), tant qu’elle n’est pas déjà livrée ou annulée.

🔐 Rappel sécurité
Toutes les routes du module commandes sont restreintes au rôle CLIENT grâce à l’annotation @PreAuthorize("hasRole('CLIENT')").


# 💳 Module Paiement

## 🎯 Objectif

Ce module permet d’intégrer un système de paiement sécurisé avec **Stripe Checkout**.
Un utilisateur connecté peut initier un paiement pour une commande existante.
Une URL est générée via l’API Stripe, vers laquelle il est redirigé pour compléter le paiement.

---

### 1️⃣ Intégration Stripe Checkout

## ✅ Étapes de développement

## ✅ Étape 1 : Ajout de la dépendance Stripe dans `pom.xml` et configuration des clés API dans application.properties

**pom.xml**
```xml
<dependency>
  <groupId>com.stripe</groupId>
  <artifactId>stripe-java</artifactId>
  <version>24.10.0</version>
</dependency>
```
**application.properties**
```properties
stripe.api.key=XXXXXXXXXXXXXXXXXXXXXXXX
frontend.url=http://localhost:3000
```

## ✅ Étape 2 : DTO – PaymentRequest
Création du DTO pour initier un paiement à partir de l'ID de commande.

## ✅ Étape 3 : Service & Implémentation
Définition du contrat métier pour créer une session Stripe Checkout.

Implémentation de la logique de création d’une session Stripe Checkout :
- Récupération de la commande
- Vérification du statut et du propriétaire
- Création de la session via Stripe Java SDK
- Retour de l’URL Stripe

### ✅ Étape 4 : Contrôleur `PaymentController`

Création d’un contrôleur REST `PaymentController` pour exposer un endpoint permettant de déclencher le paiement d’une commande via Stripe Checkout.

### 📌 Endpoint créé : `/api/payments/create-checkout-session`

- **Méthode HTTP :** `POST`
- **Accès restreint à :** `CLIENT`
- **Payload attendu :** objet `PaymentRequest` contenant l’ID de la commande à payer
- **Traitement effectué :**
  - Récupération de l'ID de l'utilisateur authentifié via `Authentication`
  - Appel au service `paymentService.createCheckoutSession(orderId, userId)`
  - Retourne une URL Stripe valide à laquelle le frontend peut rediriger l'utilisateur pour procéder au paiement


## 🧪 Test Postman : Paiement d'une commande via Stripe

**Requête Postman**
**Méthode : POST**
**URL :**

```http
http://localhost:8080/api/payments/create-checkout-session
```
**Headers :**

```http
Authorization: Bearer <JWT_TOKEN>
Content-Type: application/json
```
**Body :**

```json
{
  "orderId": 5
}
```
✅ Réponse attendue (succès)
```json
{
  "checkoutUrl": "https://checkout.stripe.com/pay/cs_test_..."
}
```
On doit cliquer su le lien pour ouvrir l’interface de paiement Stripe.

Cela permet à l’utilisateur de payer sa commande en toute sécurité.

❌ Réponse possible (échec)
Si l’orderId ne correspond pas à une commande existante, ou si elle n’appartient pas à l’utilisateur connecté :

```json
{
  "message": "Commande introuvable"
}
```
Ou bien :

```json
{
  "message": "Accès interdit"
}
```

### 2️⃣ Gestion des variables d’environnement : sécurisation des clés sensibles (Stripe, URL frontend, etc.) via .env + EnvConfig

🎯 Objectif
Isoler les informations sensibles et liées à l’environnement (comme les clés d’API Stripe ou l’URL du frontend) dans un fichier .env, afin d’éviter toute fuite accidentelle lors des commits Git.

✅ Étapes réalisées
### ✅ Étape 1 : Création du fichier .env
Contenu du fichier .env à la racine du projet :

```ini
STRIPE_SECRET_KEY=XXXXXXXXXXXXXXXXXXXXXXXX
FRONTEND_URL=http://localhost:3000
```
✅ Ce fichier ne doit jamais être versionné (ajouté dans .gitignore).

### ✅ Étape 2 : Classe EnvConfig.java
Une classe utilitaire a été créée pour lire les variables d’environnement en toute sécurité.

### ✅ Étape 3 : Modification dans le service PaymentServiceImpl
```java
Stripe.apiKey = EnvConfig.get("STRIPE_SECRET_KEY");
String frontendUrl = EnvConfig.get("FRONTEND_URL");
```
Ces lignes remplacent l’ancienne annotation @Value(...), pour éviter toute fuite via application.properties.

🔒 Avantages
✅ Meilleure sécurité (pas de clé exposée dans le code ou dans Git).

✅ Permet de changer de configuration facilement entre environnement local, staging et production.

✅ Compatible avec Stripe, Spring Boot, et les services cloud comme Heroku, Vercel, etc.


### 3️⃣ Webhook Stripe – Paiement asynchrone

## 🎯 Introduction

Lorsqu’un utilisateur paie via Stripe, le paiement est effectué **de manière asynchrone**. Cela signifie que le backend n’est pas immédiatement notifié du succès du paiement.  
Pour résoudre cela, Stripe envoie des **webhooks** (notifications HTTP POST) à un endpoint défini, contenant les détails de l’événement (paiement réussi, échec, remboursement…).

👉 Dans ShopEase, nous utilisons ce mécanisme pour :
- **Mettre à jour automatiquement le statut d’une commande** lorsque Stripe confirme que le paiement est effectué (`checkout.session.completed`).

---

# 💳 Étape 1 : Configuration Stripe CLI
## 🛠️ Étapes pour installer Stripe CLI sous Linux

### ✅ 1. Télécharger l’archive depuis GitHub

Va sur la page des releases Stripe CLI GitHub :  
👉 https://github.com/stripe/stripe-cli/releases

Repère la dernière version stable (ex. `stripe_1.27.0_linux_x86_64.tar.gz`).

Ensuite, télécharge le fichier via `wget` :

```bash
wget https://github.com/stripe/stripe-cli/releases/download/v1.27.0/stripe_1.27.0_linux_x86_64.tar.gz
```
💡 Remplace le lien par la dernière version si nécessaire.

### ✅ 2. Décompresser l’archive
```bash
tar -xvf stripe_1.27.0_linux_x86_64.tar.gz
```
Cela va créer un dossier avec un exécutable appelé stripe.

### ✅ 3. Déplacer le binaire Stripe dans un dossier du PATH
```bash
sudo mv stripe /usr/local/bin/
```
### ✅ 4. Vérifier que Stripe CLI est bien installé
```bash
stripe version
```
On devrait voir quelque chose comme :

```bash
stripe version 1.27.0
```
### 🔐 Configuration du webhook Stripe
✅ Étapes pour obtenir STRIPE_WEBHOOK_SECRET
1. Connecter Stripe CLI à ton compte Stripe
```bash
stripe login
```
Cela ouvre une page web pour autoriser la connexion.

2. Lancer l’écoute du webhook en local
Dans le terminal du projet Spring Boot :

```bash
stripe listen --forward-to localhost:8080/api/payments/webhook
```
✅ Une fois cette commande exécutée, on verra une sortie comme :

```bash
Ready! Your webhook signing secret is XXXXXXXXXXXXXXXXXXXXXXXX
```
👉 Il faut Copier cette clé whsec_... et colle-la dans le fichier .env :

```ini
STRIPE_WEBHOOK_SECRET=XXXXXXXXXXXXXXXXXXXXXXXX
```

# 💳 Étape 2 : Service de gestion du webhook Stripe

## 🎯 Objectif

Permettre à l’application ShopEase de réagir à l’événement envoyé par Stripe lorsque le paiement est réussi (`checkout.session.completed`).  
Cela permet de **mettre à jour automatiquement le statut de la commande** concernée en `PAID` sans intervention manuelle.

---

## 🧩 Interface `PaymentService`

Une nouvelle fonction a été introduite dans `PaymentService` :

```java
    void handleCheckoutSession(Session session);
```
### 📌 Rôle :
Fournit un contrat pour toute logique métier liée aux événements Stripe (notamment checkout.session.completed).

Peut être étendue à d'autres services (PayPal, etc.) à l'avenir.

## 🛠️ Implémentation – PaymentServiceImpl
La méthode handleCheckoutSession(Session session) effectue les étapes suivantes :

✅ Récupération de l'orderId à partir du champ client_reference_id de la session Stripe.

- Recherche de la commande dans la base de données.
- Vérification du statut de la commande.
- Mise à jour de son statut vers PAID si elle ne l’est pas déjà.
- Enregistre également la date de paiement (orderDate).

⚠️ Remarque :
- Le champ client_reference_id est utilisé pour associer la session Stripe à une commande spécifique.
- Stripe envoie l’objet Session via le webhook /api/payments/webhook, qui est traité dans le contrôleur.

# 💳 Étape 3 : Contrôleur `PaymentController` – Endpoint Stripe Webhook

## 🎯 Objectif

Mettre en place un **endpoint sécurisé** qui reçoit les événements Stripe **lorsqu’un paiement est terminé avec succès**.  
Ce webhook permet de mettre à jour le statut d’une commande dans la base de données **sans interaction utilisateur**.

## 🛠️ Endpoint `/api/payments/webhook`

Un nouveau point d'entrée HTTP POST a été ajouté dans `PaymentController` :

```java
@PostMapping("/webhook")
public ResponseEntity<String> handleStripeWebhook(HttpServletRequest request)
```
## 📌 Fonctionnement de ce webhook :
- Lecture manuelle du corps brut de la requête Stripe (payload).
- Récupération de l’en-tête Stripe-Signature pour vérifier que la requête provient bien de Stripe.
- Vérification cryptographique via Webhook.constructEvent(...) à l’aide du secret STRIPE_WEBHOOK_SECRET (défini dans .env).
- Traitement de l’événement uniquement si le type est checkout.session.completed.
- Désérialisation de la session Stripe (Session) et appel de la méthode métier :

```java
paymentService.handleCheckoutSession(session);
```
# => Réponse HTTP 200 OK si tout s’est bien passé, sinon 400 Bad Request en cas d’erreur.

# 🔐 Sécurité – Ouverture du webhook Stripe dans `SecurityConfig`

## 🎯 Objectif

Configurer Spring Security pour **autoriser les requêtes Stripe** vers le webhook `/api/payments/webhook` **sans authentification**.  
Stripe n'envoie pas de jeton JWT — si la route n’est pas ouverte, les événements de paiement ne seront **jamais reçus** par ton backend.

### ❓ Pourquoi cette modification est-elle cruciale ?
✅ Stripe ne peut pas s’authentifier (pas de JWT dans ses requêtes webhook).

❌ Sans cette règle, Spring Security bloquera les requêtes Stripe avec une erreur 401 Unauthorized.

✅ En ajoutant "/api/payments/webhook" dans les permitAll(), on permet à Stripe d’envoyer des événements librement, mais uniquement sur cette route spécifique.

### 🛡️ Est-ce sécurisé ?
Oui, car :

✅ L’accès libre est limité uniquement à /api/payments/webhook.

✅ Le contenu de la requête est vérifié cryptographiquement avec Stripe-Signature et le secret STRIPE_WEBHOOK_SECRET.

✅ Toute requête non authentifiée qui ne provient pas de Stripe sera automatiquement rejetée

# 🧪 Tests – Module Paiement via Stripe CLI

Pour valider la réception des événements Stripe webhook dans notre backend, il est essentiel de tester avec un outil fiable qui simule correctement les signatures cryptographiques attendues. Le test Postman ne fonctionne pas car il est impossible de reproduire la signature Stripe (`Stripe-Signature`) manuellement.

## Test avec Stripe CLI (recommandé)

### 📦 Prérequis

- Stripe CLI installé et configuré (voir README précédent)
- Webhook configuré dans Stripe Dashboard OU utilisation de la commande `stripe listen`

### 🔧 Étapes

1. **Connexion à Stripe CLI (si ce n’est pas déjà fait) :**

```bash
stripe login
```
2. **Lancer l’écoute du webhook en local :**

```bash
stripe listen --forward-to localhost:8080/api/payments/webhook
```
Cette commande connecte Stripe au serveur local et affiche le secret de signature utilisé.

3. **Simuler un paiement complété :**

```bash
stripe trigger checkout.session.completed
```
✅ Résultat attendu
Stripe envoie un événement checkout.session.completed vers le backend.

Le backend affiche en console un message du type :
✅ Paiement reçu pour commande ID: ...

La commande correspondante dans la base passe au statut PAID.

⚠️ Pourquoi ne pas utiliser Postman ?
- Les webhooks Stripe doivent être signés avec une clé secrète (Stripe-Signature).
- Cette signature est calculée dynamiquement et ne peut pas être reproduite manuellement dans Postman.
- Toute requête non signée correctement sera rejetée avec une erreur 400 Signature Stripe invalide.

# =>Pour cette raison, le test Postman n’est pas fiable pour tester les webhooks Stripe.


### 4️⃣ Génération de factures PDF – Module Paiement
## 🎯 Objectif
Permettre aux utilisateurs de télécharger une facture PDF après avoir effectué un paiement réussi via Stripe.
La facture contient les détails de la commande : produits achetés, quantités, prix unitaire, montant total, informations client et date.

## 🧰 Librairie utilisée
Pour générer le PDF côté serveur, nous utilisons OpenPDF, une bibliothèque Java open-source (sous licence LGPL/MPL) dérivée de iText 2.1.7.

## ✅ 📄 Étape 1 – Intégration de OpenPDF & création de PdfInvoiceGenerator
📌 1. Ajout de la dépendance Maven
Dans le fichier pom.xml, ajoute la dépendance suivante :

```xml
<dependency>
    <groupId>com.github.librepdf</groupId>
    <artifactId>openpdf</artifactId>
    <version>1.3.30</version>
</dependency>
```
📌 2. Création de la classe PdfInvoiceGenerator
📁 Emplacement recommandé :
src/main/java/com/shopease/util/PdfInvoiceGenerator.java

Cette classe est responsable de :

Générer dynamiquement un fichier PDF à partir d’un objet Order

Structurer le PDF avec :

✅ En-tête de facture

✅ Informations sur le client

✅ Tableau des produits (nom, quantité, prix unitaire, total)

✅ Montant total de la commande

Retourner un tableau de bytes (byte[]) que l'on pourra renvoyer dans une réponse HTTP

✏️ Exemple de contenu de la facture :
✏️ Exemple de contenu de la facture :
```yaml
📄 ShopEase - Facture

Numéro de commande : 1024
Client : Ali Benzarti
Date : 2025-06-24 14:38
Statut : PAID

| Produit      | Quantité | Prix unitaire | Total   |
|--------------|----------|----------------|---------|
| Chaussures X |    2     |    75.00 $     | 150.00 $
| Sac à main Y |    1     |   120.00 $     | 120.00 $

💰 Montant total : 270.00 $
```


