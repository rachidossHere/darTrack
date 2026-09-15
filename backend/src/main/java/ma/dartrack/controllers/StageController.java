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
@RequestMapping("/api/v1")
public class StageController {

    private final StageService stageService;

    public StageController(StageService stageService) {
        this.stageService = stageService;
    }

    @GetMapping("/projects/{projectId}/stages")
    public List<StageResponse> findAllByProject(@PathVariable UUID projectId) {
        return stageService.findAllByProject(projectId);
    }

    @PostMapping("/projects/{projectId}/stages")
    public ResponseEntity<StageResponse> create(@PathVariable UUID projectId,
                                                @Valid @RequestBody StageCreateRequest request) {
        StageResponse response = stageService.create(projectId, request);
        return ResponseEntity.created(URI.create("/api/v1/stages/" + response.id())).body(response);
    }

    @GetMapping("/stages/{id}")
    public StageResponse findById(@PathVariable UUID id) {
        return stageService.findById(id);
    }

    @PutMapping("/stages/{id}")
    public StageResponse update(@PathVariable UUID id, @Valid @RequestBody StageCreateRequest request) {
        return stageService.update(id, request);
    }

    @DeleteMapping("/stages/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        stageService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/stages/{id}/submit")
    public StageResponse submit(@PathVariable UUID id) {
        return stageService.submit(id);
    }

    @PostMapping("/stages/{id}/approve")
    public StageResponse approve(@PathVariable UUID id) {
        return stageService.approve(id);
    }

    @PostMapping("/stages/{id}/reject")
    public StageResponse reject(@PathVariable UUID id, @Valid @RequestBody StageRejectRequest request) {
        return stageService.reject(id, request);
    }
}