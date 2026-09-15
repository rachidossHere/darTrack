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
import java.time.OffsetDateTime;
import java.util.UUID;
import ma.dartrack.models.Project;
import ma.dartrack.models.Stage;

@Entity
@Table(name = "activities")
public class Activity {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stage_id")
    private Stage stage;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 40)
    private ActivityType type;

    @Column(nullable = false, columnDefinition = "text")
    private String description;

    @Column(name = "occurred_at", nullable = false)
    private OffsetDateTime occurredAt;

    protected Activity() {
    }

    public Activity(UUID id, Project project, Stage stage, ActivityType type, String description,
                    OffsetDateTime occurredAt) {
        this.id = id;
        this.project = project;
        this.stage = stage;
        this.type = type;
        this.description = description;
        this.occurredAt = occurredAt;
    }

    public UUID getId() { return id; }
    public Project getProject() { return project; }
    public Stage getStage() { return stage; }
    public ActivityType getType() { return type; }
    public String getDescription() { return description; }
    public OffsetDateTime getOccurredAt() { return occurredAt; }
}