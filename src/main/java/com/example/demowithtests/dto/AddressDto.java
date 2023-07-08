package com.example.demowithtests.dto;

import com.example.demowithtests.util.annotations.dto.CountryRightFormed;

import java.time.Instant;
import java.util.Date;

//@Accessors(chain = true)

public record AddressDto (

    Long id,

    @CountryRightFormed
    String country,

    Boolean addressHasActive,


    String city,

    String street,

    //todo: dfhgjkdfhg Jira - 5544
    Date date
){
        public AddressDto {
            date = Date.from(Instant.now());
        }
}
