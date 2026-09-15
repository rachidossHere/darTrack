package ma.dartrack.models;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import ma.dartrack.repositories.ActivityRepository;
import ma.dartrack.repositories.ProjectRepository;
import ma.dartrack.common.ResourceNotFoundException;
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

    private ma.dartrack.services.ProjectService projectService;

    @BeforeEach
    void setUp() {
        projectService = new ma.dartrack.services.impl.ProjectServiceImpl(projectRepository, activityRepository);
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
}