package lolopy.server.auth.token;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import lolopy.server.users.Users;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    Optional<RefreshToken> findByUser(Users user);

    void deleteByUserNameAndIsExpiredTrue(String username);

}
