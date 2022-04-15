package com.hackerrank.weather.controller;

import com.hackerrank.weather.model.Weather;
import com.hackerrank.weather.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/weather")
public class WeatherApiRestController {

    private static final String SORT_DATE_ASC = "date";
    private static final String SORT_DATE_DESC = "-date";

    @Autowired
    private WeatherRepository weatherRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Weather postWeather(@RequestBody Weather weather) {
        return weatherRepository.save(weather);
    }

    @GetMapping
    public List<Weather> getWeather(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @Nullable Date date,
            @RequestParam(required = false) @Nullable String city,
            @RequestParam(required = false) @Nullable String sort) {

        Boolean orderByDate = convertSort(sort);
        List<String> cities = convertCities(city);

        return weatherRepository.findWeatherFilteringDateAndCityOrderByDateAndId(date, cities, orderByDate);
    }

    @GetMapping("/{id}")
    public Weather getWeatherById(@PathVariable Integer id) {
        Optional<Weather> optionalWeather = weatherRepository.findById(id);
        return optionalWeather.orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    private Boolean convertSort(String sort) {
        if (sort != null && !sort.equals(SORT_DATE_ASC) && !sort.equals(SORT_DATE_DESC)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid `sort` parameter.");
        }

        return sort != null
                ? sort.equals(SORT_DATE_ASC)
                : null;
    }

    private List<String> convertCities(String city) {
        return city != null
                ? Arrays.asList(city.split(","))
                : null;
    }
}
