package demo.store.controller;

import demo.store.model.Category;
import demo.store.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("")
    public List<Category> getCategories() {
        return categoryService.getCategories();
    }

    @GetMapping("/{id}")
    public Category getCategory(@PathVariable Long id) {
        return categoryService.getCategory(id);
    }

    @PostMapping("")
    public void addCategory(@RequestBody Category category) {
        categoryService.addCategory(category);
    }

    @PutMapping("/{id}")
    public void updateCategory(@PathVariable Long id, @RequestBody Category category) {
        category.setId(id);
        categoryService.updateCategory(category);
    }

    @DeleteMapping("/{id}")
    public void removeCategory(@PathVariable Long id) {
        categoryService.removeCategory(id);
    }

}