package api;

import entities.Product;
import entities.Category;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import service.Warehouse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@Path("/warehouse")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class WarehouseResource {

    private static final Logger logger = LoggerFactory.getLogger(WarehouseResource.class);

    @Inject
    private Warehouse warehouse;

    @POST
    @Path("/products")
    public Response addProduct(@Valid @NotNull Product product) {
        logger.info("Adding product: {}", product);
        try {
            warehouse.addProduct(product);
            logger.info("Product added successfully: {}", product);
            return Response.status(Response.Status.CREATED).entity(product).build();
        } catch (IllegalArgumentException e) {
            logger.error("Error adding product: {}", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse("BAD_REQUEST", e.getMessage())).build();
        }
    }

    @GET
    @Path("/products")
    public Response getAllProducts(@QueryParam("page") int page, @QueryParam("size") int size) {
        logger.info("Fetching all products");
        List<Product> products = warehouse.getAllProducts();
        logger.info("Fetched {} products", products.size());
        return Response.ok(products).build();
    }

    @GET
    @Path("/products/{id}")
    public Response getProductById(@PathParam("id") String id) {
        logger.info("Fetching product with id: {}", id);
        Product product = warehouse.getProductById(id);
        if (product != null) {
            logger.info("Product found: {}", product);
            return Response.ok(product).build();
        } else {
            logger.warn("Product not found with id: {}", id);
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse("NOT_FOUND", "Product not found")).build();
        }
    }

    @GET
    @Path("/products/category/{categoryName}")
    public Response getProductsByCategory(@PathParam("categoryName") String categoryName) {
        logger.info("Fetching products by category: {}", categoryName);
        try {
            Category category = Category.fromString(categoryName);
            List<Product> products = warehouse.getProductsByCategory(category);
            logger.info("Fetched {} products in category {}", products.size(), categoryName);
            return Response.ok(products).build();
        } catch (IllegalArgumentException e) {
            logger.error("Error fetching products by category: {}", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse("BAD_REQUEST", e.getMessage())).build();
        }
    }

    @GET
    @Path("/products/createdAfter/{date}")
    public Response getProductsCreatedAfter(@PathParam("date") String dateStr) {
        logger.info("Fetching products created after: {}", dateStr);
        try {
            LocalDate date = LocalDate.parse(dateStr);
            List<Product> products = warehouse.getProductsCreatedAfter(date);
            logger.info("Fetched {} products created after {}", products.size(), dateStr);
            return Response.ok(products).build();
        } catch (DateTimeParseException e) {
            logger.error("Error parsing date: {}", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse("BAD_REQUEST", "Invalid date format")).build();
        }
    }

    @GET
    @Path("/products/modifiedAfter/{date}")
    public Response getProductsModifiedAfter(@PathParam("date") String dateStr) {
        logger.info("Fetching products modified after: {}", dateStr);
        try {
            LocalDate date = LocalDate.parse(dateStr);
            List<Product> products = warehouse.getProductsModifiedAfter(date);
            logger.info("Fetched {} products modified after {}", products.size(), dateStr);
            return Response.ok(products).build();
        } catch (DateTimeParseException e) {
            logger.error("Error parsing date: {}", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse("BAD_REQUEST", "Invalid date format")).build();
        }
    }

    @PUT
    @Path("/products/{id}")
    public Response updateProduct(@PathParam("id") String id, @Valid @NotNull Product product) {
        logger.info("Updating product with id: {}", id);
        try {
            Product updatedProduct = warehouse.updateProduct(id, product.name(), product.category(), product.quantity());
            logger.info("Product updated successfully: {}", updatedProduct);
            return Response.ok(updatedProduct).build();
        } catch (IllegalArgumentException e) {
            logger.error("Error updating product: {}", e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse("BAD_REQUEST", e.getMessage())).build();
        } catch (NotFoundException e) {
            logger.warn("Product not found with id: {}", id);
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse("NOT_FOUND", "Product not found")).build();
        }
    }

    // Error response class
    public static class ErrorResponse {
        private String code;
        private String message;

        public ErrorResponse(String code, String message) {
            this.code = code;
            this.message = message;
        }

        // Getters and setters
    }
}