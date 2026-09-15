package ma.dartrack.models;

import java.time.OffsetDateTime;
import java.util.UUID;

public record ActivityResponse(UUID id, UUID projectId, UUID stageId, ActivityType type,
                               String description, OffsetDateTime occurredAt) {
}