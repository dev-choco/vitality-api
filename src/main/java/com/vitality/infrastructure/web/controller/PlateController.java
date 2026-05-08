package com.vitality.infrastructure.web.controller;

import com.vitality.application.dto.plate.PlateResponse;
import com.vitality.application.dto.plate.SavePlateRequest;
import com.vitality.application.service.PlateService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/plates")
@RequiredArgsConstructor
public class PlateController {

  private final PlateService plateService;

  @PostMapping
  public ResponseEntity<PlateResponse> savePlate(Authentication auth,
                                                 @Valid @RequestBody SavePlateRequest request) {
    Long userId = (Long) auth.getCredentials();
    return ResponseEntity.ok(plateService.savePlate(userId, request));
  }

  @GetMapping
  public ResponseEntity<List<PlateResponse>> getUserPlates(Authentication auth) {
    Long userId = (Long) auth.getCredentials();
    return ResponseEntity.ok(plateService.getUserPlates(userId));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletePlate(Authentication auth, @PathVariable Long id) {
    Long userId = (Long) auth.getCredentials();
    plateService.deletePlate(userId, id);
    return ResponseEntity.noContent().build();
  }
}
