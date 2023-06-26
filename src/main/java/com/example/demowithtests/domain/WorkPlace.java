package com.example.demowithtests.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "workplaces")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkPlace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "air_condition")
    private Boolean airCondition = Boolean.TRUE;

    @Column(name = "coffee_machine")
    private Boolean coffeeMachine = Boolean.TRUE;
}
