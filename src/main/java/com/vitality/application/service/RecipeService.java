package com.vitality.application.service;

import com.vitality.application.dto.recipe.RecipeCreateRequest;
import com.vitality.application.dto.recipe.RecipeDetailResponse;
import com.vitality.application.dto.recipe.RecipeSummaryResponse;
import com.vitality.domain.model.Food;
import com.vitality.domain.model.Recipe;
import com.vitality.domain.model.RecipeIngredient;
import com.vitality.infrastructure.persistence.repository.FoodRepository;
import com.vitality.infrastructure.persistence.repository.RecipeRepository;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecipeService {

  private static final Logger log = LoggerFactory.getLogger(RecipeService.class);

  private final RecipeRepository recipeRepository;
  private final FoodRepository foodRepository;

  public Page<RecipeSummaryResponse> getAllRecipes(String goal, String budget, String mealType,
                                                   Pageable pageable) {
    String goalPattern = (goal != null && !goal.isBlank()) ? "%" + goal.toLowerCase() + "%" : null;
    String budgetPattern = (budget != null && !budget.isBlank()) ? "%" + budget.toLowerCase() + "%" : null;
    String mealTypeParam = (mealType != null && !mealType.isBlank()) ? mealType.toLowerCase() : null;

    Page<Recipe> page = recipeRepository.findByFilters(goalPattern, budgetPattern, mealTypeParam,
        pageable);
    return page.map(this::toSummary);
  }

  public Page<RecipeSummaryResponse> getRecipesByIngredients(String ingredients,
                                                              Pageable pageable) {
    if (ingredients == null || ingredients.isBlank()) {
      return getAllRecipes(null, null, null, pageable);
    }

    List<String> ingredientNames = Arrays.stream(ingredients.split(","))
        .map(String::trim)
        .map(String::toLowerCase)
        .filter(s -> !s.isEmpty())
        .toList();

    if (ingredientNames.isEmpty()) {
      return getAllRecipes(null, null, null, pageable);
    }

    try {
      Page<Recipe> page = recipeRepository.findByIngredientNames(ingredientNames, pageable);
      return page.map(this::toSummary);
    } catch (Exception e) {
      log.warn("Error searching by ingredients '{}': {}", ingredients, e.getMessage());
      return Page.empty(pageable);
    }
  }

  public RecipeDetailResponse getRecipeById(Long id) {
    Recipe recipe = recipeRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Recipe not found"));
    return toDetail(recipe);
  }

  public RecipeDetailResponse getRecipeBySlug(String slug) {
    Recipe recipe = recipeRepository.findBySlug(slug)
        .orElseThrow(() -> new IllegalArgumentException("Recipe not found"));
    return toDetail(recipe);
  }

  @Transactional
  public RecipeDetailResponse createRecipe(RecipeCreateRequest request) {
    Recipe recipe = new Recipe();
    recipe.setTitle(request.title());
    recipe.setSlug(generateSlug(request.title()));
    recipe.setDescription(request.description());
    recipe.setImageUrl(request.imageUrl());
    recipe.setPrepTimeMin(request.prepTimeMin());
    recipe.setDifficulty(request.difficulty());
    recipe.setBudgetTag(request.budgetTag());
    recipe.setCalories(request.calories());
    recipe.setProteinG(request.proteinG());
    recipe.setCarbsG(request.carbsG());
    recipe.setFatG(request.fatG());
    recipe.setGoalTags(request.goalTags());
    recipe.setMealType(request.mealType());
    recipe.setInstructions(request.instructions());

    if (request.ingredients() != null) {
      for (var ing : request.ingredients()) {
        Food food = foodRepository.findById(ing.foodId())
            .orElseThrow(() -> new IllegalArgumentException("Food not found: " + ing.foodId()));
        RecipeIngredient ingredient = new RecipeIngredient();
        ingredient.setRecipe(recipe);
        ingredient.setFood(food);
        ingredient.setQuantity(ing.quantity());
        ingredient.setUnit(ing.unit());
        recipe.getIngredients().add(ingredient);
      }
    }

    recipe = recipeRepository.save(recipe);
    return toDetail(recipe);
  }

  @Transactional
  public void deleteRecipe(Long id) {
    Recipe recipe = recipeRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Recipe not found"));
    recipe.setActive(false);
    recipeRepository.save(recipe);
  }

  private RecipeSummaryResponse toSummary(Recipe recipe) {
    List<String> ingredientNames = recipe.getIngredients().stream()
        .map(ri -> ri.getFood().getName())
        .toList();

    String primaryGoal = null;
    if (recipe.getGoalTags() != null && !recipe.getGoalTags().isBlank()) {
      primaryGoal = recipe.getGoalTags().split(",")[0].trim();
    }

    return new RecipeSummaryResponse(
        recipe.getId(),
        recipe.getTitle(),
        recipe.getSlug(),
        recipe.getImageUrl(),
        recipe.getPrepTimeMin(),
        recipe.getBudgetTag(),
        recipe.getCalories(),
        primaryGoal,
        ingredientNames
    );
  }

  private RecipeDetailResponse toDetail(Recipe recipe) {
    List<RecipeDetailResponse.IngredientItem> ingredients = recipe.getIngredients().stream()
        .map(ri -> new RecipeDetailResponse.IngredientItem(
            ri.getFood().getId(),
            ri.getFood().getName(),
            ri.getFood().getSlug(),
            ri.getQuantity(),
            ri.getUnit()))
        .toList();

    return new RecipeDetailResponse(
        recipe.getId(),
        recipe.getTitle(),
        recipe.getSlug(),
        recipe.getDescription(),
        recipe.getImageUrl(),
        recipe.getPrepTimeMin(),
        recipe.getDifficulty(),
        recipe.getBudgetTag(),
        recipe.getCalories(),
        recipe.getProteinG(),
        recipe.getCarbsG(),
        recipe.getFatG(),
        recipe.getGoalTags(),
        recipe.getInstructions(),
        ingredients,
        recipe.getCreatedAt()
    );
  }

  private String generateSlug(String title) {
    return title.toLowerCase()
        .replaceAll("[^a-z0-9\\s-]", "")
        .replaceAll("\\s+", "-")
        .replaceAll("-+", "-");
  }
}
