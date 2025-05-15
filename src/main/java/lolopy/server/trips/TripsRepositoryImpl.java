package lolopy.server.trips;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import lolopy.server.enums.Enums.Accommodation;
import lolopy.server.enums.Enums.FoodPlace;
import lolopy.server.enums.Enums.Transport;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TripsRepositoryImpl implements TripsRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Trips> findAllWithFilters(Pageable pageable, List<Transport> transports, List<FoodPlace> foodPlaces,
            List<Accommodation> accommodations, String country) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<Trips> query = cb.createQuery(Trips.class);
        Root<Trips> trip = query.from(Trips.class);

        List<Predicate> predicates = new ArrayList<>();

        if (transports != null && !transports.isEmpty()) {
            predicates.add(trip.get("transport").in(transports));
        }
        if (foodPlaces != null && !foodPlaces.isEmpty()) {
            predicates.add(trip.get("foodPlace").in(foodPlaces));
        }
        if (accommodations != null && !accommodations.isEmpty()) {
            predicates.add(trip.get("accommodation").in(accommodations));
        }
        if (country != null && !country.isBlank()) {
            predicates.add(cb.equal(trip.get("country"), country));
        }

        query.where(predicates.toArray(new Predicate[0]));

        List<Trips> tripsList = entityManager.createQuery(query)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Trips> countRoot = countQuery.from(Trips.class);

        List<Predicate> countPredicates = new ArrayList<>();

        if (transports != null && !transports.isEmpty()) {
            countPredicates.add(countRoot.get("transport").in(transports));
        }
        if (foodPlaces != null && !foodPlaces.isEmpty()) {
            countPredicates.add(countRoot.get("foodPlace").in(foodPlaces));
        }
        if (accommodations != null && !accommodations.isEmpty()) {
            countPredicates.add(countRoot.get("accommodation").in(accommodations));
        }
        if (country != null && !country.isBlank()) {
            countPredicates.add(cb.equal(countRoot.get("country"), country));
        }

        countQuery.select(cb.count(countRoot)).where(countPredicates.toArray(new Predicate[0]));

        Long total = entityManager.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(tripsList, pageable, total);
    }
}
