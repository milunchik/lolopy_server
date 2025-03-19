package lolopy.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EntityScan(basePackages = { "lolopy.server.profiles", "lolopy.server.trips",
		"lolopy.server.users" })

@RestController
public class ServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}

}
