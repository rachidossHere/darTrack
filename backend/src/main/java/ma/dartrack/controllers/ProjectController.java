package ma.dartrack.controllers;

import jakarta.validation.Valid;
import ma.dartrack.models.*;
import ma.dartrack.services.*;
import ma.dartrack.models.*;
import ma.dartrack.services.*;
import ma.dartrack.models.*;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public List<ProjectResponse> findAll() {
        return projectService.findAll();
    }

    @PostMapping
    public ResponseEntity<ProjectResponse> create(@Valid @RequestBody ProjectCreateRequest request) {
        ProjectResponse response = projectService.create(request);
        return ResponseEntity.created(URI.create("/api/v1/projects/" + response.id())).body(response);
    }

    @GetMapping("/{id}")
    public ProjectResponse findById(@PathVariable UUID id) {
        return projectService.findById(id);
    }

    @PutMapping("/{id}")
    public ProjectResponse update(@PathVariable UUID id, @Valid @RequestBody ProjectCreateRequest request) {
        return projectService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        projectService.delete(id);
        return ResponseEntity.noContent().build();
    }
}