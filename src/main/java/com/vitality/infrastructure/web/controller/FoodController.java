package com.vitality.infrastructure.web.controller;

import com.vitality.application.dto.food.FoodCategoryResponse;
import com.vitality.application.dto.food.FoodCreateRequest;
import com.vitality.application.dto.food.FoodDetailResponse;
import com.vitality.application.dto.food.FoodSummaryResponse;
import com.vitality.application.service.FoodService;
import jakarta.validation.Valid;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/foods")
@RequiredArgsConstructor
public class FoodController {

  private final FoodService foodService;

  @GetMapping
  public ResponseEntity<List<FoodSummaryResponse>> getAllFoods(
      @RequestParam(required = false) String search,
      @RequestParam(required = false) Long categoryId) {
    return ResponseEntity.ok(foodService.getAllFoods(search, categoryId));
  }

  @GetMapping("/categories")
  public ResponseEntity<List<FoodCategoryResponse>> getCategories() {
    return ResponseEntity.ok(foodService.getAllCategories());
  }

  @GetMapping("/{id}")
  public ResponseEntity<FoodDetailResponse> getFoodById(@PathVariable Long id) {
    return ResponseEntity.ok(foodService.getFoodById(id));
  }

  @GetMapping("/slug/{slug}")
  public ResponseEntity<FoodDetailResponse> getFoodBySlug(@PathVariable String slug) {
    return ResponseEntity.ok(foodService.getFoodBySlug(slug));
  }

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<FoodDetailResponse> createFood(
      @Valid @RequestBody FoodCreateRequest request) {
    return ResponseEntity.ok(foodService.createFood(request));
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<FoodDetailResponse> updateFood(@PathVariable Long id,
                                                       @Valid @RequestBody
                                                       FoodCreateRequest request) {
    return ResponseEntity.ok(foodService.updateFood(id, request));
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Void> deleteFood(@PathVariable Long id) {
    foodService.deleteFood(id);
    return ResponseEntity.noContent().build();
  }
}
