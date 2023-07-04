package com.example.demowithtests.dto;

import com.example.demowithtests.domain.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeReadDto {

    Integer id,

    @NotNull(message = "Name may not be null")
    @Size(min = 2, max = 32, message = "Name must be between 2 and 32 characters long")
    @Schema(description = "Name of an employee.", example = "Billy", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    private String country;

    @Email
    @NotNull
    private String email;

    private Set<AddressDto> addresses = new HashSet<>();

    //todo: dfhgjkdfhg Jira - 5544
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "Date response")
    public Date date = Date.from(Instant.now());

    private Gender gender;
}
