package ma.dartrack.services.impl;

import java.io.IOException;
import ma.dartrack.models.*;
import ma.dartrack.repositories.*;
import ma.dartrack.services.*;
import java.io.InputStream;
import ma.dartrack.models.*;
import ma.dartrack.repositories.*;
import ma.dartrack.services.*;
import java.nio.file.Files;
import ma.dartrack.models.*;
import ma.dartrack.repositories.*;
import ma.dartrack.services.*;
import java.nio.file.Path;
import ma.dartrack.models.*;
import ma.dartrack.repositories.*;
import ma.dartrack.services.*;
import java.nio.file.StandardCopyOption;
import ma.dartrack.models.*;
import ma.dartrack.repositories.*;
import ma.dartrack.services.*;
import java.time.OffsetDateTime;
import ma.dartrack.models.*;
import ma.dartrack.repositories.*;
import ma.dartrack.services.*;
import java.time.ZoneOffset;
import ma.dartrack.models.*;
import ma.dartrack.repositories.*;
import ma.dartrack.services.*;
import java.util.List;
import ma.dartrack.models.*;
import ma.dartrack.repositories.*;
import ma.dartrack.services.*;
import java.util.UUID;
import ma.dartrack.models.*;
import ma.dartrack.repositories.*;
import ma.dartrack.services.*;
import ma.dartrack.models.Activity;
import ma.dartrack.repositories.ActivityRepository;
import ma.dartrack.models.ActivityType;
import ma.dartrack.common.ResourceNotFoundException;
import ma.dartrack.models.Project;
import ma.dartrack.repositories.ProjectRepository;
import ma.dartrack.models.Stage;
import ma.dartrack.repositories.StageRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
public class DocumentServiceImpl implements DocumentService {

    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024;
    private static final List<String> ALLOWED_TYPES = List.of("image/jpeg", "image/png", "image/webp", "application/pdf");

    private final DocumentRepository documentRepository;
    private final ProjectRepository projectRepository;
    private final StageRepository stageRepository;
    private final ActivityRepository activityRepository;
    private final Path storagePath;

    public DocumentServiceImpl(DocumentRepository documentRepository, ProjectRepository projectRepository,
                           StageRepository stageRepository, ActivityRepository activityRepository,
                           @Value("${app.storage-path:./storage}") String storagePath) {
        this.documentRepository = documentRepository;
        this.projectRepository = projectRepository;
        this.stageRepository = stageRepository;
        this.activityRepository = activityRepository;
        this.storagePath = Path.of(storagePath).toAbsolutePath().normalize();
    }

    @Transactional(readOnly = true)
    public List<DocumentResponse> findAll(UUID projectId) {
        Project project = getProject(projectId);
        return documentRepository.findAllByProjectOrderByAddedAtDesc(project).stream().map(this::toResponse).toList();
    }

    public DocumentResponse upload(UUID projectId, UUID stageId, DocumentType type, MultipartFile file) {
        validate(file);
        Project project = getProject(projectId);
        Stage stage = stageId == null ? null : stageRepository.findById(stageId)
            .orElseThrow(() -> new ResourceNotFoundException("Étape introuvable : " + stageId));
        if (stage != null && !stage.getProject().getId().equals(project.getId())) throw new IllegalArgumentException("L'étape n'appartient pas au projet.");
        UUID id = UUID.randomUUID();
        String storageName = id + extension(file.getOriginalFilename());
        Path target = storagePath.resolve(storageName).normalize();
        if (!target.getParent().equals(storagePath)) throw new IllegalArgumentException("Nom de fichier invalide.");
        try {
            Files.createDirectories(storagePath);
            try (InputStream input = file.getInputStream()) {
                Files.copy(input, target, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException exception) {
            throw new IllegalStateException("Impossible d'enregistrer le document.", exception);
        }
        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
        Document document = new Document(id, project, stage, file.getOriginalFilename(), storageName,
                file.getContentType(), file.getSize(), target.toString(), type, now);
        Document saved = documentRepository.save(document);
        activityRepository.save(new Activity(UUID.randomUUID(), project, stage, ActivityType.DOCUMENT_ADDED,
                "Document ajouté : " + saved.getOriginalName(), now));
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public DownloadedDocument download(UUID id) {
        Document document = getDocument(id);
        try {
            Resource resource = new UrlResource(Path.of(document.getLocalPath()).toUri());
            if (!resource.exists() || !resource.isReadable()) throw new ResourceNotFoundException("Fichier introuvable : " + id);
            return new DownloadedDocument(resource, document.getOriginalName(), document.getMimeType());
        } catch (IOException exception) {
            throw new ResourceNotFoundException("Fichier introuvable : " + id);
        }
    }

    public void delete(UUID id) {
        Document document = getDocument(id);
        try { Files.deleteIfExists(Path.of(document.getLocalPath())); }
        catch (IOException exception) { throw new IllegalStateException("Impossible de supprimer le fichier.", exception); }
        documentRepository.delete(document);
    }

    private void validate(MultipartFile file) {
        if (file == null || file.isEmpty()) throw new IllegalArgumentException("Un fichier est obligatoire.");
        if (file.getSize() > MAX_FILE_SIZE) throw new IllegalArgumentException("La taille maximale est de 10 Mo.");
        if (file.getContentType() == null || !ALLOWED_TYPES.contains(file.getContentType())) {
            throw new IllegalArgumentException("Format accepté : JPG, PNG, WebP ou PDF.");
        }
    }

    private String extension(String name) {
        if (name == null || !name.contains(".")) return "";
        return name.substring(name.lastIndexOf('.')).replaceAll("[^a-zA-Z0-9.]", "");
    }

    private Project getProject(UUID id) { return projectRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Projet introuvable : " + id)); }
    private Document getDocument(UUID id) { return documentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Document introuvable : " + id)); }
    private DocumentResponse toResponse(Document document) { return new DocumentResponse(document.getId(), document.getProject().getId(), document.getStage() == null ? null : document.getStage().getId(), document.getOriginalName(), document.getMimeType(), document.getSize(), document.getType(), document.getAddedAt()); }

    public record DownloadedDocument(Resource resource, String filename, String mimeType) {}
}