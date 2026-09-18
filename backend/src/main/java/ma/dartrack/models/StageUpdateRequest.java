package ma.dartrack.models;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public record StageUpdateRequest(
        @NotBlank String title,
        String description,
        LocalDate plannedStartDate,
        LocalDate plannedEndDate,
        @NotNull @DecimalMin(value = "0.0", inclusive = true) BigDecimal plannedBudget,
        @NotNull @Min(0) @Max(100) Integer progress) {
}
