# Chatop – Backend Java Spring Boot

Ce projet est une API REST de gestion de locations immobilières, développée avec **Spring Boot**. Elle propose une authentification sécurisée par **JWT**, la gestion des utilisateurs, des locations, des messages, et le support de l’upload de fichiers (images). L’API est consommée par un frontend **Angular**.

## Fonctionnalités principales

- Authentification et inscription des utilisateurs (JWT)
- Création, modification, consultation de locations
- Upload d’images pour les annonces (multipart)
- Gestion des messages entre utilisateurs
- Documentation OpenAPI/Swagger intégrée

## Prérequis

- Javav version 21
- Spring version 4.1.3
- Angular CLI version 14.1.3
- MySQL
- Maven

## Installation

1. **Cloner le dépôt :**

   ```sh
   git clone <repo-url>
   cd chatop
   ```

2. **Créer la base de données MySQL :**

   Dans un terminal, connecte-toi à MySQL puis exécute :

   ```sh
   mysql -u root -p
   ```

   Puis dans le prompt MySQL :

   ```sql
   CREATE DATABASE location_app;
   EXIT;
   ```

3. **Configurer la base de données :**

   - Modifie les accès dans [`src/main/resources/application.properties`](src/main/resources/application.properties) :
     ```
     spring.datasource.url=jdbc:mysql://localhost:3306/location_app
     spring.datasource.username=root
     spring.datasource.password=
     ```
   - Vérifie les autres paramètres nécessaires dans ce fichier (clé secrète JWT, taille des fichiers, etc).

4. **Construire et lancer l’application :**
   ```sh
   ./mvnw spring-boot:run
   ```

## Utilisation

- L’API est accessible sur : `http://localhost:8080`
- Le serveur écoute par défaut sur le port **8080** (modifiable dans `application.properties` via `server.port`)
- La documentation Swagger : `http://localhost:8080/swagger-ui.html`
- Les fichiers uploadés sont servis via `/uploads/{filename}`

## Tests des routes

- Les routes de l’API ont été testées avec **Postman** (importez la collection ou testez manuellement les endpoints).
- Vous pouvez également utiliser Swagger pour tester les endpoints directement dans le navigateur.

## Authentification

- Les endpoints `/api/auth/register` et `/api/auth/login` sont publics.
- Les autres endpoints nécessitent un JWT dans l’en-tête :
  ```
  Authorization: Bearer <token>
  ```

## Endpoints principaux

| Méthode | URL                  | Description                       | Authentification |
| ------- | -------------------- | --------------------------------- | ---------------- |
| POST    | `/api/auth/register` | Inscription utilisateur           | Non              |
| POST    | `/api/auth/login`    | Connexion utilisateur (JWT)       | Non              |
| GET     | `/api/auth/me`       | Infos utilisateur connecté        | Oui              |
| GET     | `/api/rentals`       | Liste des locations               | Oui              |
| GET     | `/api/rentals/{id}`  | Détail d’une location             | Oui              |
| POST    | `/api/rentals`       | Créer une location (multipart)    | Oui              |
| PUT     | `/api/rentals/{id}`  | Modifier une location (multipart) | Oui              |
| POST    | `/api/messages`      | Envoyer un message                | Oui              |

## Configuration

Voir [`src/main/resources/application.properties`](src/main/resources/application.properties) pour :

- Connexion MySQL
- Clé secrète JWT (`app.secret.key`)
- Durée de validité du token (`app.expiration-time`)
- Limites d’upload de fichiers
- Chemin d’accès aux fichiers uploadés (`uploads.path`)
- Port d’écoute du serveur (`server.port`, par défaut 8080)

## Frontend

Le frontend Angular consomme cette API (voir documentation du frontend pour plus de détails).

---
