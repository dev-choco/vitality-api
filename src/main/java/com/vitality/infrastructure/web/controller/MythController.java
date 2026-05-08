package com.vitality.infrastructure.web.controller;

import com.vitality.application.dto.myth.MythDetailResponse;
import com.vitality.application.dto.myth.MythSummaryResponse;
import com.vitality.application.service.MythService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/myths")
@RequiredArgsConstructor
public class MythController {

  private final MythService mythService;

  @GetMapping
  public ResponseEntity<Page<MythSummaryResponse>> getAllMyths(
      @RequestParam(required = false) String category,
      @PageableDefault(size = 20) Pageable pageable) {
    return ResponseEntity.ok(mythService.getAllMyths(category, pageable));
  }

  @GetMapping("/{id}")
  public ResponseEntity<MythDetailResponse> getMythById(@PathVariable Long id) {
    return ResponseEntity.ok(mythService.getMythById(id));
  }
}
