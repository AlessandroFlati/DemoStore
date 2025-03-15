package demo.store.service;

import demo.store.exception.ResourceNotFoundException;
import demo.store.model.Product;
import demo.store.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProductService {
    private final Map<Long, Product> products = Map.of(
        1L, new Product(1L, "Product 1", "Apple", 100.0),
        2L, new Product(2L, "Product 2", "Banana", 200.0),
        3L, new Product(3L, "Product 3", "Orange", 300.0),
        4L, new Product(4L, "Product 4", "Grapes", 400.0),
        5L, new Product(5L, "Product 5", "Pineapple", 500.0),
        6L, new Product(6L, "Product 6", "Broccoli", 600.0),
        7L, new Product(7L, "Product 7", "Carrot", 700.0),
        8L, new Product(8L, "Product 8", "Cauliflower", 800.0),
        9L, new Product(9L, "Product 9", "Cabbage", 900.0),
        10L, new Product(10L, "Product 10", "Spinach", 1000.0)
    );

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    public Product update(Product product) {
        return productRepository.save(product);
    }

    public void deleteAll() {
        productRepository.deleteAll();
    }

    public boolean existsById(Long id) {
        return productRepository.existsById(id);
    }

    public long count() {
        return productRepository.count();
    }

}
