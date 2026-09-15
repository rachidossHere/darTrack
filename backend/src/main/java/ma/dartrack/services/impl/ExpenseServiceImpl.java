package ma.dartrack.services.impl;

import java.math.BigDecimal;
import ma.dartrack.models.*;
import ma.dartrack.repositories.*;
import ma.dartrack.services.*;
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
import ma.dartrack.models.Stage;
import ma.dartrack.repositories.StageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ProjectRepository projectRepository;
    private final StageRepository stageRepository;
    private final ActivityRepository activityRepository;

    public ExpenseServiceImpl(ExpenseRepository expenseRepository, ProjectRepository projectRepository,
                          StageRepository stageRepository, ActivityRepository activityRepository) {
        this.expenseRepository = expenseRepository;
        this.projectRepository = projectRepository;
        this.stageRepository = stageRepository;
        this.activityRepository = activityRepository;
    }

    @Transactional(readOnly = true)
    public List<ExpenseResponse> findAll(UUID projectId) {
        Project project = getProject(projectId);
        return expenseRepository.findAllByProjectOrderByExpenseDateDesc(project).stream().map(this::toResponse).toList();
    }

    public ExpenseResponse create(UUID projectId, ExpenseRequest request) {
        Project project = getProject(projectId);
        OffsetDateTime now = now();
        Expense expense = new Expense(UUID.randomUUID(), project, getStage(request.stageId(), project), request.label(),
                request.amount(), request.expenseDate(), request.category(), request.provider(), request.reference(),
                request.paymentStatus(), now, now);
        Expense saved = expenseRepository.save(expense);
        activityRepository.save(new Activity(UUID.randomUUID(), project, saved.getStage(), ActivityType.EXPENSE_ADDED,
                "Dépense ajoutée : " + saved.getLabel(), now));
        return toResponse(saved);
    }

    public ExpenseResponse update(UUID id, ExpenseRequest request) {
        Expense expense = getExpense(id);
        Stage stage = getStage(request.stageId(), expense.getProject());
        expense.updateDetails(stage, request.label(), request.amount(), request.expenseDate(), request.category(),
                request.provider(), request.reference(), request.paymentStatus(), now());
        return toResponse(expenseRepository.save(expense));
    }

    public void delete(UUID id) {
        expenseRepository.delete(getExpense(id));
    }

    @Transactional(readOnly = true)
    public BigDecimal total(UUID projectId) {
        return findAll(projectId).stream().map(ExpenseResponse::amount).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Project getProject(UUID id) {
        return projectRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Projet introuvable : " + id));
    }

    private Expense getExpense(UUID id) {
        return expenseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Dépense introuvable : " + id));
    }

    private Stage getStage(UUID id, Project project) {
        if (id == null) return null;
        Stage stage = stageRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Étape introuvable : " + id));
        if (!stage.getProject().getId().equals(project.getId())) throw new IllegalArgumentException("L'étape n'appartient pas au projet.");
        return stage;
    }

    private ExpenseResponse toResponse(Expense expense) {
        return new ExpenseResponse(expense.getId(), expense.getProject().getId(),
                expense.getStage() == null ? null : expense.getStage().getId(), expense.getLabel(), expense.getAmount(),
                expense.getExpenseDate(), expense.getCategory(), expense.getProvider(), expense.getReference(),
                expense.getPaymentStatus(), expense.getCreatedAt(), expense.getUpdatedAt());
    }

    private OffsetDateTime now() { return OffsetDateTime.now(ZoneOffset.UTC); }
}