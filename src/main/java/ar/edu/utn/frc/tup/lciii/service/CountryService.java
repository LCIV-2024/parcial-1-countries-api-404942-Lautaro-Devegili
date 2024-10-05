package ar.edu.utn.frc.tup.lciii.service;

import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDTO;
import ar.edu.utn.frc.tup.lciii.dtos.common.PostCountrySaveByAmountDTO;
import ar.edu.utn.frc.tup.lciii.entities.CountryEntity;
import ar.edu.utn.frc.tup.lciii.exception.CountryNotFoundException;
import ar.edu.utn.frc.tup.lciii.model.Country;
import ar.edu.utn.frc.tup.lciii.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.internal.bytebuddy.implementation.bytecode.Throw;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CountryService {

        private final CountryRepository countryRepository;

        private final RestTemplate restTemplate;

        public List<Country> getAllCountries() {
                String url = "https://restcountries.com/v3.1/all";
                List<Map<String, Object>> response = restTemplate.getForObject(url, List.class);
                return response.stream().map(this::mapToCountry).collect(Collectors.toList());
        }

        /**
         * Agregar mapeo de campo cca3 (String) Hecho
         * Agregar mapeo campos borders ((List<String>)) Hecho
         */
        private Country mapToCountry(Map<String, Object> countryData) {
                Map<String, Object> nameData = (Map<String, Object>) countryData.get("name");
                Country c = Country.builder()
                        .name((String) nameData.get("common"))
                        .population(((Number) countryData.get("population")).longValue())
                        .area(((Number) countryData.get("area")).doubleValue())
                        .region((String) countryData.get("region"))
                        .code((String) countryData.get("cca3"))
                        .borders((List<String>) countryData.get("borders"))
                        .languages((Map<String, String>) countryData.get("languages"))
                        .build();
                return c;
        }

        public List<CountryDTO> getCountries() {
                return  getAllCountries().stream().map(this::mapToDTO).toList();
        }


        private CountryDTO mapToDTO(Country country) {
                return new CountryDTO(country.getCode(), country.getName());
        }

        public CountryDTO getCountryByName(String name) {
                Optional<Country> countryOptional = getAllCountries().stream()
                        .filter(c -> c.getName().equals(name))
                        .findFirst();

                if (!countryOptional.isPresent()) {
                        throw new CountryNotFoundException("Country not found");
                }

                return mapToDTO(countryOptional.get());
        }

        public CountryDTO getCountryByCode(String code) {
                Optional<Country> countryOptional = getAllCountries().stream()
                        .filter(c -> c.getCode().equals(code))
                        .findFirst();

                if (!countryOptional.isPresent()) {
                        throw new CountryNotFoundException("Country not found");
                }

                return mapToDTO(countryOptional.get());
        }

        public List<CountryDTO> getCountriesByContinent(String continent) {
                List<CountryDTO> lcc = getAllCountries().stream()
                        .filter(c -> c.getRegion() != null && c.getRegion().equals(continent))
                        .map(this::mapToDTO)
                        .toList();

                if (lcc.isEmpty()) {
                        throw new CountryNotFoundException("Continent not found");
                }

                return lcc;
        }

        public List<CountryDTO> getCountriesByLanguage(String language) {
                return getAllCountries().stream().filter(c -> c.getLanguages() != null && c.getLanguages().containsValue(language)).map(this::mapToDTO).toList();
        }

        public CountryDTO getCountryWithMostBorders() {
                List<Country> lcc = getAllCountries().stream()
                        .filter(c -> c.getBorders() != null && !c.getBorders().isEmpty())
                        .collect(Collectors.toList());

                Country cc = lcc.stream()
                        .max(Comparator.comparingInt(c -> c.getBorders().size()))
                        .orElse(null);

                if (cc == null) {
                        throw new CountryNotFoundException("Country with most borders doesn't exist");
                }

                return mapToDTO(cc);
        }

        public List<CountryDTO> saveCountries(PostCountrySaveByAmountDTO saveDTO) {
                Integer amount = saveDTO.getAmountOfCountryToSave();
                if (amount <= 0 || amount > 10) {
                        throw new IllegalArgumentException("Amount of countries to save must be greater than 0 and less than 10");
                }
                List<Country> countries = getAllCountries();
                Collections.shuffle(countries);
                List<Country> countriesToSave = countries.subList(0, amount);
                List<CountryEntity> cToSave = countriesToSave.stream().map(c -> new CountryEntity(0, c.getName(), c.getCode(), c.getPopulation(), c.getRegion())).toList();
                countryRepository.saveAll(cToSave);
                return countriesToSave.stream().map(this::mapToDTO).toList();
        }
}