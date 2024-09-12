package entities;

public class Category {
    // Define constants for categories
    public static final Category ELECTRONICS = new Category("Electronics");
    public static final Category FOOD = new Category("Food");
    public static final Category BOOKS = new Category("Books");

    // Instance variable to store the name of the category
    private final String name;

    // Private constructor to initialize the category name
    private Category(String name) {
        this.name = name;
    }

    // Getter for the category name
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
