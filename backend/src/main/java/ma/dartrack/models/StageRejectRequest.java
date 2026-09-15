package ma.dartrack.models;

import jakarta.validation.constraints.NotBlank;

public record StageRejectRequest(@NotBlank String comment) {
}