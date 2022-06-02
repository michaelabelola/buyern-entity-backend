package com.buyern.buyern.Models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.Date;

@Entity
@Data
@RequiredArgsConstructor
public class EntityRegistrationStep {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;
    private String EntityId;
    private int registrationStep;
    @CreationTimestamp
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private Date timeStarted;
    @CreationTimestamp
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private Date timeCompleted;
}
