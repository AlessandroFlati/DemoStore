package demo.store.service;

import demo.store.model.Category;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CategoryService {
    private Map<Long, Category> categories = Map.of(
        1L, new Category(1L, "Fruits"),
        2L, new Category(2L, "Vegetables")
    );

    public List<Category> getCategories() {
        return List.copyOf(categories.values());
    }

    public Category getCategory(Long id) {
        return categories.get(id);
    }

    public void addCategory(Category category) {
        categories.put(category.getId(), category);
    }

    public void removeCategory(Long id) {
        categories.remove(id);
    }

    public void updateCategory(Category category) {
        categories.put(category.getId(), category);
    }
}
