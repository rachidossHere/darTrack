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
import ma.dartrack.models.Stage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ActivityRepository activityRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository, ActivityRepository activityRepository) {
        this.projectRepository = projectRepository;
        this.activityRepository = activityRepository;
    }

    @Transactional(readOnly = true)
    public List<ProjectResponse> findAll() {
        return projectRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public ProjectResponse findById(UUID id) {
        return toResponse(getProject(id));
    }

    public ProjectResponse create(ProjectCreateRequest request) {
        OffsetDateTime now = now();
        Project project = new Project(UUID.randomUUID(), request.name(), request.description(), request.type(),
                request.address(), request.city(), request.initialBudget(), request.startDate(),
                request.estimatedEndDate(), request.status(), now, now);
        Project saved = projectRepository.save(project);
        activityRepository.save(new Activity(UUID.randomUUID(), saved, null, ActivityType.PROJECT_CREATED,
                "Projet créé : " + saved.getName(), now));
        return toResponse(saved);
    }

    public ProjectResponse update(UUID id, ProjectCreateRequest request) {
        Project project = getProject(id);
        project.updateDetails(request.name(), request.description(), request.type(), request.address(),
                request.city(), request.initialBudget(), request.startDate(), request.estimatedEndDate(),
                request.status(), now());
        Project saved = projectRepository.save(project);
        activityRepository.save(new Activity(UUID.randomUUID(), saved, null, ActivityType.PROJECT_UPDATED,
                "Projet modifié : " + saved.getName(), saved.getUpdatedAt()));
        return toResponse(saved);
    }

    public void delete(UUID id) {
        Project project = getProject(id);
        projectRepository.delete(project);
    }

    private Project getProject(UUID id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Projet introuvable : " + id));
    }

    private ProjectResponse toResponse(Project project) {
        return new ProjectResponse(project.getId(), project.getName(), project.getDescription(), project.getType(),
                project.getAddress(), project.getCity(), project.getInitialBudget(), project.getStartDate(),
                project.getEstimatedEndDate(), project.getStatus(), calculateProgress(project.getStages()),
                project.getCreatedAt(), project.getUpdatedAt());
    }

    private int calculateProgress(List<Stage> stages) {
        if (stages.isEmpty()) {
            return 0;
        }
        return (int) Math.round(stages.stream().mapToInt(Stage::getProgress).average().orElse(0));
    }

    private OffsetDateTime now() {
        return OffsetDateTime.now(ZoneOffset.UTC);
    }
}