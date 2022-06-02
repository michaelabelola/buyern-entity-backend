package com.buyern.buyern.Controllers;

import com.azure.core.annotation.BodyParam;
import com.azure.core.annotation.QueryParam;
import com.buyern.buyern.Services.EntityRegistrationService;
import com.buyern.buyern.dtos.EntityDto;
import com.buyern.buyern.dtos.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/entity")
public class EntityRegistrationController {
    final EntityRegistrationService entityService;

    public EntityRegistrationController(EntityRegistrationService entityService) {
        this.entityService = entityService;
    }

    @PostMapping("registration/step1")
    private ResponseEntity<ResponseDTO> register1(@RequestParam("registererId") String registererId, @RequestParam("name") String entityName) {
        return entityService.register1(registererId, entityName);
    }

    @PostMapping("registration/step2")
    private ResponseEntity<ResponseDTO> register2(@RequestBody EntityDto entityDto) {
        //Details
//        about, type, registeredWithGovt, dateEstablished, category, parentId, isHq
        return entityService.register2(entityDto);
    }

    @PostMapping("registration/step3")
    private ResponseEntity<ResponseDTO> register3(@RequestBody EntityDto entityDto) {
        //location
        return entityService.register3(entityDto);
    }

    @PostMapping("registration/step4")
    private ResponseEntity<ResponseDTO> register4(@Nullable @BodyParam("color") String color, @Nullable @BodyParam("colorDark") String colorDark,
                                                  @BodyParam("registrationId") String registrationId, @Nullable @ModelAttribute MultipartFile logo,
                                                  @Nullable @ModelAttribute MultipartFile logoDark, @Nullable @ModelAttribute MultipartFile coverImage,
                                                  @Nullable @ModelAttribute MultipartFile coverImageDark) {
//       preferences
        return entityService.register4(color, colorDark, registrationId, logo, logoDark, coverImage, coverImageDark);
    }

    @PostMapping("registration/step5")
    private ResponseEntity<ResponseDTO> register5() {
//       create asset,
//       add asset to company assets also
//       call asset hq, type building
        return null;
    }

    @GetMapping("")
    private ResponseEntity<ResponseDTO> getEntity(@QueryParam("by") String by, @QueryParam("value") String value) {
        return entityService.getEntity(by, value);
    }

    private ResponseEntity<ResponseDTO> getEntities() {
        return null;
    }
}
