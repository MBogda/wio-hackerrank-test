package com.hackerrank.weather.controller;

import com.hackerrank.weather.model.Weather;
import com.hackerrank.weather.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/weather")
public class WeatherApiRestController {

    @Autowired
    private WeatherRepository defaultWeatherRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Weather postWeather(@RequestBody Weather weather) {
        return defaultWeatherRepository.save(weather);
    }
}
