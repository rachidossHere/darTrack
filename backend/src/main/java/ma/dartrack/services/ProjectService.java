package ma.dartrack.services;

import java.util.List;
import java.util.UUID;
import ma.dartrack.models.ProjectCreateRequest;
import ma.dartrack.models.ProjectResponse;

public interface ProjectService {
    List<ProjectResponse> findAll();
    ProjectResponse findById(UUID id);
    ProjectResponse create(ProjectCreateRequest request);
    ProjectResponse update(UUID id, ProjectCreateRequest request);
    void delete(UUID id);
}
