package ma.dartrack.models;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public record ProjectCreateRequest(
        @NotBlank String name,
        String description,
        @NotNull ProjectType type,
        @NotBlank String address,
        @NotBlank String city,
        @NotNull @DecimalMin(value = "0.0", inclusive = true) BigDecimal initialBudget,
        LocalDate startDate,
        LocalDate estimatedEndDate,
        @NotNull ProjectStatus status) {
}