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
        // todo: get rid of this ugly method
        if (sort == null) {
            if (city != null) {
                List<String> cities = Arrays.asList(city.split(","));
                if (date != null) {
                    return weatherRepository.findByDateAndCityInIgnoreCaseOrderByIdAsc(date, cities);
                } else {
                    return weatherRepository.findByCityInIgnoreCaseOrderByIdAsc(cities);
                }
            } else {
                if (date != null) {
                    return weatherRepository.findByDateOrderByIdAsc(date);
                } else {
                    return weatherRepository.findByOrderByIdAsc();
                }
            }
        } else if (sort.equals("date")) {
            if (city != null) {
                List<String> cities = Arrays.asList(city.split(","));
                if (date != null) {
                    return weatherRepository.findByDateAndCityInIgnoreCaseOrderByDateAscIdAsc(date, cities);
                } else {
                    return weatherRepository.findByCityInIgnoreCaseOrderByDateAscIdAsc(cities);
                }
            } else {
                if (date != null) {
                    return weatherRepository.findByDateOrderByDateAscIdAsc(date);
                } else {
                    return weatherRepository.findByOrderByDateAscIdAsc();
                }
            }
        } else if (sort.equals("-date")) {
            if (city != null) {
                List<String> cities = Arrays.asList(city.split(","));
                if (date != null) {
                    return weatherRepository.findByDateAndCityInIgnoreCaseOrderByDateDescIdAsc(date, cities);
                } else {
                    return weatherRepository.findByCityInIgnoreCaseOrderByDateDescIdAsc(cities);
                }
            } else {
                if (date != null) {
                    return weatherRepository.findByDateOrderByDateDescIdAsc(date);
                } else {
                    return weatherRepository.findByOrderByDateDescIdAsc();
                }
            }
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid `sort` parameter.");
        }
    }

    @GetMapping("/{id}")
    public Weather getWeatherById(@PathVariable Integer id) {
        Optional<Weather> optionalWeather = weatherRepository.findById(id);
        return optionalWeather.orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
