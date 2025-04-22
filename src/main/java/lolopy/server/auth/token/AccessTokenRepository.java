package lolopy.server.auth.token;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import lolopy.server.users.Users;

public interface AccessTokenRepository extends JpaRepository<AccessToken, Long> {
    Optional<AccessToken> findByToken(String token);

    Optional<AccessToken> findByUser(Users user);

    void deleteByUserNameAndIsExpiredTrue(String username);

}
