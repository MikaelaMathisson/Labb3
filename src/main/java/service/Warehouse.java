package service;

import entities.Product;
import entities.Category;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class Warehouse {
    private final List<Product> products = new CopyOnWriteArrayList<>();
    private final Lock lock = new ReentrantLock();

    public void addProduct(Product product) {
        lock.lock();
        try {
            if (product == null) {
                throw new IllegalArgumentException("Product cannot be null");
            }
            if (product.name() == null || product.name().isEmpty()) {
                throw new IllegalArgumentException("Product name cannot be empty");
            }
            products.add(product);
        } finally {
            lock.unlock();
        }
    }

    public Product updateProduct(String id, String name, Category category, int quantity) {
        lock.lock();
        try {
            Product product = getProductById(id);
            if (product == null) {
                throw new IllegalArgumentException("Product not found");
            }

            // Create a new product with updated values
            Product updatedProduct = product.withName(name)
                    .withCategory(category)
                    .withQuantity(quantity);

            // Replace the old product with the updated product
            int index = products.indexOf(product);
            if (index >= 0) {
                products.set(index, updatedProduct);
            }

            return updatedProduct; // Return the updated product
        } finally {
            lock.unlock();
        }
    }

    public List<Product> getAllProducts() {
        lock.lock();
        try {
            return List.copyOf(products); // Returns an unmodifiable copy of the list
        } finally {
            lock.unlock();
        }
    }

    public Product getProductById(String id) {
        lock.lock();
        try {
            return products.stream()
                    .filter(product -> product.id().equals(id))
                    .findFirst()
                    .orElse(null);
        } finally {
            lock.unlock();
        }
    }

    public List<Product> getProductsByCategory(Category category) {
        lock.lock();
        try {
            return products.stream()
                    .filter(p -> p.category().equals(category))
                    .sorted((p1, p2) -> p1.name().compareTo(p2.name()))
                    .collect(Collectors.toList());
        } finally {
            lock.unlock();
        }
    }

    public List<Product> getProductsCreatedAfter(LocalDate date) {
        lock.lock();
        try {
            return products.stream()
                    .filter(p -> p.createdDate().isAfter(date))
                    .collect(Collectors.toList());
        } finally {
            lock.unlock();
        }
    }

    public List<Product> getProductsModifiedAfter(LocalDate date) {
        lock.lock();
        try {
            return products.stream()
                    .filter(p -> p.modifiedDate().isAfter(date))
                    .collect(Collectors.toList());
        } finally {
            lock.unlock();
        }
    }
}
