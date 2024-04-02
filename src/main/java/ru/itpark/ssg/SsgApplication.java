package ru.itpark.ssg;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SsgApplication {

    public static void main(String[] args) {
        SpringApplication.run(SsgApplication.class, args);
    }


    @Bean
    CommandLineRunner commandLineRunner(
            UserRepository repository,
            PasswordEncoder encoder
    ) {

        return args -> {
            var user = repository.findById(1);

            if (user.isEmpty()) {
                var newUser = new User();
                newUser.setLogin("admin");
                newUser.setPassword(encoder.encode("qwerty"));

                repository.save(newUser);
            }
        };
    }
}
