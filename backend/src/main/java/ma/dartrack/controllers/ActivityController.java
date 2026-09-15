package ma.dartrack.controllers;

import java.util.List;
import java.util.UUID;
import ma.dartrack.common.ResourceNotFoundException;
import ma.dartrack.models.Project;
import ma.dartrack.repositories.ProjectRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/projects/{projectId}/activities")
public class ActivityController {
    private final ProjectRepository projectRepository;
    private final ActivityRepository activityRepository;

    public ActivityController(ProjectRepository projectRepository, ActivityRepository activityRepository) {
        this.projectRepository = projectRepository;
        this.activityRepository = activityRepository;
    }

    @GetMapping
    @Transactional(readOnly = true)
    public List<ActivityResponse> findAll(@PathVariable UUID projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Projet introuvable : " + projectId));
        return activityRepository.findAllByProjectOrderByOccurredAtDesc(project).stream()
                .map(activity -> new ActivityResponse(activity.getId(), activity.getProject().getId(),
                        activity.getStage() == null ? null : activity.getStage().getId(), activity.getType(),
                        activity.getDescription(), activity.getOccurredAt()))
                .toList();
    }
}