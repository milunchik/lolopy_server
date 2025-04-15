package lolopy.server.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import lolopy.server.users.Users;

public interface MyUserDetailRepository extends JpaRepository<Users, Long> {
    // Optional<Users> findByUsername(String email);
}
