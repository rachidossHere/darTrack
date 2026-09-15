package ma.dartrack.repositories;

import java.util.List;
import java.util.UUID;
import ma.dartrack.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, UUID> {
    List<Expense> findAllByProjectOrderByExpenseDateDesc(Project project);
}