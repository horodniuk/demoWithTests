package com.example.demowithtests.dto;

import java.time.Instant;
import java.util.Date;

//@Accessors(chain = true)

public record AddressDto (

    Long id,

    Boolean addressHasActive,

    String country,

    String city,

    String street,

    //todo: dfhgjkdfhg Jira - 5544
    Date date
){
        public AddressDto {
            date = Date.from(Instant.now());
        }
}
