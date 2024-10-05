package ar.edu.utn.frc.tup.lciii.service;

import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDTO;
import ar.edu.utn.frc.tup.lciii.dtos.common.PostCountrySaveByAmountDTO;
import ar.edu.utn.frc.tup.lciii.exception.CountryNotFoundException;
import ar.edu.utn.frc.tup.lciii.model.Country;
import ar.edu.utn.frc.tup.lciii.repository.CountryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class CountryServiceTest {

    @InjectMocks
    private CountryService countryService;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private CountryRepository countryRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getAllCountriesReturnsCorrectData() {
        Map<String, Object> countryData = new HashMap<>();
        countryData.put("name", Collections.singletonMap("common", "CountryName"));
        countryData.put("population", 1000000);
        countryData.put("area", 10000.0);
        countryData.put("region", "RegionName");
        countryData.put("cca3", "CountryCode");
        countryData.put("borders", Collections.emptyList());
        countryData.put("languages", Collections.singletonMap("lang", "LanguageName"));

        when(restTemplate.getForObject(anyString(), any())).thenReturn(List.of(countryData));

        List<Country> countries = countryService.getAllCountries();

        assertEquals(1, countries.size());
        assertEquals("CountryName", countries.get(0).getName());
    }

    @Test
    public void getCountriesReturnsCorrectData() {
        Map<String, Object> countryData = new HashMap<>();
        countryData.put("name", Collections.singletonMap("common", "CountryName"));
        countryData.put("population", 1000000);
        countryData.put("area", 10000.0);
        countryData.put("region", "RegionName");
        countryData.put("cca3", "CountryCode");
        countryData.put("borders", Collections.emptyList());
        countryData.put("languages", Collections.singletonMap("lang", "LanguageName"));

        when(restTemplate.getForObject(anyString(), any())).thenReturn(List.of(countryData));

        List<CountryDTO> countries = countryService.getCountries();

        assertEquals(1, countries.size());
        assertEquals("CountryName", countries.get(0).getName());
    }

    @Test
    public void getCountryByNameReturnsCorrectData() {
        Map<String, Object> countryData = new HashMap<>();
        countryData.put("name", Collections.singletonMap("common", "CountryName"));
        countryData.put("population", 1000000);
        countryData.put("area", 10000.0);
        countryData.put("region", "RegionName");
        countryData.put("cca3", "CountryCode");
        countryData.put("borders", Collections.emptyList());
        countryData.put("languages", Collections.singletonMap("lang", "LanguageName"));

        when(restTemplate.getForObject(anyString(), any())).thenReturn(List.of(countryData));

        CountryDTO country = countryService.getCountryByName("CountryName");

        assertEquals("CountryName", country.getName());
    }

    @Test
    public void getCountryByNameThrowsExceptionWhenNotFound() {
        when(restTemplate.getForObject(anyString(), any())).thenReturn(Collections.emptyList());

        assertThrows(CountryNotFoundException.class, () -> countryService.getCountryByName("CountryName"));
    }

    @Test
    public void getCountryByCodeReturnsCorrectData() {
        Map<String, Object> countryData = new HashMap<>();
        countryData.put("name", Collections.singletonMap("common", "CountryName"));
        countryData.put("population", 1000000);
        countryData.put("area", 10000.0);
        countryData.put("region", "RegionName");
        countryData.put("cca3", "CountryCode");
        countryData.put("borders", Collections.emptyList());
        countryData.put("languages", Collections.singletonMap("lang", "LanguageName"));

        when(restTemplate.getForObject(anyString(), any())).thenReturn(List.of(countryData));

        CountryDTO country = countryService.getCountryByCode("CountryCode");

        assertEquals("CountryName", country.getName());
    }

    @Test
    public void getCountryByCodeThrowsExceptionWhenNotFound() {
        when(restTemplate.getForObject(anyString(), any())).thenReturn(Collections.emptyList());

        assertThrows(CountryNotFoundException.class, () -> countryService.getCountryByCode("CountryCode"));
    }

    @Test
    public void getCountriesByContinentReturnsCorrectData() {
        Map<String, Object> countryData = new HashMap<>();
        countryData.put("name", Collections.singletonMap("common", "CountryName"));
        countryData.put("population", 1000000);
        countryData.put("area", 10000.0);
        countryData.put("region", "RegionName");
        countryData.put("cca3", "CountryCode");
        countryData.put("borders", Collections.emptyList());
        countryData.put("languages", Collections.singletonMap("lang", "LanguageName"));

        when(restTemplate.getForObject(anyString(), any())).thenReturn(List.of(countryData));

        List<CountryDTO> countries = countryService.getCountriesByContinent("RegionName");

        assertEquals(1, countries.size());
        assertEquals("CountryName", countries.get(0).getName());
    }

    @Test
    public void getCountryWithMostBordersThrowsExceptionWhenNoCountryHasBorders() {
        Map<String, Object> countryData = new HashMap<>();
        countryData.put("name", Collections.singletonMap("common", "CountryName"));
        countryData.put("population", 1000000);
        countryData.put("area", 10000.0);
        countryData.put("region", "RegionName");
        countryData.put("cca3", "CountryCode");
        countryData.put("borders", Collections.emptyList());
        countryData.put("languages", Collections.singletonMap("lang", "LanguageName"));

        when(restTemplate.getForObject(anyString(), any())).thenReturn(List.of(countryData));

        assertThrows(CountryNotFoundException.class, () -> countryService.getCountryWithMostBorders());
    }

    @Test
    public void getCountryByContinentThrowsExceptionWhenNotFound() {
        when(restTemplate.getForObject(anyString(), any())).thenReturn(Collections.emptyList());

        assertThrows(CountryNotFoundException.class, () -> countryService.getCountriesByContinent("CountryContinent"));
    }

    @Test
    public void getCountriesByLanguageReturnsCorrectData() {
        Map<String, Object> countryData = new HashMap<>();
        countryData.put("name", Collections.singletonMap("common", "CountryName"));
        countryData.put("population", 1000000);
        countryData.put("area", 10000.0);
        countryData.put("region", "RegionName");
        countryData.put("cca3", "CountryCode");
        countryData.put("borders", Collections.emptyList());
        countryData.put("languages", Collections.singletonMap("lang", "LanguageName"));

        when(restTemplate.getForObject(anyString(), any())).thenReturn(List.of(countryData));

        List<CountryDTO> countries = countryService.getCountriesByLanguage("LanguageName");

        assertEquals(1, countries.size());
        assertEquals("CountryName", countries.get(0).getName());
    }

    @Test
    public void getCountryWithMostBordersReturnsCorrectData() {
        Map<String, Object> countryData = new HashMap<>();
        countryData.put("name", Collections.singletonMap("common", "CountryName"));
        countryData.put("population", 1000000);
        countryData.put("area", 10000.0);
        countryData.put("region", "RegionName");
        countryData.put("cca3", "CountryCode");
        countryData.put("borders", List.of("Border1", "Border2"));
        countryData.put("languages", Collections.singletonMap("lang", "LanguageName"));

        when(restTemplate.getForObject(anyString(), any())).thenReturn(List.of(countryData));

        CountryDTO country = countryService.getCountryWithMostBorders();

        assertEquals("CountryName", country.getName());
    }

    @Test
    public void saveCountriesReturnsCorrectData() {
        Map<String, Object> countryData = new HashMap<>();
        countryData.put("name", Collections.singletonMap("common", "CountryName"));
        countryData.put("population", 1000000);
        countryData.put("area", 10000.0);
        countryData.put("region", "RegionName");
        countryData.put("cca3", "CountryCode");
        countryData.put("borders", Collections.emptyList());
        countryData.put("languages", Collections.singletonMap("lang", "LanguageName"));

        when(restTemplate.getForObject(anyString(), any())).thenReturn(List.of(countryData, countryData, countryData));

        List<CountryDTO> countries = countryService.saveCountries(new PostCountrySaveByAmountDTO(2));

        assertEquals(2, countries.size());
        assertEquals("CountryName", countries.get(0).getName());
    }

    @Test
    public void saveCountriesThrowsExceptionWhenAmountIsInvalid() {
        assertThrows(IllegalArgumentException.class, () -> countryService.saveCountries(new PostCountrySaveByAmountDTO(0)));
        assertThrows(IllegalArgumentException.class, () -> countryService.saveCountries(new PostCountrySaveByAmountDTO(11)));
    }
}