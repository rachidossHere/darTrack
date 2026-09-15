package ma.dartrack.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import java.util.UUID;
import ma.dartrack.models.Project;
import ma.dartrack.models.Stage;

@Entity
@Table(name = "documents")
public class Document {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stage_id")
    private Stage stage;

    @Column(name = "original_name", nullable = false, length = 255)
    private String originalName;

    @Column(name = "storage_name", nullable = false, length = 255)
    private String storageName;

    @Column(name = "mime_type", nullable = false, length = 100)
    private String mimeType;

    @Column(nullable = false)
    private Long size;

    @Column(name = "local_path", nullable = false, length = 500)
    private String localPath;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private DocumentType type;

    @Column(name = "added_at", nullable = false)
    private OffsetDateTime addedAt;

    protected Document() {
    }

    public Document(UUID id, Project project, Stage stage, String originalName, String storageName,
                    String mimeType, Long size, String localPath, DocumentType type, OffsetDateTime addedAt) {
        this.id = id;
        this.project = project;
        this.stage = stage;
        this.originalName = originalName;
        this.storageName = storageName;
        this.mimeType = mimeType;
        this.size = size;
        this.localPath = localPath;
        this.type = type;
        this.addedAt = addedAt;
    }

    public UUID getId() { return id; }
    public Project getProject() { return project; }
    public Stage getStage() { return stage; }
    public String getOriginalName() { return originalName; }
    public String getStorageName() { return storageName; }
    public String getMimeType() { return mimeType; }
    public Long getSize() { return size; }
    public String getLocalPath() { return localPath; }
    public DocumentType getType() { return type; }
    public OffsetDateTime getAddedAt() { return addedAt; }
}