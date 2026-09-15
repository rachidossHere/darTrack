package ma.dartrack.models;

import java.time.OffsetDateTime;
import java.util.UUID;

public record DocumentResponse(UUID id, UUID projectId, UUID stageId, String originalName, String mimeType,
                               long size, DocumentType type, OffsetDateTime addedAt) {
}