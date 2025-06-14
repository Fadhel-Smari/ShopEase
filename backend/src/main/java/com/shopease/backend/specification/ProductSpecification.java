package com.shopease.backend.specification;

/**
 * Fournit des spécifications dynamiques pour filtrer les produits dans les requêtes JPA.
 *
 * Utilisé notamment pour les filtres dynamiques dans les requêtes avec Spring Data JPA.
 * Chaque méthode retourne une spécification basée sur un critère (nom, catégorie, prix, stock).
 *
 * @author Fadhel Smari
 */

import com.shopease.backend.entity.Product;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class ProductSpecification {

    /**
     * Retourne une spécification qui filtre les produits par nom (recherche partielle, insensible à la casse).
     *
     * @param name le nom à rechercher (peut être une partie du nom)
     * @return la spécification correspondante ou null si le nom est vide ou null
     */
    public static Specification<Product> hasName(String name) {
        return (root, query, criteriaBuilder) -> {
            // Si le nom est vide ou null, aucun filtre n'est appliqué
            if (name == null || name.isBlank()) return null;

            // Effectue une recherche "like" insensible à la casse
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("name")),  // Convertit le nom du produit en minuscule
                    "%" + name.toLowerCase() + "%"            // Recherche contenant le texte saisi
            );
        };
    }

    /**
     * Retourne une spécification qui filtre les produits par catégorie.
     *
     * @param categoryId l'ID de la catégorie à filtrer
     * @return la spécification correspondante ou null si l'ID est null
     */
    public static Specification<Product> hasCategory(Long categoryId) {
        return (root, query, criteriaBuilder) -> {
            if (categoryId == null) return null;
            // Filtre les produits appartenant à la catégorie spécifiée
            return criteriaBuilder.equal(root.get("category").get("id"), categoryId);
        };
    }

    /**
     * Retourne une spécification qui filtre les produits selon une plage de prix.
     *
     * @param min le prix minimum (peut être null)
     * @param max le prix maximum (peut être null)
     * @return la spécification correspondant à la plage, ou null si min et max sont null
     */
    public static Specification<Product> hasPriceBetween(BigDecimal min, BigDecimal max) {
        return (root, query, criteriaBuilder) -> {
            // Si min et max sont définis : filtre entre les deux
            if (min != null && max != null) {
                return criteriaBuilder.between(root.get("price"), min, max);
            }
            // Si seulement min est défini : filtre à partir de min
            else if (min != null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("price"), min);
            }
            // Si seulement max est défini : filtre jusqu'à max
            else if (max != null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("price"), max);
            }
            // Aucun filtre si min et max sont null
            else {
                return null;
            }
        };
    }

    /**
     * Retourne une spécification qui filtre les produits selon leur disponibilité en stock.
     *
     * @param inStock true pour les produits en stock, false pour ceux en rupture
     * @return la spécification correspondante ou null si inStock est null
     */
    public static Specification<Product> hasStock(Boolean inStock) {
        return (root, query, criteriaBuilder) -> {
            // Aucun filtre si l'information de stock n'est pas précisée
            if (inStock == null) return null;

            // Filtre selon la disponibilité du stock
            return inStock
                    ? criteriaBuilder.greaterThan(root.get("stock"), 0) // Produits avec stock > 0
                    : criteriaBuilder.equal(root.get("stock"), 0);      // Produits en rupture
        };
    }
}
