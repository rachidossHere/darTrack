package ma.dartrack.services;

import java.util.List;
import java.util.UUID;
import ma.dartrack.models.DocumentResponse;
import ma.dartrack.models.DocumentType;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface DocumentService {
    List<DocumentResponse> findAll(UUID projectId);
    DocumentResponse upload(UUID projectId, UUID stageId, DocumentType type, MultipartFile file);
    DownloadedDocument download(UUID id);
    void delete(UUID id);
    record DownloadedDocument(Resource resource, String filename, String mimeType) {}
}
