package ma.dartrack.config;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;
import ma.dartrack.models.Activity;
import ma.dartrack.repositories.ActivityRepository;
import ma.dartrack.models.ActivityType;
import ma.dartrack.models.Expense;
import ma.dartrack.models.ExpenseCategory;
import ma.dartrack.repositories.ExpenseRepository;
import ma.dartrack.models.PaymentStatus;
import ma.dartrack.models.Project;
import ma.dartrack.repositories.ProjectRepository;
import ma.dartrack.models.ProjectStatus;
import ma.dartrack.models.ProjectType;
import ma.dartrack.models.Stage;
import ma.dartrack.repositories.StageRepository;
import ma.dartrack.models.StageStatus;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class DevelopmentDataSeeder implements CommandLineRunner {

    private static final UUID PROJECT_ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    private final ProjectRepository projectRepository;
    private final StageRepository stageRepository;
    private final ExpenseRepository expenseRepository;
    private final ActivityRepository activityRepository;

    public DevelopmentDataSeeder(ProjectRepository projectRepository, StageRepository stageRepository,
                                  ExpenseRepository expenseRepository, ActivityRepository activityRepository) {
        this.projectRepository = projectRepository;
        this.stageRepository = stageRepository;
        this.expenseRepository = expenseRepository;
        this.activityRepository = activityRepository;
    }

    @Override
    public void run(String... args) {
        if (projectRepository.existsById(PROJECT_ID)) {
            return;
        }

        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
        Project project = new Project(
                PROJECT_ID,
                "Rénovation appartement Agdal",
                "Rénovation complète d'un appartement familial à Agdal.",
                ProjectType.APARTMENT,
                "Quartier Agdal",
                "Rabat",
                new BigDecimal("180000.00"),
                LocalDate.of(2026, 1, 15),
                LocalDate.of(2026, 8, 30),
                ProjectStatus.IN_PROGRESS,
                now,
                now);
        projectRepository.save(project);

        Stage demolition = saveStage(project, "Démolition", 0, new BigDecimal("18000"), 100,
                StageStatus.APPROVED, now);
        Stage plumbing = saveStage(project, "Plomberie", 1, new BigDecimal("28000"), 70,
                StageStatus.IN_PROGRESS, now);
        Stage electricity = saveStage(project, "Électricité", 2, new BigDecimal("32000"), 45,
                StageStatus.IN_PROGRESS, now);
        saveStage(project, "Peinture", 3, new BigDecimal("24000"), 0, StageStatus.TODO, now);
        Stage kitchen = saveStage(project, "Cuisine", 4, new BigDecimal("50000"), 10,
                StageStatus.TODO, now);

        expenseRepository.save(new Expense(UUID.randomUUID(), project, demolition, "Dépose des cloisons",
                new BigDecimal("12000"), LocalDate.of(2026, 1, 20), ExpenseCategory.LABOR,
                "Entreprise Atlas", null, PaymentStatus.PAID, now, now));
        expenseRepository.save(new Expense(UUID.randomUUID(), project, plumbing, "Tuyauterie et raccords",
                new BigDecimal("14500"), LocalDate.of(2026, 3, 12), ExpenseCategory.MATERIALS,
                "Bati Matériaux", "FAC-2026-0312", PaymentStatus.PAID, now, now));
        expenseRepository.save(new Expense(UUID.randomUUID(), project, electricity, "Câblage électrique",
                new BigDecimal("9800"), LocalDate.of(2026, 4, 8), ExpenseCategory.MATERIALS,
                "Elec Rabat", null, PaymentStatus.PENDING, now, now));

        activityRepository.save(new Activity(UUID.randomUUID(), project, null, ActivityType.PROJECT_CREATED,
                "Projet de rénovation créé", now.minusDays(120)));
        activityRepository.save(new Activity(UUID.randomUUID(), project, demolition, ActivityType.STAGE_STATUS_CHANGED,
                "L'étape Démolition a été validée", now.minusDays(80)));
        activityRepository.save(new Activity(UUID.randomUUID(), project, electricity, ActivityType.EXPENSE_ADDED,
                "Dépense ajoutée : Câblage électrique", now.minusDays(12)));
        activityRepository.save(new Activity(UUID.randomUUID(), project, plumbing, ActivityType.STAGE_STATUS_CHANGED,
                "L'étape Plomberie est en cours", now.minusDays(8)));
    }

    private Stage saveStage(Project project, String title, int order, BigDecimal budget, int progress,
                            StageStatus status, OffsetDateTime now) {
        return stageRepository.save(new Stage(UUID.randomUUID(), project, title, null, order,
                LocalDate.of(2026, 1, 20).plusMonths(order),
                LocalDate.of(2026, 2, 20).plusMonths(order), budget, progress, status, null, now, now));
    }
}