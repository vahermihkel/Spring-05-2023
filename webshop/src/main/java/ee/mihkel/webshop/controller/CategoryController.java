package ee.mihkel.webshop.controller;
import ee.mihkel.webshop.entity.Category;
import ee.mihkel.webshop.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {
    @Autowired
    CategoryRepository categoryRepository;
    // Taking all
    @GetMapping("categories")
    public List<Category> getCategories(){
        return categoryRepository.findAll();
    }
    // Taking  by id
    @GetMapping("categories/{id}")
    public Category getCategory(@PathVariable Long id){
        return categoryRepository.findById(id).get();
    }
    // Deleting
    @DeleteMapping("categories/{id}")
    public List<Category> deleteCategory(@PathVariable Long id){
        categoryRepository.deleteById(id);
        return categoryRepository.findAll();
    }
    // Adding category
    @PostMapping("categories")
    public List<Category> addCategory(@RequestBody Category category){
        categoryRepository.save(category);
        return categoryRepository.findAll();
    }
    // Editing category
    @PutMapping("categories")
    public List<Category> editCategory(@RequestBody Category category){
        if (categoryRepository.existsById(category.getId())){
            categoryRepository.save(category);
        }
        return categoryRepository.findAll();
    }
}
