package ma.dartrack.repositories;

import java.util.List;
import java.util.UUID;

import ma.dartrack.models.Activity;
import ma.dartrack.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<Activity, UUID> {
    List<Activity> findAllByProjectOrderByOccurredAtDesc(Project project);
}