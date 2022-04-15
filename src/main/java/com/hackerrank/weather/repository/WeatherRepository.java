package com.hackerrank.weather.repository;

import com.hackerrank.weather.model.Weather;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface WeatherRepository extends JpaRepository<Weather, Integer> {

    // no filter

    List<Weather> findByOrderByIdAsc();

    List<Weather> findByOrderByDateAscIdAsc();

    List<Weather> findByOrderByDateDescIdAsc();

    // filter by date

    List<Weather> findByDateOrderByIdAsc(Date date);

    List<Weather> findByDateOrderByDateAscIdAsc(Date date);

    List<Weather> findByDateOrderByDateDescIdAsc(Date date);

    // filter by city

    List<Weather> findByCityInIgnoreCaseOrderByIdAsc(Collection<String> city);

    List<Weather> findByCityInIgnoreCaseOrderByDateAscIdAsc(Collection<String> city);

    List<Weather> findByCityInIgnoreCaseOrderByDateDescIdAsc(Collection<String> city);

    // filter by date and city

    List<Weather> findByDateAndCityInIgnoreCaseOrderByIdAsc(Date date, Collection<String> city);

    List<Weather> findByDateAndCityInIgnoreCaseOrderByDateAscIdAsc(Date date, Collection<String> city);

    List<Weather> findByDateAndCityInIgnoreCaseOrderByDateDescIdAsc(Date date, Collection<String> city);
}
