package com.buyern.buyern.Controllers;

import com.buyern.buyern.Enums.State;
import com.buyern.buyern.Services.HelperService;
import com.buyern.buyern.dtos.ResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("helper")
public class HelperController {
    final HelperService helperService;

    public HelperController(HelperService helperService) {
        this.helperService = helperService;
    }

    @GetMapping("entityTypes")
    private ResponseEntity<ResponseDTO> getEntityTypes() {
        return helperService.getEntityTypes();
    }

    @GetMapping("entityTypes/tools")
    private ResponseEntity<ResponseDTO> getEntityTypeTools(@RequestParam Long typeId) {
        return helperService.getEntityTypeTools(typeId);
    }

    @GetMapping("tools")
    private ResponseEntity<ResponseDTO> getTools() {
        return helperService.getTools();
    }

    @GetMapping("countries")
    private ResponseEntity<ResponseDTO> getCountriesList() {
        return helperService.getCountriesList();
    }

    @GetMapping("country")
    private ResponseEntity<ResponseDTO> getCountry(@RequestParam(name = "id") Long countryId) {
        return helperService.getCountryById(countryId);
    }

    @GetMapping("country/byCountryCode")
    private ResponseEntity<ResponseDTO> getCountryByCountryCode(@RequestParam(name = "countryCode") String countryCode) {
        return helperService.getCountryByCountryCode(countryCode);
    }

    @GetMapping("states")
    private ResponseEntity<ResponseDTO> getStatesList() {
        return helperService.getStatesList();
    }

    @GetMapping("states/byCountryId")
    private ResponseEntity<ResponseDTO> getStatesByCountryId(@RequestParam Long countryId) {
        return helperService.getStatesByCountryId(countryId);
    }

    @GetMapping("states/byCountryCode")
    private ResponseEntity<ResponseDTO> getStatesByCountryCode(@RequestParam String countryCode) {
        return helperService.getStatesByCountryCode(countryCode);
    }

    @GetMapping("state")
    private ResponseEntity<ResponseDTO> getState(@RequestParam(name = "id") Long stateId) {
        return helperService.getStateById(stateId);
    }

    @GetMapping("city")
    private ResponseEntity<ResponseDTO> getCity(@RequestParam(name = "id") Long cityId) {
        return helperService.getCity(cityId);
    }

    @GetMapping("cities")
    private ResponseEntity<ResponseDTO> getCities() {
        return helperService.getCities();
    }

    @GetMapping("cities/byStateId")
    private ResponseEntity<ResponseDTO> getCitiesByStateId(@RequestParam(name = "stateId") Long stateId) {
        return helperService.getCitiesByStateId(stateId);
    }

    @GetMapping("cities/byCountryCode/byStateCode")
    private ResponseEntity<ResponseDTO> byCountryCodeAndStateCode(@RequestParam(name = "countryCode") String countryCode, @RequestParam(name = "stateCode") String stateCode) {
        return helperService.byCountryCodeAndStateCode(countryCode, stateCode);
    }

    @GetMapping("cities/byCountryId")
    private ResponseEntity<ResponseDTO> getCitiesByCountryId(@RequestParam(name = "countryId") Long countryId) {
        return helperService.getCitiesByCountryId(countryId);
    }

    @GetMapping("cities/byCountryCode")
    private ResponseEntity<ResponseDTO> getCitiesByCountryCode(@RequestParam(name = "countryCode") String countryCode) {
        return helperService.getCitiesByCountryCode(countryCode);
    }

    @GetMapping("entity/states")
    private ResponseEntity<ResponseDTO> getEntityStates() {
        State.Entity[] entityStates = State.Entity.values();
        return ResponseEntity.ok(ResponseDTO.builder().code("00").message("SUCCESS").data(entityStates).build());
    }

}
