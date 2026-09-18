package ma.dartrack.models;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;
import ma.dartrack.models.Project;
import ma.dartrack.models.ProjectStatus;
import ma.dartrack.models.ProjectType;
import org.junit.jupiter.api.Test;

class StageWorkflowTest {

    @Test
    void stageCanBeSubmittedThenApproved() {
        Stage stage = createStage(StageStatus.IN_PROGRESS, 100);

        stage.submitForApproval(now());
        assertThat(stage.getStatus()).isEqualTo(StageStatus.PENDING_APPROVAL);

        stage.approve(now());
        assertThat(stage.getStatus()).isEqualTo(StageStatus.APPROVED);
        assertThat(stage.getProgress()).isEqualTo(100);
    }

    @Test
    void approvalIsRejectedOutsidePendingApproval() {
        Stage stage = createStage(StageStatus.IN_PROGRESS, 60);

        assertThatThrownBy(() -> stage.approve(now()))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("IN_PROGRESS");
    }

    @Test
    void submissionRequiresFullProgress() {
        Stage stage = createStage(StageStatus.IN_PROGRESS, 60);

        assertThatThrownBy(() -> stage.submitForApproval(now()))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("100 %");
    }

    @Test
    void firstProgressUpdateStartsTodoStage() {
        Stage stage = createStage(StageStatus.TODO, 0);

        stage.updateDetails("Étape", "Début des travaux", null, null,
                new BigDecimal("10000"), 1, now());

        assertThat(stage.getStatus()).isEqualTo(StageStatus.IN_PROGRESS);
        assertThat(stage.getProgress()).isEqualTo(1);
    }

    @Test
    void pendingStageCannotBeEdited() {
        Stage stage = createStage(StageStatus.IN_PROGRESS, 100);
        stage.submitForApproval(now());

        assertThatThrownBy(() -> stage.updateDetails("Étape", null, null, null,
                new BigDecimal("10000"), 80, now()))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("PENDING_APPROVAL");
    }

    @Test
    void rejectedStageReturnsToInProgressWhenEdited() {
        Stage stage = createStage(StageStatus.REJECTED, 100);

        stage.updateDetails("Étape corrigée", null, null, null,
                new BigDecimal("10000"), 90, now());

        assertThat(stage.getStatus()).isEqualTo(StageStatus.IN_PROGRESS);
        assertThat(stage.getProgress()).isEqualTo(90);
        assertThat(stage.getRejectionComment()).isNull();
    }

    @Test
    void rejectionRequiresPendingApprovalAndStoresComment() {
        Stage stage = createStage(StageStatus.IN_PROGRESS, 100);

        assertThatThrownBy(() -> stage.reject("Travail à reprendre", now()))
                .isInstanceOf(IllegalStateException.class);

        stage.submitForApproval(now());
        stage.reject("Travail à reprendre", now());

        assertThat(stage.getStatus()).isEqualTo(StageStatus.REJECTED);
        assertThat(stage.getRejectionComment()).isEqualTo("Travail à reprendre");
    }

    private Stage createStage(StageStatus status, int progress) {
        OffsetDateTime now = now();
        Project project = new Project(UUID.randomUUID(), "Projet", null, ProjectType.APARTMENT,
                "Adresse", "Rabat", new BigDecimal("100000"), null, null,
                ProjectStatus.IN_PROGRESS, now, now);
        return new Stage(UUID.randomUUID(), project, "Étape", null, 0, null, null,
                new BigDecimal("10000"), progress, status, null, now, now);
    }

    private OffsetDateTime now() {
        return OffsetDateTime.now(ZoneOffset.UTC);
    }
}
