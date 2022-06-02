package com.buyern.buyern.Services;

import com.azure.storage.blob.BlobClient;
import com.buyern.buyern.Enums.BuyernEntityType;
import com.buyern.buyern.Models.Entity;
import com.buyern.buyern.Models.EntityPreferences;
import com.buyern.buyern.Models.EntityRegistrationStep;
import com.buyern.buyern.Repositories.EntityRegistrationStepRepository;
import com.buyern.buyern.Repositories.EntityRepository;
import com.buyern.buyern.dtos.EntityDto;
import com.buyern.buyern.dtos.ResponseDTO;
import com.buyern.buyern.exception.RecordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Service
public class EntityRegistrationService {
    final EntityRepository entityRepository;
    final FileService fileService;
    @Autowired
    EntityRegistrationStepRepository entityRegistrationStepRepository;

    public EntityRegistrationService(EntityRepository entityRepository, FileService fileService) {
        this.entityRepository = entityRepository;
        this.fileService = fileService;
    }

    public ResponseEntity<ResponseDTO> getEntity(String by, String value) {
        Optional<Entity> entity = switch (by) {
            case "REGISTERER_ID" -> entityRepository.findByRegistererId(BuyernEntityType.BUSINESS + "/" + value);
            case "PARENT_ID" -> entityRepository.findByParentId(value);
            default -> entityRepository.findByEntityId(value);
        };
        if (entity.isEmpty())
            throw new RecordNotFoundException("Entity does not exist");
        return ResponseEntity.ok(ResponseDTO.builder().code("00").message("SUCCESS").data(EntityDto.create(entity.get())).build());
    }

    public ResponseEntity<ResponseDTO> register1(String registererEntityId, String entityName) {
        /* if registererEntityId is null then registererType is USER. get user id from header etc*/
        Entity entity = new Entity();
        entity.setName(entityName);
        entity.setRegistererId(BuyernEntityType.BUSINESS + "/" + registererEntityId);
//        entity.setParentId();
        EntityDto entityDto = EntityDto.create(entityRepository.save(entity));
        EntityRegistrationStep entityRegistrationStep = new EntityRegistrationStep();
        entityRegistrationStep.setEntityId(entityDto.getEntityId());
        entityRegistrationStep.setRegistrationStep(1);
        entityRegistrationStepRepository.save(entityRegistrationStep);
        return ResponseEntity.ok(ResponseDTO.builder().code("00").data(entityDto).build());
    }

    /**
     * <h3>Update entity registration current step count.</h3>
     *
     * @param entityId entity to update its step count
     * @param step     current step count
     */
    private EntityRegistrationStep updateStep(String entityId, int step) {
        Optional<EntityRegistrationStep> entityRegistrationStep = entityRegistrationStepRepository.findByEntityId(entityId);
        if (entityRegistrationStep.isEmpty())
            throw new RuntimeException("Entity has already been registered or entity does not exist");
        entityRegistrationStep.get().setRegistrationStep(step);
        return entityRegistrationStepRepository.save(entityRegistrationStep.get());
    }

    public ResponseEntity<ResponseDTO> register2(EntityDto entityDto) {
        Optional<Entity> entity = entityRepository.findByEntityId(entityDto.getEntityId());
        if (entity.isEmpty())
            throw new RecordNotFoundException("entity with registration id not found");
        entity.get().setAbout(entityDto.getAbout());
        entity.get().setType(entityDto.getType());
        entity.get().setRegisteredWithGovt(entityDto.isRegisteredWithGovt());
        entity.get().setDateEstablished(entityDto.getDateEstablished());
        entity.get().setEmail(entityDto.getEmail());
        entity.get().setPhone(entityDto.getPhone());
        entity.get().setHq(entityDto.isHq());
        entity.get().setCategory(entityDto.getCategory().toEntityCategory());
        Entity entity1 = entityRepository.save(entity.get());
        updateStep(entity1.getEntityId(), 2);
        return ResponseEntity.ok(ResponseDTO.builder().code("00").data(EntityDto.create(entity1)).build());
    }

    /**
     * <h3>Register Entity Location
     *
     * @param entityDto entity dto must contain a well formatted location field
     * @return ResponseEntity containing entityDto</h3>
     */
    public ResponseEntity<ResponseDTO> register3(EntityDto entityDto) {
        Entity entity = getEntity(entityDto.getEntityId());
        if (entity.getLocation().getId() != null) {
            entityDto.getLocation().setId(entity.getLocation().getId());
        }
        entity.setLocation(entityDto.getLocation().toLocation());
        Entity entity1 = entityRepository.save(entity);
        updateStep(entity1.getEntityId(), 3);
        return ResponseEntity.ok(ResponseDTO.builder().code("00").message("SUCCESS").data(EntityDto.create(entity1)).build());
    }

    /**
     * <h3>register entity preferences</h3>
     *
     * @param color     base color for entity. a color from the logo is preferred
     * @param colorDark base color for entity in dark mode. a color from the logo is preferred
     * @param logo      entity logo. supported
     * @return ResponseEntity containing new entityDto
     */
    public ResponseEntity<ResponseDTO> register4(String color, String colorDark, String registrationId, MultipartFile logo, MultipartFile logoDark, MultipartFile coverImage, MultipartFile coverImageDark) {
        if (logo != null) verifyMediaType(logo.getContentType());
        if (logoDark != null) verifyMediaType(logoDark.getContentType());
        if (coverImage != null) verifyMediaType(coverImage.getContentType());
        if (coverImageDark != null) verifyMediaType(coverImageDark.getContentType());

        Entity entity = getEntity(registrationId);
        if (entity.getPreferences() == null)
            entity.setPreferences(new EntityPreferences());
        if (color != null)
            entity.getPreferences().setColor(color);
        if (colorDark != null)
            entity.getPreferences().setColorDark(colorDark);
        if (logo != null)
            entity.getPreferences().setLogo(uploadToEntityBucket(logo, entity.getEntityId(), "logo"));
        if (logoDark != null)
            entity.getPreferences().setLogoDark(uploadToEntityBucket(logoDark, entity.getEntityId(), "logoDark"));
        if (coverImage != null)
            entity.getPreferences().setCoverImage(uploadToEntityBucket(coverImage, entity.getEntityId(), "coverImage"));
        if (coverImageDark != null)
            entity.getPreferences().setCoverImageDark(uploadToEntityBucket(coverImageDark, entity.getEntityId(), "coverImageDark"));
        Entity entity1 = entityRepository.save(entity);
        updateStep(entity1.getEntityId(), 4);
        return ResponseEntity.ok(ResponseDTO.builder().code("00").message("SUCCESS").data(EntityDto.create(entity1)).build());
    }

    private void verifyMediaType(String type) {
        if (Objects.equals(type, MediaType.IMAGE_GIF_VALUE) || Objects.equals(type, MediaType.IMAGE_JPEG_VALUE) || Objects.equals(type, MediaType.IMAGE_PNG_VALUE))
            return;
        throw new IllegalArgumentException("Unsupported Image Format: only " + MediaType.IMAGE_JPEG_VALUE + ", " + MediaType.IMAGE_PNG_VALUE + " and " + MediaType.IMAGE_GIF_VALUE + " are supported");
    }

    private String uploadToEntityBucket(MultipartFile file, String entityId, String newName) {
        try {
            String[] name = Objects.requireNonNull(file.getContentType()).split("/");
            BlobClient blobClient = fileService.blobClient(fileService.entitiesContainerClient, entityId + "/" + newName + "." + name[name.length - 1]);
            blobClient.upload(file.getInputStream(), file.getSize(), true);
            return blobClient.getBlobUrl();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            throw new RuntimeException("cant convert file to stream");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new RuntimeException("Error uploading to storage server");
        }
    }

    /**
     * <h3>Register Entity Location</h3>
     *
     * @param entityId entity's unique UUID
     * @return Entity
     */
    public Entity getEntity(String entityId) {
        Optional<Entity> entity = entityRepository.findByEntityId(entityId);
        if (entity.isEmpty())
            throw new RecordNotFoundException("entity with registration id not found");
        return entity.get();
    }

    /**
     * <h3>Initiate entity</h3>
     * Finalize all entity settings, activate entity, send to main server and delete from this server
     */
    private void initiateEntity(String entityId) {
//TODO: get entity required tools
//TODO: initialize all tools
//TODO: delete entity from entity registration step table
    }

    private void initiatePermissions(String entityId) {

    }

    private void initiateFinance(String entityId) {

    }

    private void initiateInventory(String entityId) {

    }

    private void initiateCustomerCare(String entityId) {

    }

    private void initiateCustomerManager(String entityId) {

    }

    private void initiateAssetManager(String entityId) {

    }

    private void initiateEmployeeManager(String entityId) {

    }

    private void initiateDeliveryManager(String entityId) {

    }
}
