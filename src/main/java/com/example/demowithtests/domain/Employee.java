package com.example.demowithtests.domain;

import com.example.demowithtests.util.annotations.entity.Name;
import com.example.demowithtests.util.annotations.entity.ToLowerCase;
import lombok.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@Builder
@EqualsAndHashCode(exclude = {"passport", "addresses", "employeeWorkPlaces"})
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    @Name
    private String name;

    @Column(name = "country")
    @NotBlank
    private String country;

    @Column(name = "email")
    @ToLowerCase
    private String email;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id")
    private Set<Address> addresses = new HashSet<>();

    @ToString.Exclude
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "passport_id", referencedColumnName = "id")
    private Passport passport;

    @ToString.Exclude
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<EmployeeWorkPlace> employeeWorkPlaces = new HashSet<>();
}
