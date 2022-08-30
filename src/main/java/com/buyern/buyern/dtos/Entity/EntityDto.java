package com.buyern.buyern.dtos.Entity;

import com.buyern.buyern.Enums.State;
import com.buyern.buyern.Models.Entity.EntityType;
import com.buyern.buyern.Models.Location.Location;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
public class EntityDto implements Serializable {
    private Long id;
    private String entityId;
    private String name;
    private State.Entity state;
    private EntityType type;
    private Location location;
    private String registererId;
    private boolean isLive;
    private String logo;
    private String logoDark;
    private String email;
    private String phone;
    private boolean registeredWithGovt;
    private String about;
    private LocalDate dateEstablished;
    private Long parentId;
    private boolean hq;
    private Date timeRegistered;
    private String color;
    private String colorDark;
    private String coverImage;
    private String coverImageDark;

    @Data
    public static class EntityRegistrationDto implements Serializable {
        @NotBlank(message = "Entity Name can't be empty")
        private String name;
        @NotNull(message = "type not specified")
        private EntityType type;
        @NotNull(message = "entity location not specified")
        private Location location;
        @NotBlank(message = "email not specified")
        private String email;
        @NotBlank(message = "phone not specified")
        private String phone;
        @NotNull(message = "is registered with govt not specified")
        private boolean registeredWithGovt;
        private String about;
        @NotNull(message = "date est. not specified")
        private LocalDate dateEstablished;
        private Long parentId;
        private boolean hq;
        private String color;
        private String colorDark;

        public static class EntityImageRegistrationDto{
            private String uid;
            @NotBlank(message = "logo not specified")
            private MultipartFile logo;
            private MultipartFile logoDark;
            private MultipartFile coverImage;
            private MultipartFile coverImageDark;
        }
    }
}
