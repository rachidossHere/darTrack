package ma.dartrack.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

public record ProjectResponse(
        UUID id,
        String name,
        String description,
        ProjectType type,
        String address,
        String city,
        BigDecimal initialBudget,
        LocalDate startDate,
        LocalDate estimatedEndDate,
        ProjectStatus status,
        int progress,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt) {
}