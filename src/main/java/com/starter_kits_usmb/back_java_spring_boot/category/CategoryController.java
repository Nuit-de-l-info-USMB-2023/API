package com.starter_kits_usmb.back_java_spring_boot.category;

import com.starter_kits_usmb.back_java_spring_boot.category.dto.CategoryCreateDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Tag(name = "category controller", description = "Endpoints for managing category")
public class CategoryController {
    private final CategoryRepository categoryRepository;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all categories")
    public List<Category> getAllCategories() { return categoryRepository.findAll(); }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get category by id")
    public Optional<Category> getCategoryById(@PathVariable long id) { return categoryRepository.findById(id); }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get an category by id")
    public Category createCategory(@Valid @RequestBody CategoryCreateDTO categoryTDO) {
        Category category = new Category();
        category.setName(categoryTDO.getName());
        return categoryRepository.save(category);
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update the category")
    public Optional<Category> updateCategory(@Valid @RequestBody Category category) {
        if (categoryRepository.existsById(category.getId())) {
            return Optional.of(categoryRepository.save(category));
        }
        return Optional.empty();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete a category")
    public void deleteCategoryById(@PathVariable long id) { categoryRepository.deleteById(id);}

}
