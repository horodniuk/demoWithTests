package com.example.demowithtests.dto;

import com.example.demowithtests.util.annotations.dto.CountryRightFormed;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @CountryRightFormed
    public String country;

    public String city;

    public String street;

    //todo: dfhgjkdfhg Jira - 5544
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "Date response")
    public Date date = Date.from(Instant.now());
}
