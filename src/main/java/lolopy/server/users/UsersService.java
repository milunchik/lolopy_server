package lolopy.server.users;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class UsersService {
    List<Long> tripIds = Arrays.asList(6L, 1L);

    public List<Users> getUsers() {
        return List.of(new Users(1L, "emiliia", "emiliia@gmail.com",
                "emiliia@gmail.com", tripIds, 1L));
    }
}
