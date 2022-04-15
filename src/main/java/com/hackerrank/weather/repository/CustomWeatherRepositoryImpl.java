package com.hackerrank.weather.repository;

import com.hackerrank.weather.model.Weather;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomWeatherRepositoryImpl implements CustomWeatherRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Weather> findWeatherFilteringDateAndCityOrderByDateAndId(Date date, Collection<String> city, Boolean orderByDate) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Weather> query = criteriaBuilder.createQuery(Weather.class);
        Root<Weather> from = query.from(Weather.class);

        Predicate predicates = buildPredicates(criteriaBuilder, from, date, city);
        List<Order> orders = buildOrders(criteriaBuilder, from, orderByDate);

        query.select(from).where(predicates).orderBy(orders);
        return entityManager.createQuery(query).getResultList();
    }

    private Predicate buildPredicates(CriteriaBuilder criteriaBuilder, Root<Weather> from, Date date, Collection<String> city) {
        Path<String> datePath = from.get("date");
        Path<String> cityPath = from.get("city");

        List<Predicate> predicates = new ArrayList<>();
        if (date != null) {
            predicates.add(criteriaBuilder.equal(datePath, date));
        }
        if (city != null) {
            predicates.add(criteriaBuilder.upper(cityPath).in(
                    city.stream().map(String::toUpperCase).collect(Collectors.toList())));
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private List<Order> buildOrders(CriteriaBuilder criteriaBuilder, Root<Weather> from, Boolean orderByDate) {
        Path<String> datePath = from.get("date");
        Path<String> idPath = from.get("id");

        List<Order> orders = new ArrayList<>();
        if (orderByDate != null) {
            if (orderByDate) {
                orders.add(criteriaBuilder.asc(datePath));
            } else {
                orders.add(criteriaBuilder.desc(datePath));
            }
        }
        orders.add(criteriaBuilder.asc(idPath));
        return orders;
    }
}
