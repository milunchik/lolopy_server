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
    public Page<Trips> findAllWithFilters(Pageable pageable, Transport transport, FoodPlace foodPlace,
            Accommodation accommodation, String country) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<Trips> query = cb.createQuery(Trips.class);
        Root<Trips> trip = query.from(Trips.class);

        List<Predicate> predicates = new ArrayList<>();

        if (transport != null) {
            predicates.add(cb.equal(trip.get("transport"), transport));
        }
        if (foodPlace != null) {
            predicates.add(cb.equal(trip.get("foodPlace"), foodPlace));
        }
        if (accommodation != null) {
            predicates.add(cb.equal(trip.get("accommodation"), accommodation));
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

        if (transport != null) {
            countPredicates.add(cb.equal(countRoot.get("transport"), transport));
        }
        if (foodPlace != null) {
            countPredicates.add(cb.equal(countRoot.get("foodPlace"), foodPlace));
        }
        if (accommodation != null) {
            countPredicates.add(cb.equal(countRoot.get("accommodation"), accommodation));
        }
        if (country != null && !country.isBlank()) {
            countPredicates.add(cb.equal(countRoot.get("country"), country));
        }

        countQuery.select(cb.count(countRoot)).where(countPredicates.toArray(new Predicate[0]));

        Long total = entityManager.createQuery(countQuery).getSingleResult();

        return new PageImpl<>(tripsList, pageable, total);
    }
}
