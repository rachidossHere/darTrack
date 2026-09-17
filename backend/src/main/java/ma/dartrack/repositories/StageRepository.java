package ma.dartrack.repositories;

import java.util.List;
import java.util.UUID;
import ma.dartrack.models.Project;
import ma.dartrack.models.Stage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StageRepository extends JpaRepository<Stage, UUID> {
    List<Stage> findAllByProjectOrderByDisplayOrder(Project project);
}