package com.shopease.backend.config;

/**
 * Classe utilitaire pour accéder aux variables d'environnement définies dans un fichier `.env`.
 *
 * Cette classe utilise la bibliothèque dotenv-java pour charger automatiquement les variables
 * depuis un fichier `.env` à la racine du projet. Elle fournit une méthode statique pour
 * récupérer la valeur associée à une clé donnée.
 *
 * Exemple d'utilisation :
 * String dbUrl = EnvConfig.get("DATABASE_URL");
 *
 * @author Fadhel Smari
 */

import io.github.cdimascio.dotenv.Dotenv;

public class EnvConfig {
    private static final Dotenv dotenv = Dotenv.configure().load();

    /**
     * Récupère la valeur d'une variable d'environnement à partir de sa clé.
     *
     * @param key le nom de la variable à récupérer (par exemple "DATABASE_URL")
     * @return la valeur associée à cette clé, ou null si la variable n'existe pas
     */
    public static String get(String key) {
        // Retourne la valeur de la clé demandée à partir des variables chargées
        return dotenv.get(key);
    }
}
