package lolopy.server.user;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    public List<User> getUsers(){
        return List.of(new User(1, "example@gmail.com", "example@gmail.com", "Example Name", "GRFT5846", "01245789", "124546_photo"));
    }

}
