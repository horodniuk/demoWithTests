package com.example.demowithtests.domain;


import lombok.*;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(exclude = {"employee", "image"})
@Entity
public class Passport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String number;

    @Column(unique = true, nullable = false)
    private String series;

    @Builder.Default
    private String uuid = UUID.randomUUID().toString();

    private LocalDateTime expireDate;

    @Column(name = "canceled_employee_id")
    private Integer canceledEmployeeId;

    @Builder.Default
    private Boolean isHandled = Boolean.FALSE;

    @ToString.Exclude
    @OneToOne(mappedBy = "passport")
    private Employee employee;

    @ToString.Exclude
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private Image image;
}
