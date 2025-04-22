package lolopy.server.auth.token;

import jakarta.persistence.*;
import lolopy.server.users.Users;
import lombok.Data;

@Entity
@Data
@Table(name = "access_tokens")
public class AccessToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    private String tokenType;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true)
    private Users user;

    private boolean isExpired;

    public AccessToken() {
    }

    public AccessToken(String token, String tokenType, Users user, boolean isExpired) {
        this.token = token;
        this.tokenType = tokenType;
        this.user = user;
        this.isExpired = isExpired;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public boolean isExpired() {
        return isExpired;
    }

    public void setIsExpired(boolean isExpired) {
        this.isExpired = isExpired;
    }

}
