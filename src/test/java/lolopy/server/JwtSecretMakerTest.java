package lolopy.server;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.MacAlgorithm;
import javax.crypto.SecretKey;
import org.junit.jupiter.api.Test;

public class JwtSecretMakerTest {
    @Test
    public void generateSecretKey() {
        MacAlgorithm algorithm = Jwts.SIG.HS512;
        SecretKey key = algorithm.key().build();

        String base64Key = java.util.Base64.getEncoder().encodeToString(key.getEncoded());
        System.out.println("\nBase64 Key = " + base64Key);
    }

}
