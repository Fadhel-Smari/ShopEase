package com.shopease.backend.controller;

/**
 * Contrôleur REST pour la gestion des produits.
 *
 * Ce contrôleur permet de :
 * - lister tous les produits,
 * - obtenir un produit par son ID,
 * - créer, modifier ou supprimer un produit,
 * - effectuer une recherche filtrée sur les produits.
 *
 * Les routes exposées commencent par /api/products.
 *
 * @author Fadhel Smari
 */

import com.shopease.backend.dto.ProductFilterRequest;
import com.shopease.backend.dto.ProductResponse;
import com.shopease.backend.entity.Product;
import com.shopease.backend.service.ProductService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Retourne la liste de tous les produits.
     *
     * Méthode appelée par une requête GET sur /api/products.
     *
     * @return liste des produits au format ProductResponse
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }

    /**
     * Retourne un produit spécifique à partir de son identifiant.
     *
     * @param id identifiant du produit
     * @return le produit trouvé au format ProductResponse
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public ProductResponse getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    /**
     * Crée un nouveau produit à partir des données fournies dans la requête.
     *
     * @param product l’objet produit à sauvegarder
     * @return le produit enregistré
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Product createProduct(@RequestBody Product product) {
        return productService.saveProduct(product);
    }

    /**
     * Met à jour un produit existant à partir de son identifiant.
     *
     * @param id identifiant du produit à modifier
     * @param product nouvelles données du produit
     * @return le produit mis à jour
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {
        product.setId(id);
        return productService.saveProduct(product);
    }

    /**
     * Supprime un produit par son identifiant.
     *
     * @param id identifiant du produit à supprimer
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

    /**
     * Recherche des produits à l’aide de critères dynamiques (catégorie, prix, nom, etc.).
     *
     * @param filterRequest les critères de recherche
     * @return liste des produits correspondant aux filtres
     */
    @PostMapping("/search")
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public List<ProductResponse> searchProducts(@RequestBody ProductFilterRequest filterRequest) {
        return productService.searchProducts(filterRequest);
    }
}
