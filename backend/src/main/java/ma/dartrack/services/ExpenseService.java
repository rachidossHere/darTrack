package ma.dartrack.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import ma.dartrack.models.ExpenseRequest;
import ma.dartrack.models.ExpenseResponse;

public interface ExpenseService {
    List<ExpenseResponse> findAll(UUID projectId);
    ExpenseResponse create(UUID projectId, ExpenseRequest request);
    ExpenseResponse update(UUID id, ExpenseRequest request);
    void delete(UUID id);
    BigDecimal total(UUID projectId);
}
