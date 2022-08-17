package com.buyern.buyern.Models.Location;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.*;
import java.util.Date;

@Entity(name = "states")
@Data
public class State {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//  @Column(nullable = false, columnDefinition = "mediumint(8) unsigned NOT NULL AUTO_INCREMENT")
    @Column(nullable = false)
    private Long id;
    @Column(columnDefinition = "varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL")
    private String name;
    @Column(name = "country_id", columnDefinition = "mediumint(8) unsigned NOT NULL")
    private Long countryId;
    @Column(name = "country_code", columnDefinition = "char(2) COLLATE utf8mb4_unicode_ci NOT NULL")
    private String countryCode;
    @Column(name = "fips_code", columnDefinition = "varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL")
    private String fipsCode;
    @Column(columnDefinition = "varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL")
    private String iso2;
    @Column(columnDefinition = "varchar(191) COLLATE utf8mb4_unicode_ci DEFAULT NULL")
    private String type;
    @Column(columnDefinition = "decimal(10,8) DEFAULT NULL")
    private Double latitude;
    @Column(columnDefinition = "decimal(11,8) DEFAULT NULL")
    private Double longitude;
    @CreationTimestamp
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    @Column(name = "created_at", columnDefinition = "timestamp NULL DEFAULT NULL")
    private Date createdAt;
    @UpdateTimestamp
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    @Column(name = "updated_at", columnDefinition = "timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private Date updatedAt;
    @Column(columnDefinition = "tinyint(1) NOT NULL DEFAULT '1'")
    private boolean flag;
    @Column(columnDefinition = "varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'Rapid API GeoDB Cities'")
    private String wikiDataId;
}