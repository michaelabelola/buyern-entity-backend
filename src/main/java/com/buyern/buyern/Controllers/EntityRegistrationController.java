package com.buyern.buyern.Controllers;

import com.azure.core.annotation.BodyParam;
import com.azure.core.annotation.QueryParam;
import com.buyern.buyern.Services.EntityRegistrationService;
import com.buyern.buyern.dtos.Entity.EntityDto;
import com.buyern.buyern.dtos.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/entity")
public class EntityRegistrationController {
    final EntityRegistrationService entityRegistrationService;

    public EntityRegistrationController(EntityRegistrationService entityRegistrationService) {
        this.entityRegistrationService = entityRegistrationService;
    }

    @PostMapping("registration/step1")
    private ResponseEntity<ResponseDTO> register1(@NotBlank(message = "register id not specified") @NonNull @RequestParam("registererId") String registererId,
                                                  @NotBlank(message = "Entity name must be specified") @RequestParam("name") String entityName) {
        return entityRegistrationService.register1(registererId, entityName);
    }

    @PostMapping("registration/step2")
    private ResponseEntity<ResponseDTO> register2(@RequestBody EntityDto entityDto) {
        //Details
//        about, type, registeredWithGovt, dateEstablished, category, parentId, isHq
        return entityRegistrationService.register2(entityDto);
    }

    @PostMapping("registration/step3")
    private ResponseEntity<ResponseDTO> register3(@RequestBody EntityDto entityDto) {
        //location
        return entityRegistrationService.register3(entityDto);
    }

    @PostMapping("registration/step4")
    private ResponseEntity<ResponseDTO> register4(@Nullable @BodyParam("color") String color, @Nullable @BodyParam("colorDark") String colorDark,
                                                  @BodyParam("entityId") Long entityId, @Nullable @ModelAttribute MultipartFile logo,
                                                  @Nullable @ModelAttribute MultipartFile logoDark, @Nullable @ModelAttribute MultipartFile coverImage,
                                                  @Nullable @ModelAttribute MultipartFile coverImageDark) {
//       preferences
        return entityRegistrationService.register4(color, colorDark, entityId, logo, logoDark, coverImage, coverImageDark);
    }

    @PostMapping("registration/finalize")
    private ResponseEntity<ResponseDTO> finalizeRegistration(Long entityId) {
        return entityRegistrationService.finalizeRegistration(entityId);
    }

    @GetMapping("")
    private ResponseEntity<ResponseDTO> getEntity(@QueryParam("by") String by, @QueryParam("value") String value) {
        return entityRegistrationService.getEntity(by, value);
    }

    private ResponseEntity<ResponseDTO> getEntities() {
        return null;
    }
}
