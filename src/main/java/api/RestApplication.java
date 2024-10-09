package api;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api") // Set the base URI for your REST API
public class RestApplication extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(WarehouseResource.class); // Add your resource classes
        classes.add(GenericExceptionMapper.class); // Register the exception mapper
        return classes;
    }
}