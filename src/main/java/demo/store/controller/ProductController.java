package demo.store.controller;

import demo.store.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ProductController {
    // Inject Product Service using constructor injection (DI)
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Add a new endpoint to get sample products
    @GetMapping("/products")
    public String getProducts() {
        return productService.getSampleProducts().toString();
    }
}
