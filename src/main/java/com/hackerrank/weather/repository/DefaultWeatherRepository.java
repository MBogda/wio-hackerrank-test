package com.hackerrank.weather.repository;

import com.hackerrank.weather.model.Weather;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

@Component
public class DefaultWeatherRepository extends SimpleJpaRepository<Weather, Integer> implements WeatherRepository {
    public DefaultWeatherRepository(EntityManager em) {
        super(Weather.class, em);
    }
}
