package ma.dartrack.services.impl;

import java.time.OffsetDateTime;
import ma.dartrack.models.*;
import ma.dartrack.repositories.*;
import ma.dartrack.services.*;
import java.time.ZoneOffset;
import ma.dartrack.models.*;
import ma.dartrack.repositories.*;
import ma.dartrack.services.*;
import java.util.List;
import ma.dartrack.models.*;
import ma.dartrack.repositories.*;
import ma.dartrack.services.*;
import java.util.UUID;
import ma.dartrack.models.*;
import ma.dartrack.repositories.*;
import ma.dartrack.services.*;
import ma.dartrack.models.Activity;
import ma.dartrack.repositories.ActivityRepository;
import ma.dartrack.models.ActivityType;
import ma.dartrack.common.ResourceNotFoundException;
import ma.dartrack.models.Project;
import ma.dartrack.repositories.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class StageServiceImpl implements StageService {

    private final StageRepository stageRepository;
    private final ProjectRepository projectRepository;
    private final ActivityRepository activityRepository;

    public StageServiceImpl(StageRepository stageRepository, ProjectRepository projectRepository,
                        ActivityRepository activityRepository) {
        this.stageRepository = stageRepository;
        this.projectRepository = projectRepository;
        this.activityRepository = activityRepository;
    }

    @Transactional(readOnly = true)
    public List<StageResponse> findAllByProject(UUID projectId) {
        Project project = getProject(projectId);
        return stageRepository.findAllByProjectOrderByDisplayOrder(project).stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public StageResponse findById(UUID id) {
        return toResponse(getStage(id));
    }

    public StageResponse create(UUID projectId, StageCreateRequest request) {
        Project project = getProject(projectId);
        OffsetDateTime now = now();
        int displayOrder = stageRepository.findFirstByProjectOrderByDisplayOrderDesc(project)
                .map(stage -> stage.getDisplayOrder() + 1)
                .orElse(0);
        Stage stage = new Stage(UUID.randomUUID(), project, request.title(), request.description(),
                displayOrder, request.plannedStartDate(), request.plannedEndDate(), request.plannedBudget(),
                0, StageStatus.TODO, null, now, now);
        Stage saved = stageRepository.save(stage);
        record(saved, ActivityType.STAGE_CREATED, "Étape créée : " + saved.getTitle(), now);
        return toResponse(saved);
    }

    public StageResponse update(UUID id, StageUpdateRequest request) {
        Stage stage = getStage(id);
        OffsetDateTime now = now();
        stage.updateDetails(request.title(), request.description(),
                request.plannedStartDate(), request.plannedEndDate(), request.plannedBudget(), request.progress(), now());
        Stage saved = stageRepository.save(stage);
        record(saved, ActivityType.STAGE_UPDATED, "Étape mise à jour : " + saved.getTitle(), now);
        return toResponse(saved);
    }

    public void delete(UUID id) {
        stageRepository.delete(getStage(id));
    }

    public StageResponse submit(UUID id) {
        Stage stage = getStage(id);
        OffsetDateTime now = now();
        stage.submitForApproval(now);
        Stage saved = stageRepository.save(stage);
        record(saved, ActivityType.STAGE_STATUS_CHANGED, "Étape envoyée en attente de validation : " + saved.getTitle(), now);
        return toResponse(saved);
    }

    public StageResponse approve(UUID id) {
        Stage stage = getStage(id);
        OffsetDateTime now = now();
        stage.approve(now);
        Stage saved = stageRepository.save(stage);
        record(saved, ActivityType.STAGE_APPROVED, "Étape validée : " + saved.getTitle(), now);
        return toResponse(saved);
    }

    public StageResponse reject(UUID id, StageRejectRequest request) {
        Stage stage = getStage(id);
        OffsetDateTime now = now();
        stage.reject(request.comment(), now);
        Stage saved = stageRepository.save(stage);
        record(saved, ActivityType.STAGE_REJECTED, "Étape refusée : " + saved.getTitle(), now);
        return toResponse(saved);
    }

    private Project getProject(UUID id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Projet introuvable : " + id));
    }

    private Stage getStage(UUID id) {
        return stageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Étape introuvable : " + id));
    }

    private void record(Stage stage, ActivityType type, String description, OffsetDateTime occurredAt) {
        activityRepository.save(new Activity(UUID.randomUUID(), stage.getProject(), stage, type, description, occurredAt));
    }

    private StageResponse toResponse(Stage stage) {
        return new StageResponse(stage.getId(), stage.getProject().getId(), stage.getTitle(), stage.getDescription(),
                stage.getDisplayOrder(), stage.getPlannedStartDate(), stage.getPlannedEndDate(), stage.getPlannedBudget(),
                stage.getProgress(), stage.getStatus(), stage.getRejectionComment(), stage.getCreatedAt(), stage.getUpdatedAt());
    }

    private OffsetDateTime now() {
        return OffsetDateTime.now(ZoneOffset.UTC);
    }
}
