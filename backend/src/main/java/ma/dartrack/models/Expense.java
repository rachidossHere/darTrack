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
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;
import ma.dartrack.models.Project;
import ma.dartrack.models.Stage;

@Entity
@Table(name = "expenses")
public class Expense {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stage_id")
    private Stage stage;

    @Column(nullable = false, length = 160)
    private String label;

    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false)
    private LocalDate expenseDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private ExpenseCategory category;

    @Column(length = 160)
    private String provider;

    @Column(length = 100)
    private String reference;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private PaymentStatus paymentStatus;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    protected Expense() {
    }

    public Expense(UUID id, Project project, Stage stage, String label, BigDecimal amount, LocalDate expenseDate,
                   ExpenseCategory category, String provider, String reference, PaymentStatus paymentStatus,
                   OffsetDateTime createdAt, OffsetDateTime updatedAt) {
        this.id = id;
        this.project = project;
        this.stage = stage;
        this.label = label;
        this.amount = amount;
        this.expenseDate = expenseDate;
        this.category = category;
        this.provider = provider;
        this.reference = reference;
        this.paymentStatus = paymentStatus;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() { return id; }
    public Project getProject() { return project; }
    public Stage getStage() { return stage; }
    public String getLabel() { return label; }
    public BigDecimal getAmount() { return amount; }
    public LocalDate getExpenseDate() { return expenseDate; }
    public ExpenseCategory getCategory() { return category; }
    public String getProvider() { return provider; }
    public String getReference() { return reference; }
    public PaymentStatus getPaymentStatus() { return paymentStatus; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public OffsetDateTime getUpdatedAt() { return updatedAt; }

    public void updateDetails(Stage stage, String label, BigDecimal amount, LocalDate expenseDate,
                              ExpenseCategory category, String provider, String reference,
                              PaymentStatus paymentStatus, OffsetDateTime updatedAt) {
        this.stage = stage;
        this.label = label;
        this.amount = amount;
        this.expenseDate = expenseDate;
        this.category = category;
        this.provider = provider;
        this.reference = reference;
        this.paymentStatus = paymentStatus;
        this.updatedAt = updatedAt;
    }
}