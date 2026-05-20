package com.vitality.application.service;

import com.vitality.application.dto.myth.MythCreateRequest;
import com.vitality.application.dto.myth.MythDetailResponse;
import com.vitality.application.dto.myth.MythSummaryResponse;
import com.vitality.domain.model.Myth;
import com.vitality.infrastructure.persistence.repository.MythRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MythService {

  private final MythRepository mythRepository;

  public Page<MythSummaryResponse> getAllMyths(String category, Pageable pageable) {
    Page<Myth> page;

    if (category != null && !category.isBlank()) {
      page = mythRepository.findByCategoryIgnoreCaseAndActiveTrue(category, pageable);
    } else {
      page = mythRepository.findByActiveTrue(pageable);
    }

    return page.map(this::toSummary);
  }

  public MythDetailResponse getMythById(Long id) {
    Myth myth = mythRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Myth not found"));
    return toDetail(myth);
  }

  @Transactional
  public MythDetailResponse createMyth(MythCreateRequest request) {
    Myth myth = new Myth();
    myth.setMythText(request.mythText());
    myth.setRealityText(request.realityText());
    myth.setMythExplanation(request.mythExplanation());
    myth.setRealityExplanation(request.realityExplanation());
    myth.setCategory(request.category());
    myth.setImageUrl(request.imageUrl());
    myth.setScientificSource(request.scientificSource());
    myth = mythRepository.save(myth);
    return toDetail(myth);
  }

  @Transactional
  public MythDetailResponse updateMyth(Long id, MythCreateRequest request) {
    Myth myth = mythRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Myth not found"));
    myth.setMythText(request.mythText());
    myth.setRealityText(request.realityText());
    myth.setMythExplanation(request.mythExplanation());
    myth.setRealityExplanation(request.realityExplanation());
    myth.setCategory(request.category());
    myth.setImageUrl(request.imageUrl());
    myth.setScientificSource(request.scientificSource());
    myth = mythRepository.save(myth);
    return toDetail(myth);
  }

  @Transactional
  public void deleteMyth(Long id) {
    Myth myth = mythRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Myth not found"));
    myth.setActive(false);
    mythRepository.save(myth);
  }

  private MythSummaryResponse toSummary(Myth myth) {
    return new MythSummaryResponse(
        myth.getId(),
        myth.getMythText(),
        myth.getRealityText(),
        myth.getCategory()
    );
  }

  private MythDetailResponse toDetail(Myth myth) {
    return new MythDetailResponse(
        myth.getId(),
        myth.getMythText(),
        myth.getRealityText(),
        myth.getMythExplanation(),
        myth.getRealityExplanation(),
        myth.getCategory(),
        myth.getImageUrl(),
        myth.getScientificSource(),
        myth.getCreatedAt()
    );
  }
}
