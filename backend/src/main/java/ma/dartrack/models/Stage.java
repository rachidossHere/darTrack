package ma.dartrack.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;
import ma.dartrack.models.Project;

@Entity
@Table(name = "stages", uniqueConstraints = @UniqueConstraint(columnNames = {"project_id", "display_order"}))
public class Stage {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Column(nullable = false, length = 160)
    private String title;

    @Column(columnDefinition = "text")
    private String description;

    @Column(name = "display_order", nullable = false)
    private Integer displayOrder;

    @Column(name = "planned_start_date")
    private LocalDate plannedStartDate;

    @Column(name = "planned_end_date")
    private LocalDate plannedEndDate;

    @Column(name = "planned_budget", nullable = false, precision = 14, scale = 2)
    private BigDecimal plannedBudget;

    @Column(nullable = false)
    private Integer progress;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private StageStatus status;

    @Column(name = "rejection_comment", columnDefinition = "text")
    private String rejectionComment;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    protected Stage() {
    }

    public Stage(UUID id, Project project, String title, String description, Integer displayOrder,
                 LocalDate plannedStartDate, LocalDate plannedEndDate, BigDecimal plannedBudget,
                 Integer progress, StageStatus status, String rejectionComment,
                 OffsetDateTime createdAt, OffsetDateTime updatedAt) {
        this.id = id;
        this.project = project;
        this.title = title;
        this.description = description;
        this.displayOrder = displayOrder;
        this.plannedStartDate = plannedStartDate;
        this.plannedEndDate = plannedEndDate;
        this.plannedBudget = plannedBudget;
        this.progress = progress;
        this.status = status;
        this.rejectionComment = rejectionComment;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() { return id; }
    public Project getProject() { return project; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public Integer getDisplayOrder() { return displayOrder; }
    public LocalDate getPlannedStartDate() { return plannedStartDate; }
    public LocalDate getPlannedEndDate() { return plannedEndDate; }
    public BigDecimal getPlannedBudget() { return plannedBudget; }
    public Integer getProgress() { return progress; }
    public StageStatus getStatus() { return status; }
    public String getRejectionComment() { return rejectionComment; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public OffsetDateTime getUpdatedAt() { return updatedAt; }

    public void updateDetails(String title, String description,
                              LocalDate plannedStartDate, LocalDate plannedEndDate,
                              BigDecimal plannedBudget, Integer progress, OffsetDateTime updatedAt) {
        if (status == StageStatus.PENDING_APPROVAL || status == StageStatus.APPROVED) {
            throw new IllegalStateException("Impossible de modifier l'étape depuis le statut " + status + ".");
        }
        this.title = title;
        this.description = description;
        this.plannedStartDate = plannedStartDate;
        this.plannedEndDate = plannedEndDate;
        this.plannedBudget = plannedBudget;
        this.progress = progress;
        if (status == StageStatus.REJECTED || (status == StageStatus.TODO && progress > 0)) {
            this.status = StageStatus.IN_PROGRESS;
            this.rejectionComment = null;
        }
        this.updatedAt = updatedAt;
    }

    public void submitForApproval(OffsetDateTime updatedAt) {
        requireStatus(StageStatus.IN_PROGRESS, "soumettre l'étape à validation");
        if (progress != 100) {
            throw new IllegalStateException("L'étape doit être terminée à 100 % avant sa soumission.");
        }
        this.status = StageStatus.PENDING_APPROVAL;
        this.updatedAt = updatedAt;
    }

    public void approve(OffsetDateTime updatedAt) {
        requireStatus(StageStatus.PENDING_APPROVAL, "valider l'étape");
        this.status = StageStatus.APPROVED;
        this.progress = 100;
        this.rejectionComment = null;
        this.updatedAt = updatedAt;
    }

    public void reject(String comment, OffsetDateTime updatedAt) {
        requireStatus(StageStatus.PENDING_APPROVAL, "refuser l'étape");
        this.status = StageStatus.REJECTED;
        this.rejectionComment = comment;
        this.updatedAt = updatedAt;
    }

    private void requireStatus(StageStatus expected, String action) {
        if (status != expected) {
            throw new IllegalStateException("Impossible de " + action + " depuis le statut " + status + ".");
        }
    }
}
