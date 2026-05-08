package com.vitality.application.service;

import com.vitality.application.dto.plate.PlateResponse;
import com.vitality.application.dto.plate.SavePlateRequest;
import com.vitality.domain.model.SavedPlate;
import com.vitality.domain.model.User;
import com.vitality.infrastructure.persistence.repository.FoodRepository;
import com.vitality.infrastructure.persistence.repository.SavedPlateRepository;
import com.vitality.infrastructure.persistence.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlateService {

  private final SavedPlateRepository savedPlateRepository;
  private final UserRepository userRepository;
  private final FoodRepository foodRepository;

  @Transactional
  public PlateResponse savePlate(Long userId, SavePlateRequest request) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("User not found"));

    SavedPlate plate = new SavedPlate();
    plate.setUser(user);

    if (request.proteinFoodId() != null) {
      plate.setProteinFood(foodRepository.findById(request.proteinFoodId())
          .orElseThrow(() -> new IllegalArgumentException("Protein food not found")));
    }
    if (request.carbFoodId() != null) {
      plate.setCarbFood(foodRepository.findById(request.carbFoodId())
          .orElseThrow(() -> new IllegalArgumentException("Carb food not found")));
    }

    plate.setVeggieFoodIds(request.veggieFoodIds().stream()
        .map(String::valueOf)
        .collect(Collectors.joining(",")));
    plate.setNotes(request.notes());

    plate = savedPlateRepository.save(plate);
    return toResponse(plate);
  }

  public List<PlateResponse> getUserPlates(Long userId) {
    return savedPlateRepository.findByUserIdOrderByCreatedAtDesc(userId).stream()
        .map(this::toResponse)
        .toList();
  }

  @Transactional
  public void deletePlate(Long userId, Long plateId) {
    SavedPlate plate = savedPlateRepository.findById(plateId)
        .orElseThrow(() -> new IllegalArgumentException("Plate not found"));

    if (!plate.getUser().getId().equals(userId)) {
      throw new IllegalArgumentException("Not authorized");
    }

    savedPlateRepository.delete(plate);
  }

  private PlateResponse toResponse(SavedPlate plate) {
    PlateResponse.FoodInfo protein = plate.getProteinFood() != null
        ? new PlateResponse.FoodInfo(plate.getProteinFood().getId(),
        plate.getProteinFood().getName(), plate.getProteinFood().getImageUrl())
        : null;

    PlateResponse.FoodInfo carb = plate.getCarbFood() != null
        ? new PlateResponse.FoodInfo(plate.getCarbFood().getId(),
        plate.getCarbFood().getName(), plate.getCarbFood().getImageUrl())
        : null;

    String veggieNames = "";
    if (plate.getVeggieFoodIds() != null && !plate.getVeggieFoodIds().isBlank()) {
      veggieNames = List.of(plate.getVeggieFoodIds().split(",")).stream()
          .map(id -> foodRepository.findById(Long.parseLong(id)))
          .filter(java.util.Optional::isPresent)
          .map(o -> o.get().getName())
          .collect(Collectors.joining(", "));
    }

    return new PlateResponse(plate.getId(), protein, carb, veggieNames, plate.getNotes(),
        plate.getCreatedAt());
  }
}
