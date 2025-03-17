package demo.store.service;

import demo.store.exception.ResourceNotFoundException;
import demo.store.model.Product;
import demo.store.repository.ProductRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Cacheable("products")
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    // Dynamic cache key
    @Cacheable(value = "products", key = "#id")
    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }

    // Dynamic cache key
    @CachePut(value = "products", key = "#product.id")
    public Product save(Product product) {
        return productRepository.save(product);
    }

    // Dynamic cache key
    @CacheEvict(value = "products", key = "#id")
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @CachePut(value = "products", key = "#product.id")
    public Product update(Product product) {
        return productRepository.save(product);
    }

    @CacheEvict(value = "products", allEntries = true)
    public void deleteAll() {
        productRepository.deleteAll();
    }

    @Cacheable(value = "products", key = "#id")
    public boolean existsById(Long id) {
        return productRepository.existsById(id);
    }

    public long count() {
        return productRepository.count();
    }

}
