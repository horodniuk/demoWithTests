package com.example.demowithtests.domain;


import lombok.*;
import org.hibernate.annotations.Type;

import jakarta.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String type;

    @Lob
    @Column(name = "image_data", columnDefinition = "bytea")
    public byte[] imageData;

    @ToString.Exclude
    @OneToOne(mappedBy = "image")
    private Passport passport;
}
