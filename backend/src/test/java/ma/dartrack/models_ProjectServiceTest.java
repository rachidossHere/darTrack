package ma.dartrack.models;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import ma.dartrack.common.ResourceNotFoundException;
import ma.dartrack.repositories.ActivityRepository;
import ma.dartrack.repositories.DocumentRepository;
import ma.dartrack.repositories.ProjectRepository;
import ma.dartrack.repositories.StageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private ActivityRepository activityRepository;

    @Mock
    private StageRepository stageRepository;

    @Mock
    private DocumentRepository documentRepository;

    private ma.dartrack.services.ProjectService projectService;

    @BeforeEach
    void setUp() {
        projectService = new ma.dartrack.services.impl.ProjectServiceImpl(projectRepository, activityRepository, stageRepository, documentRepository);
    }

    @Test
    void createSavesProjectAndCreationActivity() {
        ProjectCreateRequest request = new ProjectCreateRequest(
                "Projet test", "Description", ProjectType.APARTMENT, "Adresse", "Rabat",
                new BigDecimal("100000"), LocalDate.of(2026, 1, 1),
                LocalDate.of(2026, 12, 31), ProjectStatus.DRAFT);
        when(projectRepository.save(any(Project.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ProjectResponse response = projectService.create(request);

        assertThat(response.name()).isEqualTo("Projet test");
        assertThat(response.progress()).isZero();
        verify(activityRepository).save(any());
    }

    @Test
    void findByIdRaisesNotFoundForUnknownProject() {
        UUID id = UUID.randomUUID();
        when(projectRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> projectService.findById(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining(id.toString());
    }

    @Test
    void deleteRemovesStagesAndDocumentsForProject() {
        UUID id = UUID.randomUUID();
        Project project = new Project(
                id,
                "Projet test",
                "Description",
                ProjectType.APARTMENT,
                "Adresse",
                "Rabat",
                new BigDecimal("100000"),
                LocalDate.of(2026, 1, 1),
                LocalDate.of(2026, 12, 31),
                ProjectStatus.DRAFT,
                OffsetDateTime.now(),
                OffsetDateTime.now()
        );

        Stage stage = new Stage(
                UUID.randomUUID(),
                project,
                "Étape 1",
                "Desc",
                0,
                LocalDate.of(2026, 1, 5),
                LocalDate.of(2026, 1, 10),
                new BigDecimal("2500"),
                0,
                StageStatus.TODO,
                null,
                OffsetDateTime.now(),
                OffsetDateTime.now()
        );

        Document document = new Document(
                UUID.randomUUID(),
                project,
                null,
                "photo.png",
                "storage-name.png",
                "image/png",
                1024L,
                "./storage/storage-name.png",
                DocumentType.SITE_PHOTO,
                OffsetDateTime.now()
        );

        when(projectRepository.findById(id)).thenReturn(Optional.of(project));
        when(stageRepository.findAllByProjectOrderByDisplayOrder(project)).thenReturn(List.of(stage));
        when(documentRepository.findAllByProjectOrderByAddedAtDesc(project)).thenReturn(List.of(document));

        projectService.delete(id);

        verify(stageRepository).deleteAll(List.of(stage));
        verify(documentRepository).deleteAll(List.of(document));
        verify(projectRepository).delete(project);
    }
}