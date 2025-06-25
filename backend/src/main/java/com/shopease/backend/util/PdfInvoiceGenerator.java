package com.shopease.backend.util;

/**
 * Génère une facture PDF à partir d'une commande.
 *
 * Cette classe utilise la bibliothèque OpenPDF pour construire dynamiquement
 * un document PDF contenant les détails de la commande, les articles achetés,
 * et le montant total.
 *
 * Elle est annotée avec {@code @Component} pour être injectée automatiquement
 * dans les services Spring.
 *
 * @author Fadhel Smari
 */

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.shopease.backend.entity.Order;
import com.shopease.backend.entity.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class PdfInvoiceGenerator {

    /**
     * Génère un fichier PDF représentant la facture associée à une commande donnée.
     *
     * @param order la commande à transformer en facture
     * @return un tableau de bytes représentant le fichier PDF
     */
    public byte[] generateInvoicePdf(Order order) {
        try {
            // Flux de sortie pour écrire les données PDF en mémoire
            ByteArrayOutputStream out = new ByteArrayOutputStream();

            // Création d'un document PDF vide
            Document document = new Document();

            // Association du document au writer PDF qui écrit dans le flux mémoire
            PdfWriter.getInstance(document, out);

            // Ouverture du document pour écriture
            document.open();

            // Ajout d'un titre au document
            document.add(new Paragraph("📄 ShopEase - Facture", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18)));

            // Ligne vide
            document.add(new Paragraph(" "));

            // Ajout des informations générales sur la commande
            document.add(new Paragraph("Numéro de commande : " + order.getId()));
            document.add(new Paragraph("Client : " + order.getUser().getFirstname() + " " + order.getUser().getLastname()));
            document.add(new Paragraph("Date : " + order.getOrderDate()));
            document.add(new Paragraph("Statut : " + order.getStatus()));
            document.add(new Paragraph(" ")); // Espace

            // Création du tableau pour les détails des produits commandés (4 colonnes)
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100); // Le tableau occupe toute la largeur du document
            table.setWidths(new float[]{4, 2, 2, 2}); // Largeur relative des colonnes

            // Ajout des en-têtes de colonnes
            addTableHeader(table, "Produit", "Quantité", "Prix unitaire", "Total");

            // Remplissage du tableau avec les articles de la commande
            for (OrderItem item : order.getItems()) {
                // Nom du produit
                table.addCell(item.getProduct().getName());

                // Quantité achetée
                table.addCell(String.valueOf(item.getQuantity()));

                // Prix unitaire avec le symbole $
                table.addCell(item.getProduct().getPrice() + " $");

                // Calcul du total pour l'article (prix * quantité)
                BigDecimal totalItem = item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
                table.addCell(totalItem + " $");
            }

            // Ajout du tableau au document
            document.add(table);

            // Ligne vide
            document.add(new Paragraph(" "));

            // Ajout du total de la commande, mis en gras
            document.add(new Paragraph("💰 Montant total : " + order.getTotalAmount() + " $", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));

            // Fermeture du document
            document.close();

            // Retourne le contenu PDF en tant que tableau de bytes
            return out.toByteArray();
        } catch (Exception e) {
            // Gestion des erreurs lors de la génération
            throw new RuntimeException("Erreur lors de la génération de la facture PDF", e);
        }
    }

    /**
     * Ajoute les en-têtes de colonne à un tableau PDF.
     *
     * @param table le tableau PDF à compléter
     * @param headers les intitulés des colonnes
     */
    private void addTableHeader(PdfPTable table, String... headers) {
        for (String h : headers) {
            // Création de la cellule d'en-tête
            PdfPCell header = new PdfPCell();

            // Définition du texte et de la police
            header.setPhrase(new Phrase(h, FontFactory.getFont(FontFactory.HELVETICA_BOLD)));

            // Ajout de la cellule au tableau
            table.addCell(header);
        }
    }
}

