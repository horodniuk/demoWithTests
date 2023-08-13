package com.example.demowithtests.dto;

import com.example.demowithtests.util.annotations.dto.CountryRightFormed;
import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.Date;

//@Accessors(chain = true)

public record AddressDto (


    @Schema(name = "Address ID", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    Long id,

    @NotNull
    @CountryRightFormed
    @Schema(name = "Country name", description = "Name of the country use ISO 3166 two-letter country codes",
            example = "UA", requiredMode = Schema.RequiredMode.REQUIRED)
    String country,

    @Schema(name = "Address нas active", description = "Indicate whether the address is active now", example = "true")
    Boolean addressHasActive,

    @Schema(name = "City name", example = "Kharkiv")
    String city,

    @Schema(name = "Street name", example = "Street Shevchenko 192")
    String street,

    //todo: dfhgjkdfhg Jira - 5544
    @Schema(name = "Today's date", example = "2023-12-12")
    Date date
){
        public AddressDto {
            date = Date.from(Instant.now());
        }
}
