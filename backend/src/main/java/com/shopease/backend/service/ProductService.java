package com.shopease.backend.service;

/**
 * Service pour gérer les opérations liées aux produits :
 * récupération, ajout, suppression et recherche avec filtres dynamiques.
 *
 * @author Fadhel Smari
 */

import com.shopease.backend.dto.ProductFilterRequest;
import com.shopease.backend.dto.ProductResponse;
import com.shopease.backend.entity.Product;
import com.shopease.backend.exception.ResourceNotFoundException;
import com.shopease.backend.repository.ProductRepository;
import com.shopease.backend.specification.ProductSpecification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Récupère tous les produits disponibles et les transforme en réponse DTO.
     *
     * @return une liste d'objets ProductResponse
     */
    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(this::mapToProductResponse)
                .collect(Collectors.toList());
    }

    /**
     * Récupère un produit par son identifiant.
     *
     * @param id l'identifiant du produit
     * @return un objet ProductResponse correspondant
     * @throws ResourceNotFoundException si le produit n'est pas trouvé
     */
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produit non trouvé avec id : " + id));
        return mapToProductResponse(product);
    }

    /**
     * Sauvegarde un nouveau produit ou met à jour un produit existant.
     *
     * @param product le produit à sauvegarder
     * @return le produit enregistré
     */
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    /**
     * Supprime un produit à partir de son identifiant.
     *
     * @param id l'identifiant du produit à supprimer
     */
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    /**
     * Recherche des produits en fonction de critères dynamiques (nom, catégorie, prix).
     *
     * @param filterRequest les critères de filtre
     * @return une liste de produits filtrés sous forme de ProductResponse
     */
    public List<ProductResponse> searchProducts(ProductFilterRequest filterRequest) {
        // Construction dynamique de la spécification avec les filtres
        var spec = ProductSpecification.hasName(filterRequest.getName())
                .and(ProductSpecification.hasCategory(filterRequest.getCategoryId()))
                .and(ProductSpecification.hasPriceBetween(filterRequest.getMinPrice(), filterRequest.getMaxPrice()));

        // Récupération des produits correspondant à la spécification
        List<Product> products = productRepository.findAll(spec);

        // Conversion des entités en DTOs
        return products.stream()
                .map(this::mapToProductResponse)
                .collect(Collectors.toList());
    }

    /**
     * Méthode utilitaire pour convertir un objet Product en ProductResponse.
     *
     * @param product l'entité produit à convertir
     * @return le DTO ProductResponse
     */
    private ProductResponse mapToProductResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getImageUrl(),
                product.getStock(),
                product.getCategory() != null ? product.getCategory().getName() : null
        );
    }
}
