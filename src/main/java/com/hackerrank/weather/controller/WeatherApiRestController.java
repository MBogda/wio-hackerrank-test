package com.hackerrank.weather.controller;

import com.hackerrank.weather.model.Weather;
import com.hackerrank.weather.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

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
            @RequestParam(required = false) Date date,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String sort) {
        return weatherRepository.findByOrderByIdAsc();
    }
}
