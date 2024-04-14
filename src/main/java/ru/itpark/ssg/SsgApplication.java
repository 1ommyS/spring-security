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
            RoleRepository repository,
            PasswordEncoder encoder
    ) {

        return args -> {
            var role = repository.findByName("ROLE_USER");
            if (role.isEmpty()) {
                var newRole = Role.builder()
                        .name("ROLE_USER")
                        .build();

                repository.save(newRole);
            }

        };
    }
}
