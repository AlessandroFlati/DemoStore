package demo.store.repository;

import demo.store.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // This interface extends JpaRepository which provides all the CRUD operations for the Product entity
}
