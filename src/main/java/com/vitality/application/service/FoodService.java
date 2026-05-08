package com.vitality.application.service;

import com.vitality.application.dto.food.FoodCategoryResponse;
import com.vitality.application.dto.food.FoodCreateRequest;
import com.vitality.application.dto.food.FoodDetailResponse;
import com.vitality.application.dto.food.FoodSummaryResponse;
import com.vitality.domain.model.Food;
import com.vitality.domain.model.FoodCategory;
import com.vitality.infrastructure.persistence.repository.FoodCategoryRepository;
import com.vitality.infrastructure.persistence.repository.FoodRepository;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FoodService {

  private final FoodRepository foodRepository;
  private final FoodCategoryRepository foodCategoryRepository;

  public List<FoodCategoryResponse> getAllCategories() {
    return foodCategoryRepository.findAll().stream()
        .map(c -> new FoodCategoryResponse(c.getId(), c.getName(), c.getSlug(), c.getIcon()))
        .toList();
  }

  public List<FoodSummaryResponse> getAllFoods(String search, Long categoryId) {
    List<Food> foods;

    if (search != null && !search.isBlank()) {
      foods = foodRepository.searchByName(search);
    } else if (categoryId != null) {
      foods = foodRepository.findByCategoryIdAndActiveTrue(categoryId);
    } else {
      foods = foodRepository.findByActiveTrue();
    }

    return foods.stream().map(this::toSummary).toList();
  }

  public FoodDetailResponse getFoodById(Long id) {
    Food food = foodRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Food not found"));
    return toDetail(food);
  }

  public FoodDetailResponse getFoodBySlug(String slug) {
    Food food = foodRepository.findBySlug(slug)
        .orElseThrow(() -> new IllegalArgumentException("Food not found"));
    return toDetail(food);
  }

  @Transactional
  public FoodDetailResponse createFood(FoodCreateRequest request) {
    FoodCategory category = foodCategoryRepository.findById(request.categoryId())
        .orElseThrow(() -> new IllegalArgumentException("Category not found"));

    Food food = new Food();
    food.setName(request.name());
    food.setSlug(generateSlug(request.name()));
    food.setCategory(category);
    food.setDescription(request.description());
    food.setImageUrl(request.imageUrl());
    food.setCaloriesPer100g(request.caloriesPer100g());
    food.setProteinG(request.proteinG());
    food.setCarbsG(request.carbsG());
    food.setFatG(request.fatG());
    food.setFiberG(request.fiberG());
    food.setBenefits(request.benefits());
    food.setConsumptionTips(request.consumptionTips());

    food = foodRepository.save(food);
    return toDetail(food);
  }

  @Transactional
  public FoodDetailResponse updateFood(Long id, FoodCreateRequest request) {
    Food food = foodRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Food not found"));
    FoodCategory category = foodCategoryRepository.findById(request.categoryId())
        .orElseThrow(() -> new IllegalArgumentException("Category not found"));

    food.setName(request.name());
    food.setSlug(generateSlug(request.name()));
    food.setCategory(category);
    food.setDescription(request.description());
    food.setImageUrl(request.imageUrl());
    food.setCaloriesPer100g(request.caloriesPer100g());
    food.setProteinG(request.proteinG());
    food.setCarbsG(request.carbsG());
    food.setFatG(request.fatG());
    food.setFiberG(request.fiberG());
    food.setBenefits(request.benefits());
    food.setConsumptionTips(request.consumptionTips());

    food = foodRepository.save(food);
    return toDetail(food);
  }

  @Transactional
  public void deleteFood(Long id) {
    Food food = foodRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Food not found"));
    food.setActive(false);
    foodRepository.save(food);
  }

  private FoodSummaryResponse toSummary(Food food) {
    return new FoodSummaryResponse(
        food.getId(),
        food.getName(),
        food.getSlug(),
        food.getCategory().getName(),
        food.getImageUrl(),
        extractPrimaryBenefit(food.getBenefits()),
        food.getConsumptionTips()
    );
  }

  private FoodDetailResponse toDetail(Food food) {
    List<FoodDetailResponse.BenefitItem> benefitItems = parseBenefits(food.getBenefits());

    return new FoodDetailResponse(
        food.getId(),
        food.getName(),
        food.getSlug(),
        new FoodCategoryResponse(food.getCategory().getId(), food.getCategory().getName(),
            food.getCategory().getSlug(), food.getCategory().getIcon()),
        food.getDescription(),
        food.getImageUrl(),
        food.getCaloriesPer100g(),
        food.getProteinG(),
        food.getCarbsG(),
        food.getFatG(),
        food.getFiberG(),
        benefitItems,
        food.getConsumptionTips()
    );
  }

  private List<FoodDetailResponse.BenefitItem> parseBenefits(String benefits) {
      if (benefits == null || benefits.isBlank()) {
          return List.of();
      }
    return Arrays.stream(benefits.split(","))
        .map(String::trim)
        .filter(b -> !b.isEmpty())
        .map(b -> new FoodDetailResponse.BenefitItem(detectIcon(b), b))
        .toList();
  }

  private String extractPrimaryBenefit(String benefits) {
      if (benefits == null || benefits.isBlank()) {
          return "";
      }
    return benefits.split(",")[0].trim();
  }

  private String detectIcon(String benefit) {
    String lower = benefit.toLowerCase();
      if (lower.contains("fibra") || lower.contains("grain")) {
          return "grain";
      }
      if (lower.contains("proteína") || lower.contains("protein")) {
          return "fitness_center";
      }
      if (lower.contains("energ") || lower.contains("energy")) {
          return "bolt";
      }
      if (lower.contains("económico") || lower.contains("barato")) {
          return "savings";
      }
      if (lower.contains("grasas") || lower.contains("heart")) {
          return "ecg";
      }
      if (lower.contains("piel") || lower.contains("skin")) {
          return "face";
      }
      if (lower.contains("ocular") || lower.contains("eye")) {
          return "visibility";
      }
      if (lower.contains("inmunidad") || lower.contains("immun")) {
          return "shield";
      }
      if (lower.contains("saciedad")) {
          return "battery_full";
      }
      if (lower.contains("antioxidante") || lower.contains("antioxidant")) {
          return "spa";
      }
    return "check_circle";
  }

  private String generateSlug(String name) {
    return name.toLowerCase()
        .replaceAll("[^a-z0-9\\s-]", "")
        .replaceAll("\\s+", "-")
        .replaceAll("-+", "-");
  }
}
