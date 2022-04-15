package com.hackerrank.weather.repository;

import com.hackerrank.weather.model.Weather;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomWeatherRepositoryImpl implements CustomWeatherRepository {

    private static final String SELECT_QUERY = "SELECT * FROM Weather ";
    private static final String NO_FILTER = "";
    private static final String FILTER_DATE = "WHERE date = :date ";
    private static final String FILTER_CITY = "WHERE UPPER(city) in (:city) ";
    private static final String FILTER_DATE_AND_CITY = "WHERE date = :date AND UPPER(city) in (:city) ";
    private static final String ORDER_BY_ID = "ORDER BY ID ASC";
    private static final String ORDER_BY_DATE_ASC_AND_ID_ASC = "ORDER BY DATE ASC, ID ASC";
    private static final String ORDER_BY_DATE_DESC_AND_ID_ASC = "ORDER BY DATE DESC, ID ASC";

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Weather> findWeatherFilteringDateAndCityOrderByDateAndId(Date date, Collection<String> city, Boolean orderByDate) {
        String sqlQuery = SELECT_QUERY + getFilterByDateAndCity(date, city) + getOrderBy(orderByDate);
        Query nativeQuery = entityManager.createNativeQuery(sqlQuery, Weather.class);

        if (date != null) {
            nativeQuery.setParameter("date", date);
        }
        if (city != null) {
            nativeQuery.setParameter("city", city.stream().map(String::toUpperCase).collect(Collectors.toList()));
        }

        return nativeQuery.getResultList();
    }

    private String getFilterByDateAndCity(Date date, Collection<String> city) {
        if (date == null && city == null) {
            return NO_FILTER;
        } else if (date != null && city == null) {
            return FILTER_DATE;
        } else if (date == null) {
            return FILTER_CITY;
        } else {
            return FILTER_DATE_AND_CITY;
        }
    }

    private String getOrderBy(Boolean orderByDate) {
        if (orderByDate == null) {
            return ORDER_BY_ID;
        } else if (orderByDate) {
            return ORDER_BY_DATE_ASC_AND_ID_ASC;
        } else {
            return ORDER_BY_DATE_DESC_AND_ID_ASC;
        }
    }
}
