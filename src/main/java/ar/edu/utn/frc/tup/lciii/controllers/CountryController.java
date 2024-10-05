package ar.edu.utn.frc.tup.lciii.controllers;
import ar.edu.utn.frc.tup.lciii.dtos.common.CountryDTO;
import ar.edu.utn.frc.tup.lciii.dtos.common.PostCountrySaveByAmountDTO;
import ar.edu.utn.frc.tup.lciii.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CountryController {

    private final CountryService countryService;

    @GetMapping("api/countries")
    public List<CountryDTO> getCountries() {
        return countryService.getCountries();
    }
    @PostMapping("api/countries")
    public List<CountryDTO> saveCountries(@RequestBody PostCountrySaveByAmountDTO saveDTO) {
        return countryService.saveCountries(saveDTO);
    }

    @GetMapping("api/countries/name/{name}")
    public CountryDTO getCountryByName(@RequestParam String name) {
        return countryService.getCountryByName(name);
    }
    @GetMapping("api/countries/code/{code}")
    public CountryDTO getCountryByCode(@RequestParam String code) {
        return countryService.getCountryByCode(code);
    }

    @GetMapping("api/countries/{continent}/continent")
    public List<CountryDTO> getCountriesByContinent(@RequestParam String continent) {
        return countryService.getCountriesByContinent(continent);
    }

    @GetMapping("api/countries/{language}/language")
    public List<CountryDTO> getCountriesByLanguage(@RequestParam String language) {
        return countryService.getCountriesByLanguage(language);
    }

    @GetMapping("api/countries/most-borders")
    public CountryDTO getCountriesWithMostBorders() {
        return countryService.getCountryWithMostBorders();
    }

}