package ma.dartrack.services.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;
import ma.dartrack.common.ResourceNotFoundException;
import ma.dartrack.models.Activity;
import ma.dartrack.models.ActivityType;
import ma.dartrack.models.Document;
import ma.dartrack.models.Project;
import ma.dartrack.models.ProjectCreateRequest;
import ma.dartrack.models.ProjectResponse;
import ma.dartrack.models.ProjectStatus;
import ma.dartrack.models.ProjectType;
import ma.dartrack.models.Stage;
import ma.dartrack.repositories.ActivityRepository;
import ma.dartrack.repositories.DocumentRepository;
import ma.dartrack.repositories.ProjectRepository;
import ma.dartrack.repositories.StageRepository;
import ma.dartrack.services.ProjectService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ActivityRepository activityRepository;
    private final StageRepository stageRepository;
    private final DocumentRepository documentRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository, ActivityRepository activityRepository,
                             StageRepository stageRepository, DocumentRepository documentRepository) {
        this.projectRepository = projectRepository;
        this.activityRepository = activityRepository;
        this.stageRepository = stageRepository;
        this.documentRepository = documentRepository;
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

        List<Stage> stages = stageRepository.findAllByProjectOrderByDisplayOrder(project);
        if (!stages.isEmpty()) {
            stageRepository.deleteAll(stages);
        }

        List<Document> documents = documentRepository.findAllByProjectOrderByAddedAtDesc(project);
        for (Document document : documents) {
            try {
                Files.deleteIfExists(Path.of(document.getLocalPath()));
            } catch (IOException exception) {
                throw new IllegalStateException("Impossible de supprimer le fichier du document " + document.getId(), exception);
            }
        }
        if (!documents.isEmpty()) {
            documentRepository.deleteAll(documents);
        }

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