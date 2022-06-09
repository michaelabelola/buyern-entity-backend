package com.buyern.buyern.Services;

import com.azure.storage.blob.BlobClient;
import com.buyern.buyern.Enums.BuyernEntityType;
import com.buyern.buyern.Models.Entity.*;
import com.buyern.buyern.Repositories.Entity.EntityPresetRepository;
import com.buyern.buyern.Repositories.Entity.EntityRegistrationStepRepository;
import com.buyern.buyern.Repositories.Entity.EntityRepository;
import com.buyern.buyern.dtos.Entity.EntityDto;
import com.buyern.buyern.dtos.ResponseDTO;
import com.buyern.buyern.exception.RecordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EntityRegistrationService {
    final EntityRepository entityRepository;
    final FileService fileService;
    @Autowired
    EntityRegistrationStepRepository entityRegistrationStepRepository;
    @Autowired
    EntityPresetRepository entityPresetRepository;

    public EntityRegistrationService(EntityRepository entityRepository, FileService fileService) {
        this.entityRepository = entityRepository;
        this.fileService = fileService;
    }

    public ResponseEntity<ResponseDTO> getEntity(String by, String value) {
        Optional<Entity> entity = switch (by) {
            case "REGISTERER_ID" -> entityRepository.findByRegistererId(BuyernEntityType.BUSINESS + "/" + value);
            case "PARENT_ID" -> entityRepository.findByParentId(value);
            default -> Optional.empty();
        };
        if (entity.isEmpty())
            throw new RecordNotFoundException("Entity does not exist");
        return ResponseEntity.ok(ResponseDTO.builder().code("00").message("SUCCESS").data(EntityDto.create(entity.get())).build());
    }

    public ResponseEntity<ResponseDTO> register1(String registererEntityId, String entityName) {
        if (registererEntityId.isEmpty()) throw new RuntimeException("registerer not specified");
        if (entityName.isEmpty()) throw new RuntimeException("entityName cant be empty");
        /* if registererEntityId is null then registererType is USER. get user id from header etc*/
        Entity entity = new Entity();
        entity.setName(entityName);
        entity.setRegistererId(BuyernEntityType.BUSINESS + "/" + registererEntityId);
//        entity.setParentId();
        EntityDto entityDto = EntityDto.create(entityRepository.save(entity));
        EntityRegistrationStep entityRegistrationStep = new EntityRegistrationStep();
        entityRegistrationStep.setEntityId(entityDto.getId());
        entityRegistrationStep.setRegistrationStep(1);
        entityRegistrationStepRepository.save(entityRegistrationStep);
        return ResponseEntity.ok(ResponseDTO.builder().code("00").data(entityDto).build());
    }

    /**
     * <h3>get entity registration current step count.</h3>
     *
     * @param entityId entity
     */
    private EntityRegistrationStep getRegistrationProgress(Long entityId) {
        Optional<EntityRegistrationStep> entityRegistrationStep = entityRegistrationStepRepository.findByEntityId(entityId);
        if (entityRegistrationStep.isEmpty())
            throw new RuntimeException("Entity has already been registered or entity does not exist");
        else return entityRegistrationStep.get();
    }

    public ResponseEntity<ResponseDTO> register2(EntityDto entityDto) {
        EntityRegistrationStep entityRegistrationStep = getRegistrationProgress(entityDto.getId());
        Optional<Entity> entity = entityRepository.findById(entityDto.getId());
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
        entityRegistrationStep.setRegistrationStep(2);
        entityRegistrationStepRepository.save(entityRegistrationStep);
        return ResponseEntity.ok(ResponseDTO.builder().code("00").data(EntityDto.create(entity1)).build());
    }

    /**
     * <h3>Register Entity Location
     *
     * @param entityDto entity dto must contain a well formatted location field
     * @return ResponseEntity containing entityDto</h3>
     */
    public ResponseEntity<ResponseDTO> register3(EntityDto entityDto) {
        EntityRegistrationStep entityRegistrationStep = getRegistrationProgress(entityDto.getId());
        Entity entity = getEntity(entityDto.getId());
        if (entity.getLocation() != null) {
            entityDto.getLocation().setId(entity.getLocation().getId());
        }
        entity.setLocation(entityDto.getLocation().toLocation());
        Entity entity1 = entityRepository.save(entity);
        entityRegistrationStep.setRegistrationStep(3);
        entityRegistrationStepRepository.save(entityRegistrationStep);
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
    public ResponseEntity<ResponseDTO> register4(String color, String colorDark, Long entityId, MultipartFile logo, MultipartFile logoDark, MultipartFile coverImage, MultipartFile coverImageDark) {
        EntityRegistrationStep entityRegistrationStep = getRegistrationProgress(entityId);
        if (logo != null) verifyMediaType(logo.getContentType());
        if (logoDark != null) verifyMediaType(logoDark.getContentType());
        if (coverImage != null) verifyMediaType(coverImage.getContentType());
        if (coverImageDark != null) verifyMediaType(coverImageDark.getContentType());

        Entity entity = getEntity(entityId);
        if (entity.getPreferences() == null)
            entity.setPreferences(new EntityPreferences());
        if (color != null)
            entity.getPreferences().setColor(color);
        if (colorDark != null)
            entity.getPreferences().setColorDark(colorDark);
        if (logo != null)
            entity.getPreferences().setLogo(uploadToEntityBucket(logo, entity.getId(), "logo"));
        if (logoDark != null)
            entity.getPreferences().setLogoDark(uploadToEntityBucket(logoDark, entity.getId(), "logoDark"));
        if (coverImage != null)
            entity.getPreferences().setCoverImage(uploadToEntityBucket(coverImage, entity.getId(), "coverImage"));
        if (coverImageDark != null)
            entity.getPreferences().setCoverImageDark(uploadToEntityBucket(coverImageDark, entity.getId(), "coverImageDark"));
        Entity entity1 = entityRepository.save(entity);
        entityRegistrationStep.setRegistrationStep(4);
        entityRegistrationStepRepository.save(entityRegistrationStep);
        return ResponseEntity.ok(ResponseDTO.builder().code("00").message("SUCCESS").data(EntityDto.create(entity1)).build());
    }

    /**
     * <h3>Verify that uploaded media type is an image</h3>
     *
     * @param type uploaded media type
     */
    private void verifyMediaType(String type) {
        if (Objects.equals(type, MediaType.IMAGE_GIF_VALUE) || Objects.equals(type, MediaType.IMAGE_JPEG_VALUE) || Objects.equals(type, MediaType.IMAGE_PNG_VALUE))
            return;
        throw new IllegalArgumentException("Unsupported Image Format: only " + MediaType.IMAGE_JPEG_VALUE + ", " + MediaType.IMAGE_PNG_VALUE + " and " + MediaType.IMAGE_GIF_VALUE + " are supported");
    }

    private String uploadToEntityBucket(MultipartFile file, Long entityId, String newName) {
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
     * @param entityId entity's unique Long
     * @return Entity
     */
    public Entity getEntity(Long entityId) {
        Optional<Entity> entity = entityRepository.findById(entityId);
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

    /**
     * <h3>finalize entity registration</h3>
     * Finalize all entity settings, activate entity, send to main server and delete from this server
     */
    public ResponseEntity<ResponseDTO> finalizeRegistration(Long entityId) {
        Optional<Entity> entity = entityRepository.findById(entityId);
        if (entity.isEmpty()) throw new RecordNotFoundException("entity not found");
        EntityCategory entityCategory = entity.get().getCategory();

        List<EntityPreset> entityPresets = entityPresetRepository.findByCategory_IdIsOrderByTool_NameAsc(entityCategory.getId());

        if (entityPresets.isEmpty()) throw new RecordNotFoundException("no presets available for this category");

        entityPresets.forEach(entityPreset -> {
            if (entityPreset.getTool().getId() == 1L) {
//                finance
                initializeFinance(entityId);
            } else if (entityPreset.getTool().getId() == 2L) {
//                inventory
                initializeInventory(entityId);
            } else if (entityPreset.getTool().getId() == 3L) {
//                customer care
                initializeCustomerCare(entityId);
            } else if (entityPreset.getTool().getId() == 4L) {
//                customer manager
                initializeCustomerManager(entityId);
            } else if (entityPreset.getTool().getId() == 5L) {
//                asset manager
                initializeAssetsManager(entityId);
            } else if (entityPreset.getTool().getId() == 6L) {
//                employee manager
                initializeStakeholdersManager(entityId);
            } else if (entityPreset.getTool().getId() == 7L) {
//                permissions / roles
                initializePermissions(entityId);
            } else if (entityPreset.getTool().getId() == 8L) {
//                delivery
                initializeDeliveryManager(entityId);
            } else if (entityPreset.getTool().getId() == 9L) {
//                location
                initializeFinance(entityId);
            } else if (entityPreset.getTool().getId() == 10L) {
//                order
                initializeOrdersManager(entityId);
            }
        });
//        Optional<EntityCategory> category = entityCategoryRepository.findById(categoryId);
//        if (category.isEmpty()) throw new RecordNotFoundException("Category doesn't exist");
//        ObjectMapper mapper = new ObjectMapper();
//        ObjectNode categoryObject = mapper.valueToTree(category.get());
//        categoryObject.set("tools", mapper.valueToTree(new ListMapper<EntityPreset, Tool>().map(entityPresets, EntityPreset::getTool)));
        return ResponseEntity.ok(ResponseDTO.builder().code("00").message("SUCCESS").data("initializing").build());


        //get entity from reg table, den get its category tools presets and create them.
//       create asset,
//       add asset to company assets also
//       call asset hq, type building
    }

    private void initializePermissions(Long entityId) {

    }

    private void initializeFinance(Long entityId) {
        //TODO:initialize on the finance server
    }

    private void initializeInventory(Long entityId) {

    }

    private void initializeCustomerCare(Long entityId) {

    }

    private void initializeCustomerManager(Long entityId) {

    }

    private void initializeAssetsManager(Long entityId) {

    }

    private void initializeStakeholdersManager(Long entityId) {

    }

    private void initializeDeliveryManager(Long entityId) {

    }

    private void initializeOrdersManager(Long entityId) {

    }

}
