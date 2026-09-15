package ma.dartrack.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

public record ExpenseResponse(
        UUID id,
        UUID projectId,
        UUID stageId,
        String label,
        BigDecimal amount,
        LocalDate expenseDate,
        ExpenseCategory category,
        String provider,
        String reference,
        PaymentStatus paymentStatus,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt) {
}