package entities;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record Product(
        @NotBlank String id,
        @NotBlank String name,
        @NotNull Category category,
        @Min(0) int quantity,
        @NotNull LocalDate createdDate,
        @NotNull LocalDate modifiedDate) {

    // Constructor will be automatically generated with validation annotations

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Product product = (Product) obj;
        return id.equals(product.id());
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
