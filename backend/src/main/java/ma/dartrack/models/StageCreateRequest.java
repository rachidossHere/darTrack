package ma.dartrack.models;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Max;
import java.math.BigDecimal;
import java.time.LocalDate;

public record StageCreateRequest(
        @NotBlank String title,
        String description,
        @NotNull @Min(0) Integer displayOrder,
        LocalDate plannedStartDate,
        LocalDate plannedEndDate,
        @NotNull @DecimalMin(value = "0.0", inclusive = true) BigDecimal plannedBudget,
        @NotNull @Min(0) @Max(100) Integer progress) {
}