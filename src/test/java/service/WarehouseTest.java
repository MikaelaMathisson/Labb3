package service;

import entities.Category;
import entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class WarehouseTest {
    private Warehouse warehouse;

    @BeforeEach
    public void setUp() {
        warehouse = new Warehouse();
    }

    @Test
    public void testAddProduct() {
        Product product = new Product("1", "Product 1", Category.ELECTRONICS, 5, LocalDate.now(), LocalDate.now());
        warehouse.addProduct(product);
        assertEquals(1, warehouse.getAllProducts().size());
    }

//Test för att se om det går att lägga till en produkt med null värde.
    /*
    @Test
    public void testAddProductWithNull() {
        Warehouse warehouse = new Warehouse();
        Product nullNameProduct = new Product("2", null, Category.FOOD, 7, LocalDate.now(), LocalDate.now());
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            warehouse.addProduct(nullNameProduct);
        });
        assertEquals("Fields cannot be null" , thrown.getMessage());
    }*/

    @Test
    public void testUpdateProduct() {
        Product product = new Product("3", "Product 3", Category.BOOKS, 8, LocalDate.now(), LocalDate.now());
        warehouse.addProduct(product);
        warehouse.updateProduct("3", "NewName", Category.ELECTRONICS, 9);
        Product updatedProduct = warehouse.getProductById("3");
        assertEquals("NewName", updatedProduct.name());
        assertEquals(Category.ELECTRONICS, updatedProduct.category());
        assertEquals(9, updatedProduct.quantity());
    }

    @Test
    public void testGetProductById() {
        Product product = new Product("4", "Product 4", Category.FOOD, 6, LocalDate.now(), LocalDate.now());
        warehouse.addProduct(product);
        Product foundProduct = warehouse.getProductById("4");
        assertEquals(product, foundProduct);
    }

    @Test
    public void testGetProductByIdNotFound() {
        assertNull(warehouse.getProductById("None existent id"));
    }

    @Test
    public void testGetProductsCreatedAfter() {
        Product product1 = new Product("5", "Product 5", Category.FOOD, 6, LocalDate.now(), LocalDate.now());
        Product product2 = new Product("6", "Product 6", Category.FOOD, 6, LocalDate.now().minusDays(1), LocalDate.now());
        warehouse.addProduct(product1);
        warehouse.addProduct(product2);
        assertEquals(1, warehouse.getProductsCreatedAfter(LocalDate.now().minusDays(1)).size());
    }

    @Test
    public void testGetProductsModifiedAfter() {
        Product product = new Product("7", "Product 7", Category.FOOD, 6, LocalDate.now().minusDays(1), LocalDate.now());
        warehouse.addProduct(product);
        assertEquals(1, warehouse.getProductsModifiedAfter(LocalDate.now().minusDays(1)).size());
    }
}
