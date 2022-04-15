package com.hackerrank.weather.controller;

import com.hackerrank.weather.model.Weather;
import com.hackerrank.weather.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
        // todo: proper city filtering
        if (sort == null) {
            if (date != null && city != null) {
                return weatherRepository.findByDateAndCityOrderByIdAsc(date, city);
            } else if (date != null) {
                return weatherRepository.findByDateOrderByIdAsc(date);
            } else if (city != null) {
                return weatherRepository.findByCityOrderByIdAsc(city);
            } else {
                return weatherRepository.findByOrderByIdAsc();
            }
        } else if (sort.equals("date")) {
            if (date != null && city != null) {
                return weatherRepository.findByDateAndCityOrderByDateAscIdAsc(date, city);
            } else if (date != null) {
                return weatherRepository.findByDateOrderByDateAscIdAsc(date);
            } else if (city != null) {
                return weatherRepository.findByCityOrderByDateAscIdAsc(city);
            } else {
                return weatherRepository.findByOrderByDateAscIdAsc();
            }
        } else if (sort.equals("-date")) {
            if (date != null && city != null) {
                return weatherRepository.findByDateAndCityOrderByDateDescIdAsc(date, city);
            } else if (date != null) {
                return weatherRepository.findByDateOrderByDateDescIdAsc(date);
            } else if (city != null) {
                return weatherRepository.findByCityOrderByDateDescIdAsc(city);
            } else {
                return weatherRepository.findByOrderByDateDescIdAsc();
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
