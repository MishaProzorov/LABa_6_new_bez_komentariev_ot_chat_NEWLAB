package com.example.SunriseSunset.controller;

import com.example.SunriseSunset.dto.LocationDto;
import com.example.SunriseSunset.service.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/locations")
@Tag(name = "Location Controller", description = "API for managing locations")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @Operation(summary = "Create a new location", responses = {
            @ApiResponse(responseCode = "200", description = "Location created successfully",
                    content = @Content(schema = @Schema(implementation = LocationDto.class)))})
    @PostMapping
    public ResponseEntity<LocationDto> createLocation(@RequestBody LocationDto dto) {
        return ResponseEntity.ok(locationService.createLocation(dto));
    }

    @Operation(summary = "Get location by ID", responses = {
            @ApiResponse(responseCode = "200", description = "Location found",
                    content = @Content(schema = @Schema(implementation = LocationDto.class))),
            @ApiResponse(responseCode = "404", description = "Location not found")})
    @GetMapping("/{id}")
    public ResponseEntity<LocationDto> getLocationById(
            @Parameter(description = "ID of the location to be retrieved") @PathVariable Integer id) {
        LocationDto dto = locationService.getLocationById(id);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }


    @Operation(summary = "Get all locations", responses = {
            @ApiResponse(responseCode = "200", description = "List of all locations",
                    content = @Content(schema = @Schema(implementation = LocationDto.class)))})
    @GetMapping("/all")
    public ResponseEntity<List<LocationDto>> getAllLocations() {
        return ResponseEntity.ok(locationService.getAllLocations());
    }

    @Operation(summary = "Update location", responses = {
            @ApiResponse(responseCode = "200", description = "Location updated successfully",
                    content = @Content(schema = @Schema(implementation = LocationDto.class))),
            @ApiResponse(responseCode = "404", description = "Location not found")})
    @PutMapping("/{id}")
    public ResponseEntity<LocationDto> updateLocation(
            @Parameter(description = "ID of the location to be updated") @PathVariable Integer id,
            @RequestBody LocationDto dto) {
        LocationDto updatedDto = locationService.updateLocation(id, dto);
        return updatedDto != null ? ResponseEntity.ok(updatedDto) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Delete location", responses = {
            @ApiResponse(responseCode = "204", description = "Location deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Location not found")})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocation(
            @Parameter(description = "ID of the location to be deleted") @PathVariable Integer id) {
        locationService.deleteLocation(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Bulk create locations", responses = {
            @ApiResponse(responseCode = "200", description = "Locations created successfully",
                    content = @Content(schema = @Schema(implementation = LocationDto.class)))})
    @PostMapping("/bulk")
    public ResponseEntity<List<LocationDto>> bulkCreateLocations(@RequestBody List<LocationDto> dtos) {
        return ResponseEntity.ok(locationService.bulkCreateLocations(dtos));
    }

}