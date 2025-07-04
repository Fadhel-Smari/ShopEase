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
