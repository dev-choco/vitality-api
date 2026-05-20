package com.vitality.application.service;

import com.vitality.application.dto.food.FoodCategoryResponse;
import com.vitality.domain.model.FoodCategory;
import com.vitality.infrastructure.persistence.repository.FoodCategoryRepository;
import com.vitality.infrastructure.web.controller.FoodCategoryController.FoodCategoryCreateRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FoodCategoryService {

  private final FoodCategoryRepository foodCategoryRepository;

  public List<FoodCategoryResponse> getAll() {
    return foodCategoryRepository.findAll().stream()
        .map(this::toResponse)
        .toList();
  }

  @Transactional
  public FoodCategoryResponse create(FoodCategoryCreateRequest request) {
    FoodCategory category = new FoodCategory();
    category.setName(request.name());
    category.setSlug(generateSlug(request.name()));
    category.setIcon(request.icon());
    category = foodCategoryRepository.save(category);
    return toResponse(category);
  }

  @Transactional
  public FoodCategoryResponse update(Long id, FoodCategoryCreateRequest request) {
    FoodCategory category = foodCategoryRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Category not found"));
    category.setName(request.name());
    category.setSlug(generateSlug(request.name()));
    category.setIcon(request.icon());
    category = foodCategoryRepository.save(category);
    return toResponse(category);
  }

  @Transactional
  public void delete(Long id) {
    if (!foodCategoryRepository.existsById(id)) {
      throw new IllegalArgumentException("Category not found");
    }
    foodCategoryRepository.deleteById(id);
  }

  private FoodCategoryResponse toResponse(FoodCategory category) {
    return new FoodCategoryResponse(
        category.getId(),
        category.getName(),
        category.getSlug(),
        category.getIcon()
    );
  }

  private String generateSlug(String name) {
    return name.toLowerCase()
        .replaceAll("[^a-z0-9\\s-]", "")
        .replaceAll("\\s+", "-")
        .replaceAll("-+", "-");
  }
}
