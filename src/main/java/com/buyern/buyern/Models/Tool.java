package com.buyern.buyern.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.*;
import java.util.UUID;

@Entity(name = "tools")
@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class Tool {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;
    @Column(unique = true)
    private String name;
    private String about;

    public Tool(Long id) {
        this.id = id;
    }
}
