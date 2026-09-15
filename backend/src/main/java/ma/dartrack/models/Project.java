package ma.dartrack.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import ma.dartrack.models.Activity;
import ma.dartrack.models.Document;
import ma.dartrack.models.Expense;
import ma.dartrack.models.Stage;

@Entity
@Table(name = "projects")
public class Project {

    @Id
    private UUID id;

    @Column(nullable = false, length = 160)
    private String name;

    @Column(columnDefinition = "text")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private ProjectType type;

    @Column(nullable = false, length = 255)
    private String address;

    @Column(nullable = false, length = 100)
    private String city;

    @Column(name = "initial_budget", nullable = false, precision = 14, scale = 2)
    private BigDecimal initialBudget;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "estimated_end_date")
    private LocalDate estimatedEndDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private ProjectStatus status;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Stage> stages = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Expense> expenses = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Document> documents = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Activity> activities = new ArrayList<>();

    protected Project() {
    }

    public Project(UUID id, String name, String description, ProjectType type, String address, String city,
                   BigDecimal initialBudget, LocalDate startDate, LocalDate estimatedEndDate, ProjectStatus status,
                   OffsetDateTime createdAt, OffsetDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.address = address;
        this.city = city;
        this.initialBudget = initialBudget;
        this.startDate = startDate;
        this.estimatedEndDate = estimatedEndDate;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public ProjectType getType() { return type; }
    public String getAddress() { return address; }
    public String getCity() { return city; }
    public BigDecimal getInitialBudget() { return initialBudget; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEstimatedEndDate() { return estimatedEndDate; }
    public ProjectStatus getStatus() { return status; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public OffsetDateTime getUpdatedAt() { return updatedAt; }
    public List<Stage> getStages() { return stages; }
    public List<Expense> getExpenses() { return expenses; }
    public List<Document> getDocuments() { return documents; }
    public List<Activity> getActivities() { return activities; }

    public void updateDetails(String name, String description, ProjectType type, String address, String city,
                              BigDecimal initialBudget, LocalDate startDate, LocalDate estimatedEndDate,
                              ProjectStatus status, OffsetDateTime updatedAt) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.address = address;
        this.city = city;
        this.initialBudget = initialBudget;
        this.startDate = startDate;
        this.estimatedEndDate = estimatedEndDate;
        this.status = status;
        this.updatedAt = updatedAt;
    }
}