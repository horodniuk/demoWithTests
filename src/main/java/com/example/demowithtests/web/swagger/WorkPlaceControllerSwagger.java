package com.example.demowithtests.web.swagger;


import com.example.demowithtests.dto.workspace.WorkPlaceDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;


@Tag(name = "WorkPlace", description = "WorkPlace API")
public interface WorkPlaceControllerSwagger {

    @Operation(summary = "Save a work place.",
            description = "Create request to save a work place.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request")})
    WorkPlaceDto saveWorkPlace(@RequestBody @Valid WorkPlaceDto workPlaceDto);

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "404", description = "WorkPlace not Found")})
    @Operation(summary = "This is endpoint returned a workplayce by his id.",
               description = "Create request to read a workplayce by id", tags = {"WorkPlace"})
    WorkPlaceDto getWorkPlaceById(
            @PathVariable
            @Parameter(description = "WorkPlace ID", example = "1", required = true)
            Integer id);
}
