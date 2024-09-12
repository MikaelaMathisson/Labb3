package entities;

import java.time.LocalDate;

public record Product(String id, String name, Category category, int quantity, LocalDate createdDate, LocalDate modifiedDate) {

    public Product {
        if (id == null || name == null || createdDate == null || modifiedDate == null) {
            throw new IllegalArgumentException("Fields cannot be null");
        }
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }
    }

    // Methods for creating modified copies
    public Product withName(String name) {
        return new Product(id, name, category, quantity, createdDate, LocalDate.now());
    }

    public Product withCategory(Category category) {
        return new Product(id, name, category, quantity, createdDate, LocalDate.now());
    }

    public Product withQuantity(int quantity) {
        return new Product(id, name, category, quantity, createdDate, LocalDate.now());
    }
}
