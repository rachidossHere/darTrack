package ma.dartrack.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

public record StageResponse(
        UUID id,
        UUID projectId,
        String title,
        String description,
        Integer displayOrder,
        LocalDate plannedStartDate,
        LocalDate plannedEndDate,
        BigDecimal plannedBudget,
        Integer progress,
        StageStatus status,
        String rejectionComment,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt) {
}