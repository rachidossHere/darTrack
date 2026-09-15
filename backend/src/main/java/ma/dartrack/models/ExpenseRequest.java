package ma.dartrack.models;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ExpenseRequest(
        @NotBlank String label,
        @NotNull @DecimalMin(value = "0.01") BigDecimal amount,
        @NotNull LocalDate expenseDate,
        @NotNull ExpenseCategory category,
        String provider,
        String reference,
        @NotNull PaymentStatus paymentStatus,
        UUID stageId) {
}