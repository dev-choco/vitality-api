package com.vitality.application.service;

import com.vitality.application.dto.goal.GoalCreateRequest;
import com.vitality.application.dto.goal.GoalResponse;
import com.vitality.domain.model.Goal;
import com.vitality.infrastructure.persistence.repository.GoalRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GoalService {

  private final GoalRepository goalRepository;

  public List<GoalResponse> getAllGoals() {
    return goalRepository.findByActiveTrue().stream()
        .map(this::toResponse)
        .toList();
  }

  public GoalResponse getGoalById(Long id) {
    Goal goal = goalRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Goal not found"));
    return toResponse(goal);
  }

  public GoalResponse getGoalBySlug(String slug) {
    Goal goal = goalRepository.findBySlug(slug)
        .orElseThrow(() -> new IllegalArgumentException("Goal not found"));
    return toResponse(goal);
  }

  @Transactional
  public GoalResponse createGoal(GoalCreateRequest request) {
    Goal goal = new Goal();
    goal.setName(request.name());
    goal.setSlug(request.slug());
    goal.setIcon(request.icon());
    goal.setDescription(request.description());
    goal.setColorClass(request.colorClass());
    goal = goalRepository.save(goal);
    return toResponse(goal);
  }

  @Transactional
  public GoalResponse updateGoal(Long id, GoalCreateRequest request) {
    Goal goal = goalRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Goal not found"));
    goal.setName(request.name());
    goal.setSlug(request.slug());
    goal.setIcon(request.icon());
    goal.setDescription(request.description());
    goal.setColorClass(request.colorClass());
    goal = goalRepository.save(goal);
    return toResponse(goal);
  }

  @Transactional
  public void deleteGoal(Long id) {
    Goal goal = goalRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Goal not found"));
    goal.setActive(false);
    goalRepository.save(goal);
  }

  private GoalResponse toResponse(Goal goal) {
    return new GoalResponse(
        goal.getSlug(),
        goal.getName(),
        goal.getIcon(),
        goal.getDescription(),
        goal.getColorClass()
    );
  }
}
