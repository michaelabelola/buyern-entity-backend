package com.buyern.buyern.Services;

import com.buyern.buyern.Controllers.UserController;
import com.buyern.buyern.Enums.EntityType;
import com.buyern.buyern.Helpers.ListMapper;
import com.buyern.buyern.Models.Entity.EntityCategory;
import com.buyern.buyern.Models.Location.City;
import com.buyern.buyern.Repositories.*;
import com.buyern.buyern.Repositories.Entity.EntityCategoryRepository;
import com.buyern.buyern.Repositories.Entity.EntityCategoryToolsRepository;
import com.buyern.buyern.dtos.Entity.EntityCategoryDto;
import com.buyern.buyern.dtos.ResponseDTO;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class HelperService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    final EntityCategoryRepository entityCategoryRepository;
    final CountryRepository countryRepository;
    final StateRepository stateRepository;
    final CityRepository cityRepository;
    final EntityCategoryToolsRepository entityCategoryToolsRepository;
    final ToolRepository toolRepository;

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
                .data(countryRepository.findByOrderByNameAsc())
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
                .data(stateRepository.findAllByCountryIdOrderByName(countryId))
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
                .data(cityRepository.findAllByStateIdOrderByName(stateId))
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
                .data(cityRepository.findAllByCountryIdOrderByName(countryId))
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
                .data(cityRepository.findAllByCountryCodeOrderByName(countryCode))
                .build());
    }

    public ResponseEntity<ResponseDTO> getEntityCategoriesItems(Long categoryId) {
        return ResponseEntity.ok(ResponseDTO.builder().code("00").message("SUCCESS").data(entityCategoryToolsRepository.findByEntityCategory_Id(categoryId)).build());
    }
    public ResponseEntity<ResponseDTO> getTools() {
        return ResponseEntity.ok(ResponseDTO.builder().code("00").message("SUCCESS").data(toolRepository.findAll()).build());
    }

    /**
     * <h3>Rename A MultipartFile And Move It To Project's Base Location. </h3>
     * @param _file the multipart file
     * @param newName new name to be assigned to returned file
     * @return the new file object is returned
     * @apiNote the File object returned has its name in the format = newName.fileExtension
     */
    public static File renameFile(MultipartFile _file, String newName) {
        File file = new File(String.format("%s/%s.%s", System.getProperty("user.dir"), newName, _file.getContentType().split("/")[1]));
        file.deleteOnExit();
        try {
            _file.transferTo(file);
        } catch (IOException e) {
            LOGGER.error("Exception Occurred while renaming files: {} ", e.getMessage());
        }
        return file;
    }
}