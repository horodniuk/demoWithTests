package com.example.demowithtests.dto;

import com.example.demowithtests.domain.Address;
import lombok.*;
import lombok.experimental.Accessors;

import java.time.Instant;
import java.util.Date;

//@Accessors(chain = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {

    private Long id;

    private Boolean addressHasActive = Boolean.TRUE;

    private String country;

    private String city;

    private String street;

    //todo: dfhgjkdfhg Jira - 5544
    private Date date = Date.from(Instant.now());
}
