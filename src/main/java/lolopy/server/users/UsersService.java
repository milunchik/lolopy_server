package lolopy.server.users;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class UsersService {

    public List<Users> getUsers() {
        return List.of(new Users(1L, "emiliia", "emiliia@gmail.com", "emiliia@gmail.com"));
    }

}
