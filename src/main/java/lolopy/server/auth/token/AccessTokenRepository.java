package lolopy.server.auth.token;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;
import lolopy.server.users.Users;

public interface AccessTokenRepository extends JpaRepository<AccessToken, Long> {
    Optional<AccessToken> findByToken(String token);

    Optional<AccessToken> findByUser(Users user);

    void deleteByUserNameAndIsExpiredTrue(String username);

    @Transactional
    @Modifying
    @Query("DELETE FROM AccessToken at WHERE at.user.email = :email AND at.isExpired=true")
    void deleteByUser(@Param("email") String email);

}
