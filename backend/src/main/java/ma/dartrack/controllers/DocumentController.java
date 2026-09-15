package ma.dartrack.controllers;

import jakarta.validation.constraints.NotNull;
import ma.dartrack.models.*;
import ma.dartrack.services.*;
import ma.dartrack.models.*;
import ma.dartrack.services.*;
import ma.dartrack.models.*;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Validated
@RestController
@RequestMapping("/api/v1")
public class DocumentController {
    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) { this.documentService = documentService; }

    @GetMapping("/projects/{projectId}/documents")
    public List<DocumentResponse> findAll(@PathVariable UUID projectId) { return documentService.findAll(projectId); }

    @PostMapping(value = "/projects/{projectId}/documents", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DocumentResponse> upload(@PathVariable UUID projectId, @RequestParam(required = false) UUID stageId,
                                                    @RequestParam @NotNull DocumentType type, @RequestParam MultipartFile file) {
        DocumentResponse response = documentService.upload(projectId, stageId, type, file);
        return ResponseEntity.created(URI.create("/api/v1/documents/" + response.id())).body(response);
    }

    @GetMapping("/documents/{id}/download")
    public ResponseEntity<Resource> download(@PathVariable UUID id) {
        DocumentService.DownloadedDocument document = documentService.download(id);
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(document.mimeType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.attachment().filename(document.filename()).build().toString())
                .body(document.resource());
    }

    @DeleteMapping("/documents/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) { documentService.delete(id); return ResponseEntity.noContent().build(); }
}