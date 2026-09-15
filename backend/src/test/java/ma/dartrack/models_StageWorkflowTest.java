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
        Stage stage = createStage(StageStatus.IN_PROGRESS, 60);

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
                .hasMessageContaining("PENDING_APPROVAL");
    }

    @Test
    void rejectionRequiresPendingApprovalAndStoresComment() {
        Stage stage = createStage(StageStatus.IN_PROGRESS, 60);

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