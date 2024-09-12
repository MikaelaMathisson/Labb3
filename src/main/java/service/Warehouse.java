package service;

import entities.Product;
import entities.Category;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Warehouse {
    private final List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        if (product.name() == null || product.name().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }
        products.add(product);
    }

    public void updateProduct(String id, String name, Category category, int quantity) {
        Product product = getProductById(id);
        if (product != null) {
            Product updatedProduct = product.withName(name)
                    .withCategory(category)
                    .withQuantity(quantity);
            // Remove old product and add updated product
            products.remove(product);
            products.add(updatedProduct);
        }
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(products);
    }

    public Product getProductById(String id) {
        return products.stream()
                .filter(product -> product.id().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Product> getProductsByCategory(Category category) {
        return products.stream()
                .filter(p -> p.category().equals(category))
                .sorted((p1, p2) -> p1.name().compareTo(p2.name()))
                .collect(Collectors.toList());
    }

    public List<Product> getProductsCreatedAfter(LocalDate date) {
        return products.stream()
                .filter(p -> p.createdDate().isAfter(date))
                .collect(Collectors.toList());
    }

    public List<Product> getProductsModifiedAfter(LocalDate date) {
        return products.stream()
                .filter(p -> p.modifiedDate().isAfter(date))
                .collect(Collectors.toList());
    }
}
