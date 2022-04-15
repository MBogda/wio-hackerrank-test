package com.hackerrank.weather.repository;

import com.hackerrank.weather.model.Weather;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface CustomWeatherRepository {
    List<Weather> findWeatherFilteringDateAndCityOrderByDateAndId(Date date, Collection<String> city, Boolean dateAsc);
}
