package com.vitality.infrastructure.web.controller;

import com.vitality.application.dto.food.FoodCategoryResponse;
import com.vitality.application.service.FoodCategoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/foods/categories")
@RequiredArgsConstructor
public class FoodCategoryController {

  private final FoodCategoryService foodCategoryService;

  @GetMapping
  public ResponseEntity<List<FoodCategoryResponse>> getAllCategories() {
    return ResponseEntity.ok(foodCategoryService.getAll());
  }

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<FoodCategoryResponse> createCategory(
      @Valid @RequestBody FoodCategoryCreateRequest request) {
    return ResponseEntity.ok(foodCategoryService.create(request));
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<FoodCategoryResponse> updateCategory(
      @PathVariable Long id,
      @Valid @RequestBody FoodCategoryCreateRequest request) {
    return ResponseEntity.ok(foodCategoryService.update(id, request));
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
    foodCategoryService.delete(id);
    return ResponseEntity.noContent().build();
  }

  public record FoodCategoryCreateRequest(
      @NotBlank String name,
      @NotBlank String icon
  ) {}
}
