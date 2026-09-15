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
public class ExpenseController {
    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) { this.expenseService = expenseService; }

    @GetMapping("/projects/{projectId}/expenses")
    public List<ExpenseResponse> findAll(@PathVariable UUID projectId) { return expenseService.findAll(projectId); }

    @PostMapping("/projects/{projectId}/expenses")
    public ResponseEntity<ExpenseResponse> create(@PathVariable UUID projectId, @Valid @RequestBody ExpenseRequest request) {
        ExpenseResponse response = expenseService.create(projectId, request);
        return ResponseEntity.created(URI.create("/api/v1/expenses/" + response.id())).body(response);
    }

    @PutMapping("/expenses/{id}")
    public ExpenseResponse update(@PathVariable UUID id, @Valid @RequestBody ExpenseRequest request) {
        return expenseService.update(id, request);
    }

    @DeleteMapping("/expenses/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        expenseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}