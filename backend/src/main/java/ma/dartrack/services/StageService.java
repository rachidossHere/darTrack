package ma.dartrack.services;

import java.util.List;
import java.util.UUID;
import ma.dartrack.models.StageCreateRequest;
import ma.dartrack.models.StageRejectRequest;
import ma.dartrack.models.StageResponse;
import ma.dartrack.models.StageUpdateRequest;

public interface StageService {
    List<StageResponse> findAllByProject(UUID projectId);
    StageResponse findById(UUID id);
    StageResponse create(UUID projectId, StageCreateRequest request);
    StageResponse update(UUID id, StageUpdateRequest request);
    void delete(UUID id);
    StageResponse submit(UUID id);
    StageResponse approve(UUID id);
    StageResponse reject(UUID id, StageRejectRequest request);
}
