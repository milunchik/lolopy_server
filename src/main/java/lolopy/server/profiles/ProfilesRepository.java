package lolopy.server.profiles;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfilesRepository extends JpaRepository<Profiles, Long> {
    @Query("SELECT p FROM Profiles p WHERE p.name = ?1")
    Optional<Profiles> findProfileByName(String name);
}
