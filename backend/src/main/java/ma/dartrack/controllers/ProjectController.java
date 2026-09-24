package ma.dartrack.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import ma.dartrack.common.ApiError;
import ma.dartrack.models.ProjectCreateRequest;
import ma.dartrack.models.ProjectResponse;
import ma.dartrack.services.ProjectService;
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
@Tag(name = "Projets", description = "Gestion locale des projets de renovation DarTrack")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    @Operation(
            summary = "Lister les projets",
            description = "Retourne les projets de renovation disponibles pour le proprietaire.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Liste des projets retournee")
            })
    public List<ProjectResponse> findAll() {
        return projectService.findAll();
    }

    @PostMapping
    @Operation(
            summary = "Creer un projet",
            description = "Cree un projet de renovation local avec ses informations principales.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Projet cree"),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Donnees de projet invalides",
                            content = @Content(schema = @Schema(implementation = ApiError.class)))
            })
    public ResponseEntity<ProjectResponse> create(@Valid @RequestBody ProjectCreateRequest request) {
        ProjectResponse response = projectService.create(request);
        return ResponseEntity.created(URI.create("/api/v1/projects/" + response.id())).body(response);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Consulter un projet",
            description = "Retourne le detail d'un projet de renovation.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Projet trouve"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Projet introuvable",
                            content = @Content(schema = @Schema(implementation = ApiError.class)))
            })
    public ProjectResponse findById(@Parameter(description = "Identifiant du projet") @PathVariable UUID id) {
        return projectService.findById(id);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Modifier un projet",
            description = "Met a jour les informations principales d'un projet de renovation.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Projet modifie"),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Donnees de projet invalides",
                            content = @Content(schema = @Schema(implementation = ApiError.class))),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Projet introuvable",
                            content = @Content(schema = @Schema(implementation = ApiError.class)))
            })
    public ProjectResponse update(@Parameter(description = "Identifiant du projet") @PathVariable UUID id,
                                  @Valid @RequestBody ProjectCreateRequest request) {
        return projectService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Supprimer un projet",
            description = "Supprime un projet de renovation local.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Projet supprime"),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Projet introuvable",
                            content = @Content(schema = @Schema(implementation = ApiError.class)))
            })
    public ResponseEntity<Void> delete(@Parameter(description = "Identifiant du projet") @PathVariable UUID id) {
        projectService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
