package com.buyern.buyern.Services;

import com.buyern.buyern.Enums.EntityType;
import com.buyern.buyern.Helpers.ListMapper;
import com.buyern.buyern.Models.*;
import com.buyern.buyern.Repositories.*;
import com.buyern.buyern.dtos.AssetTypeDto;
import com.buyern.buyern.dtos.EntityCategoryDto;
import com.buyern.buyern.dtos.ResponseDTO;
import com.buyern.buyern.exception.RecordNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.util.RawValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class HelperService {
    final AssetTypeRepository assetTypeRepository;
    final EntityCategoryRepository entityCategoryRepository;
    final CountryRepository countryRepository;
    final StateRepository stateRepository;
    final CityRepository cityRepository;
    @Autowired
    EntityPresetRepository entityPresetRepository;

    public HelperService(AssetTypeRepository assetTypeRepository, EntityCategoryRepository entityCategoryRepository, CountryRepository countryRepository, StateRepository stateRepository, CityRepository cityRepository) {
        this.assetTypeRepository = assetTypeRepository;
        this.entityCategoryRepository = entityCategoryRepository;
        this.countryRepository = countryRepository;
        this.stateRepository = stateRepository;
        this.cityRepository = cityRepository;
    }

    /**
     * <h3>get all Asset Types belonging to an entity including default ones</h3>
     *
     * @param entityId specified entityId
     * @return List of available entity categories
     * @apiNote this should never return null
     */
    public ResponseEntity<ResponseDTO> getAssetTypes(@Nullable String entityId) {
        if (entityId != null)
            return ResponseEntity.ok(ResponseDTO.builder().code("00").message("SUCCESS")
                    .data(new ListMapper<AssetType, AssetTypeDto>().map(assetTypeRepository.findByEntityIdIsNullOrEntityId(entityId), AssetTypeDto::create))
                    .build());
        return ResponseEntity.ok(ResponseDTO.builder().code("00").message("SUCCESS")
                .data(new ListMapper<AssetType, AssetTypeDto>().map(assetTypeRepository.findByEntityIdIsNull(), AssetTypeDto::create))
                .build());
    }

    /**
     * <h3>get all Asset Types belonging to a particular type group</h3>
     *
     * @param typeGroup specified type group
     * @return List of available entity categories
     */
    public ResponseEntity<ResponseDTO> getAssetTypesByTypeGroup(String typeGroup) {
        List<AssetType> assetTypes = assetTypeRepository.findAllByTypeGroup(typeGroup);
        if (assetTypes.isEmpty())
            throw new RecordNotFoundException("Invalid Asset Group. No Assets has been registered under this asset group");
        return ResponseEntity.ok(ResponseDTO.builder().code("00").message("SUCCESS")
                .data(new ListMapper<AssetType, AssetTypeDto>().map(assetTypes, AssetTypeDto::create))
                .build());
    }

    /**
     * <h3>Entity Types</h3>
     *
     * @return List of all entity types
     */
    public ResponseEntity<ResponseDTO> getEntityTypes() {
        return ResponseEntity.ok(ResponseDTO.builder().code("00").message("SUCCESS")
                .data(EntityType.values())
                .build());
    }

    /**
     * <h3>Entity Categories</h3>
     *
     * @return List of available entity categories
     */
    public ResponseEntity<ResponseDTO> getEntityCategories() {
        return ResponseEntity.ok(ResponseDTO.builder().code("00").message("SUCCESS")
                .data(new ListMapper<EntityCategory, EntityCategoryDto>().map(entityCategoryRepository.findAll(), EntityCategoryDto::create))
                .build());
    }

    /**
     * <h3>Get all Countries</h3>
     *
     * @return List of all counties
     */
    public ResponseEntity<ResponseDTO> getCountriesList() {
        return ResponseEntity.ok(ResponseDTO.builder().code("00").message("SUCCESS")
                .data(countryRepository.findAll())
                .build());
    }

    /**
     * <h3>Get a particular state by id</h3>
     *
     * @param id id of the particular country
     * @return a country object
     */
    public ResponseEntity<ResponseDTO> getCountryById(Long id) {
        return ResponseEntity.ok(ResponseDTO.builder().code("00").message("SUCCESS")
                .data(countryRepository.findById(id))
                .build());
    }

    /**
     * <h3>Get a country</h3>
     *
     * @param countryCode countryCode of the particular country
     * @return country object
     */
    public ResponseEntity<ResponseDTO> getCountryByCountryCode(String countryCode) {
        return ResponseEntity.ok(ResponseDTO.builder().code("00").message("SUCCESS")
                .data(countryRepository.findCountryByIso2(countryCode))
                .build());
    }

    /**
     * <h3>Get all states</h3>
     *
     * @return List of all states
     */
    public ResponseEntity<ResponseDTO> getStatesList() {
        return ResponseEntity.ok(ResponseDTO.builder().code("00").message("SUCCESS")
                .data(stateRepository.findAll())
                .build());
    }

    /**
     * <h3>Get all states belonging to a particular country id</h3>
     *
     * @param countryId id of the particular country
     * @return List of states
     */
    public ResponseEntity<ResponseDTO> getStatesByCountryId(Long countryId) {
        return ResponseEntity.ok(ResponseDTO.builder().code("00").message("SUCCESS")
                .data(stateRepository.findAllByCountryId(countryId))
                .build());
    }

    /**
     * <h3>Get all states belonging to a particular country id</h3>
     *
     * @param countryCode id of the particular country
     * @return List of states
     */
    public ResponseEntity<ResponseDTO> getStatesByCountryCode(String countryCode) {
        return ResponseEntity.ok(ResponseDTO.builder().code("00").message("SUCCESS")
                .data(stateRepository.findAllByCountryCode(countryCode))
                .build());
    }

    /**
     * <h3>Get a particular state by id</h3>
     *
     * @param id id of the particular state
     * @return state object
     */
    public ResponseEntity<ResponseDTO> getStateById(Long id) {
        return ResponseEntity.ok(ResponseDTO.builder().code("00").message("SUCCESS")
                .data(stateRepository.findById(id))
                .build());
    }

    /**
     * <h3>Get a particular city by id</h3>
     *
     * @param cityId id of the particular city
     * @return city object
     */
    public ResponseEntity<ResponseDTO> getCity(Long cityId) {
        Optional<City> city = cityRepository.findById(cityId);
        if (city.isEmpty()) throw new EntityNotFoundException("Cannot find a city using this id");
        return ResponseEntity.ok(ResponseDTO.builder().code("00").message("SUCCESS")
                .data(city.get())
                .build());
    }

    /**
     * <h3>Get a list of all cities</h3>
     *
     * @return List of Cities
     */
    public ResponseEntity<ResponseDTO> getCities() {
        return ResponseEntity.ok(ResponseDTO.builder().code("00").message("SUCCESS")
                .data(cityRepository.findAll())
                .build());
    }

    /**
     * <h3>Get a list of cities belonging to the same state</h3>
     *
     * @param stateId id of the particular state
     * @return List of cities
     */
    public ResponseEntity<ResponseDTO> getCitiesByStateId(Long stateId) {
        return ResponseEntity.ok(ResponseDTO.builder().code("00").message("SUCCESS")
                .data(cityRepository.findAllByStateId(stateId))
                .build());
    }

    /**
     * <h3>Get a list of cities belonging to the same country</h3>
     *
     * @param countryId id of the particular country
     * @return List of cities
     */
    public ResponseEntity<ResponseDTO> getCitiesByCountryId(Long countryId) {
        return ResponseEntity.ok(ResponseDTO.builder().code("00").message("SUCCESS")
                .data(cityRepository.findAllByCountryId(countryId))
                .build());
    }

    /**
     * <h3>Get a list of cities belong to the same country and state</h3>
     *
     * @param countryCode countryCode of the particular country
     * @param stateCode   code of the particular state
     * @return List of cities
     */
    public ResponseEntity<ResponseDTO> byCountryCodeAndStateCode(String countryCode, String stateCode) {
        return ResponseEntity.ok(ResponseDTO.builder().code("00").message("SUCCESS")
                .data(cityRepository.findAllByCountryCodeAndStateCode(countryCode, stateCode))
                .build());
    }

    /**
     * <h3>Get a list of cities belong to the same country</h3>
     *
     * @param countryCode countryCode of the particular country
     * @return List of cities
     */
    public ResponseEntity<ResponseDTO> getCitiesByCountryCode(String countryCode) {
        return ResponseEntity.ok(ResponseDTO.builder().code("00").message("SUCCESS")
                .data(cityRepository.findAllByCountryCode(countryCode))
                .build());
    }

    /**
     * <h3>Get a list of category presets</h3>
     *
     * @param categoryId the category id
     * @return List of presets
     */
    public ResponseEntity<ResponseDTO> getEntityCategoriesPresets(Long categoryId) {
        List<EntityPreset> entityPresets = entityPresetRepository.findByCategory_IdIsOrderByTool_NameAsc(categoryId);
        if (entityPresets.isEmpty())
            throw new RecordNotFoundException("no presets available for this category");
        Optional<EntityCategory> category = entityCategoryRepository.findById(categoryId);
        if (category.isEmpty()) throw new RecordNotFoundException("Category doesn't exist");
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode categoryObject = mapper.valueToTree(category.get());
        categoryObject.set("tools", mapper.valueToTree(new ListMapper<EntityPreset, Tool>().map(entityPresets, EntityPreset::getTool)));
        return ResponseEntity.ok(ResponseDTO.builder().code("00").message("SUCCESS").data(categoryObject).build());
    }
}