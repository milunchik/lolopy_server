package lolopy.server.users;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    @Query("SELECT u FROM Users u WHERE u.email = ?1")
    Optional<Users> findUserByEmail(String email);

    @Query("SELECT u FROM Users u WHERE u.name = ?1")
    Optional<Users> findUserByName(String name);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END " +
            "FROM Users u JOIN u.trips t " +
            "WHERE u.id = :userId AND t.id = :tripId")
    boolean existsByTripIdAndUserId(Long tripId, Long userId);
}
