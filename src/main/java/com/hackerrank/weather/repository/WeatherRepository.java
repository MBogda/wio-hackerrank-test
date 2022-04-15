package com.hackerrank.weather.repository;

import com.hackerrank.weather.model.Weather;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface WeatherRepository extends JpaRepository<Weather, Integer> {
    List<Weather> findByOrderByIdAsc();

    List<Weather> findByOrderByDateAscIdAsc();

    List<Weather> findByOrderByDateDescIdAsc();

    List<Weather> findByDateOrderByIdAsc(Date date);

    List<Weather> findByDateOrderByDateAscIdAsc(Date date);

    List<Weather> findByDateOrderByDateDescIdAsc(Date date);

    List<Weather> findByCityOrderByIdAsc(String city);

    List<Weather> findByCityOrderByDateAscIdAsc(String city);

    List<Weather> findByCityOrderByDateDescIdAsc(String city);

    List<Weather> findByDateAndCityOrderByIdAsc(Date date, String city);

    List<Weather> findByDateAndCityOrderByDateAscIdAsc(Date date, String city);

    List<Weather> findByDateAndCityOrderByDateDescIdAsc(Date date, String city);
}
