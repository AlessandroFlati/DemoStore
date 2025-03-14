package demo.store.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    public List<String> getSampleProducts() {
        return List.of("Apple", "Banana", "Orange");
    }
}
