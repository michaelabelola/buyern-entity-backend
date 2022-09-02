package com.buyern.buyern.Services;

import com.buyern.buyern.Models.Entity.*;
import com.buyern.buyern.Models.Entity.Entity;
import com.buyern.buyern.Models.User.User;
import com.buyern.buyern.Repositories.Entity.EntityDetailRepository;
import com.buyern.buyern.Repositories.Entity.EntityPreferenceRepository;
import com.buyern.buyern.Repositories.Entity.EntityRepository;
import com.buyern.buyern.Repositories.LocationRepository;
import com.buyern.buyern.Repositories.UserRepository;
import com.buyern.buyern.dtos.Entity.EntityDto;
import com.buyern.buyern.dtos.ResponseDTO;
import com.buyern.buyern.exception.RecordNotFoundException;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
@Data
public class EntityRegistrationService {
    final UserRepository userRepository;
    final EntityRepository entityRepository;
    final EntityDetailRepository entityDetailRepository;
    final EntityPreferenceRepository entityPreferenceRepository;
    final LocationRepository locationRepository;
    final FileService fileService;
    Logger logger = LoggerFactory.getLogger(EntityRegistrationService.class);

    private Entity fetchEntityById(Long id) {
        return entityRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("Entity not found"));
    }

    private List<Entity> fetchChildrenEntityByParentId(Long id) {
        return entityRepository.findAllByParentId(id);
    }

    public ResponseEntity<ResponseDTO> getChildEntities(Long id) {
        return ResponseEntity.ok(ResponseDTO.builder().code("00").message("SUCCESS").data(fetchChildrenEntityByParentId(id)).build());
    }

    public ResponseEntity<Entity> getEntity(Long id) {
        return ResponseEntity.ok(fetchEntityById(id));
    }

    public ResponseEntity<Boolean> checkEntityNameAvailability(String name) {
        return ResponseEntity.ok(entityRepository.existsByNameAllIgnoreCase(name));
    }

    /**
     * <h3>Entity registration step 1. Entity Details</h3>
     * return entity
     */
    @Transactional
    public ResponseEntity<Entity> register(EntityDto.EntityRegistrationDto entityDto, Principal principal) {
        User user = userRepository.findByUid(principal.getName()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        logger.info(user.toString());
//        Location savedLocation = locationRepository.save(entityDto.getLocation());


        Entity entity = new Entity();
        entity.setName(entityDto.getName());
//        entity.setState(entityDto.getState());
        entity.setType(entityDto.getType());
        entity.setLocation(entityDto.getLocation());
        entity.setLive(false);
        /**
         * <h3>Save Entity Save Entity</h3>
         * */
        Entity savedEntity = entityRepository.save(entity);


        EntityDetail entityDetail = new EntityDetail();
        entityDetail.setId(savedEntity.getId());
        entityDetail.setEmail(entityDto.getEmail());
        entityDetail.setPhone(entityDto.getPhone());
        entityDetail.setRegisteredWithGovt(entityDto.isRegisteredWithGovt());
        entityDetail.setRegistererId(user.getUid());
        entityDetail.setAbout(entityDto.getAbout());
        entityDetail.setDateEstablished(entityDto.getDateEstablished());
        //TODO: check if user has permission to register a child company under this company
        if (entityDto.getParentId() != null) {
            Optional<Entity> parent = entityRepository.findById(entityDto.getParentId());
            parent.ifPresent(entityDetail::setParent);
        }
        entityDetail.setHq(entityDto.isHq());
        /**
         * <h3>Save Entity Details</h3>
         * */
        entityDetailRepository.save(entityDetail);
        EntityPreference entityPreference = new EntityPreference();
        entityPreference.setId(savedEntity.getId());
        entityPreference.setColor(entityDto.getColor());
        entityPreference.setColorDark(entityDto.getColorDark());
        /**
         * <h3>Save Entity Preferences</h3>
         * */
        entityPreferenceRepository.save(entityPreference);
        entityRepository.save(savedEntity);
        return ResponseEntity.ok(savedEntity);
    }

    /**
     * <h3>register entity preferences</h3>
     *
     * @param entityId       entity id
     * @param logo           entity logo. supported
     * @param logoDark       entity logo for dark mode. optional
     * @param coverImage     entity cover image
     * @param coverImageDark entity cover image for dark mode
     * @return ResponseEntity containing new entity
     */
    public Map<String, Object> registerImages(Long entityId, MultipartFile logo, MultipartFile logoDark, MultipartFile coverImage, MultipartFile coverImageDark) {
        if (logo != null) verifyMediaType(logo.getContentType());
        if (logoDark != null) verifyMediaType(logoDark.getContentType());
        if (coverImage != null) verifyMediaType(coverImage.getContentType());
        if (coverImageDark != null) verifyMediaType(coverImageDark.getContentType());

        Entity entity = fetchEntityById(entityId);
        EntityPreference entityPreference = entityPreferenceRepository.findById(entityId).orElseThrow(() -> new RecordNotFoundException("Entity preferences not found"));
        if (logo != null)
            entity.setLogo(uploadToEntityBucket(logo, entity.getId(), "logo"));
        if (logoDark != null)
            entity.setLogoDark(uploadToEntityBucket(logoDark, entity.getId(), "logoDark"));
        if (coverImage != null)
            entityPreference.setCoverImage(uploadToEntityBucket(coverImage, entity.getId(), "coverImage"));
        if (coverImageDark != null)
            entityPreference.setCoverImageDark(uploadToEntityBucket(coverImageDark, entity.getId(), "coverImageDark"));
        Entity entity1 = entityRepository.save(entity);
        return Map.of("entity", entity, "details", entityDetailRepository.findById(entityId).orElseThrow(() -> new RecordNotFoundException("entity details not found")), "preferences", entityPreference);
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
        String[] name = Objects.requireNonNull(file.getContentType()).split("/");
        return fileService.uploadToEntityContainer(String.valueOf(entityId), file, entityId + "/" + newName + "." + name[name.length - 1]);
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
        Entity entity = entityRepository.findById(entityId).orElseThrow(() -> new RecordNotFoundException("entity not found"));
//        EntityCategory entityCategory = entity.get().getCategory();
//        Optional<EntityCategory> category = entityCategoryRepository.findById(categoryId);
//        if (category.isEmpty()) throw new RecordNotFoundException("Category doesn't exist");
//        ObjectMapper mapper = new ObjectMapper();
//        ObjectNode categoryObject = mapper.valueToTree(category.get());
//        categoryObject.set("tools", mapper.valueToTree(new ListMapper<EntityPreset, Tool>().map(entityPresets, EntityPreset::getTool)));
        return ResponseEntity.ok(ResponseDTO.builder().code("00").message("SUCCESS").data("initializing").build());
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

    public ResponseEntity<Object> deleteEntity(Long entityId) {
        entityRepository.deleteById(entityId);
        entityDetailRepository.deleteById(entityId);
        entityPreferenceRepository.deleteById(entityId);
        return ResponseEntity.ok("deleted successfully");
    }
}
