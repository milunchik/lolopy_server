package lolopy.server.auth.token;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import lolopy.server.users.Users;

@Service
public class TokenService {

    @Autowired
    private final RefreshTokenRepository refreshTokenRepository;
    @Autowired
    private final AccessTokenRepository accessTokenRepository;

    public TokenService(RefreshTokenRepository refreshTokenRepository,
            AccessTokenRepository accessTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.accessTokenRepository = accessTokenRepository;
    }

    public void saveTokens(Users user, String accessTokenStr, String refreshTokenStr) {
        Optional<AccessToken> existingAccessToken = accessTokenRepository.findByUser(user);
        Optional<RefreshToken> existingRefreshToken = refreshTokenRepository.findByUser(user);

        if (existingAccessToken.isPresent()) {
            AccessToken accessToken = existingAccessToken.get();
            accessToken.setToken(accessTokenStr);
            accessToken.setIsExpired(false);
            accessTokenRepository.save(accessToken);
        } else {
            AccessToken accessToken = new AccessToken(accessTokenStr, "Bearer", user, false);
            accessTokenRepository.save(accessToken);
        }

        if (existingRefreshToken.isPresent()) {
            RefreshToken refreshToken = existingRefreshToken.get();
            refreshToken.setToken(refreshTokenStr);
            refreshToken.setIsExpired(false);
            refreshTokenRepository.save(refreshToken);
        } else {

            RefreshToken refreshToken = new RefreshToken(refreshTokenStr, "Bearer", user, false);
            refreshTokenRepository.save(refreshToken);
        }

        user.setAccess(existingAccessToken.orElseGet(() -> new AccessToken(accessTokenStr, "Bearer", user, false)));
        user.setRefresh(existingRefreshToken.orElseGet(() -> new RefreshToken(refreshTokenStr, "Bearer", user, false)));
    }

    public boolean isRefreshTokenValid(String token) {
        return refreshTokenRepository.findByToken(token)
                .map(t -> !t.isExpired())
                .orElse(false);
    }

    public void invalidateToken(String token) {
        refreshTokenRepository.findByToken(token).ifPresent(t -> {
            t.setIsExpired(true);
            refreshTokenRepository.save(t);
        });
    }

    @Transactional
    public void deleteExpiredAccessTokens(String username) {
        accessTokenRepository.deleteByUserNameAndIsExpiredTrue(username);
    }

    @Transactional
    public void deleteExpiredRefreshTokens(String username) {
        refreshTokenRepository.deleteByUserNameAndIsExpiredTrue(username);
    }
}
