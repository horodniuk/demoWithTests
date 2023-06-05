package com.example.demowithtests.dto;

import com.example.demowithtests.domain.Address;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.Instant;
import java.util.Date;

//@Accessors(chain = true)
@Setter
@Getter
public class AddressDto {

    public Long id;

    public Boolean addressHasActive = Boolean.TRUE;

    public String country;

    public String city;

    public String street;

    //todo: dfhgjkdfhg Jira - 5544
    public Date date = Date.from(Instant.now());
}
