package demo.store.service;

import demo.store.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProductService {
    private Map<Long, Product> products = Map.of(
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

    public List<Product> getProducts() {
        return List.copyOf(products.values());
    }

    public Product getProduct(Long id) {
        return products.get(id);
    }

    public void addProduct(Product product) {
        products.put(product.getId(), product);
    }

    public void removeProduct(Long id) {
        products.remove(id);
    }

    public void updateProduct(Product product) {
        products.put(product.getId(), product);
    }
}
