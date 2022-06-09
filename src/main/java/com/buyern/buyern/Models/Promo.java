package com.buyern.buyern.Models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "promos")
@Data
public class Promo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;
    private String name;
    private Long entityId;
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private Date startDate;
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private Date endDate;

}