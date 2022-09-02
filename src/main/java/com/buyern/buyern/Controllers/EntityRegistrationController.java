package com.buyern.buyern.Controllers;

import com.azure.core.annotation.BodyParam;
import com.azure.core.annotation.QueryParam;
import com.buyern.buyern.Configs.CustomAuthority;
import com.buyern.buyern.Models.Entity.Entity;
import com.buyern.buyern.Services.EntityRegistrationService;
import com.buyern.buyern.dtos.Entity.EntityDto;
import com.buyern.buyern.dtos.ResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/entity")
public class EntityRegistrationController {
    final EntityRegistrationService entityRegistrationService;
Logger logger = LoggerFactory.getLogger(EntityRegistrationController.class);
    public EntityRegistrationController(EntityRegistrationService entityRegistrationService) {
        this.entityRegistrationService = entityRegistrationService;
    }

    @PostMapping("registration/details")
    private ResponseEntity<Entity> register1(@Valid @RequestBody EntityDto.EntityRegistrationDto entity, Principal principal, Authentication authentication) {
//        logger.info(authentication.getPrincipal().toString());
        List<CustomAuthority> authorities = (List<CustomAuthority>) authentication.getAuthorities().stream().toList();
//        logger.info(String.valueOf(authorities));
        return entityRegistrationService.register(entity, principal);
    }

    @PostMapping("registration/images")
    private Map<String, Object> registerImages(@BodyParam("id") Long id, @Nullable @ModelAttribute MultipartFile logo,
                                               @Nullable @ModelAttribute MultipartFile logoDark, @Nullable @ModelAttribute MultipartFile coverImage,
                                               @Nullable @ModelAttribute MultipartFile coverImageDark) {
//       preferences
        return entityRegistrationService.registerImages(id, logo, logoDark, coverImage, coverImageDark);
    }

    @PostMapping("registration/finalize")
    private ResponseEntity<ResponseDTO> finalizeRegistration(Long entityId) {
        return entityRegistrationService.finalizeRegistration(entityId);
    }
    @DeleteMapping("")
    private ResponseEntity<Object> deleteEntity(@Valid @NotEmpty(message = "entity Id not specified") @QueryParam("id") Long id) {
        return entityRegistrationService.deleteEntity(id);
    }

    @GetMapping("")
    private ResponseEntity<Entity> getEntity(@QueryParam("id") Long id) {
        return entityRegistrationService.getEntity(id);
    }
    @GetMapping("/registration/existsByName")
    private ResponseEntity<Boolean> isNameTaken(@QueryParam("name") String name) {
        return entityRegistrationService.checkEntityNameAvailability(name);
    }

    private ResponseEntity<ResponseDTO> getEntities() {
        return null;
    }

    private ResponseEntity<ResponseDTO> getChildEntities(@QueryParam("id") Long id) {
        return entityRegistrationService.getChildEntities(id);
    }
}
