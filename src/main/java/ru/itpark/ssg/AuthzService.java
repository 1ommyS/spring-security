package ru.itpark.ssg;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthzService {
    private final UserRepository repository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;

    public User auth(String login, String password) {

        var user = repository.findByLogin(login).orElseThrow(EntityNotFoundException::new);
        // user.getPassword() ->  тсмочдяльовчфдсоыавпещшсозщшо123213ждлоабчыфвсщшо2ц3хз94лс123здх

        log.info(user.getPassword());
        log.info(password);
        log.info(encoder.encode(password));

        if (
                encoder.matches(
                        password, user.getPassword()
                )
        ) return user;

        throw new IllegalArgumentException("Неверный логин или пароль");
    }

    public User signup(String login, String password) {
        var userFromDB = repository.findByLogin(login);
        if (userFromDB.isPresent())
            throw new IllegalArgumentException("Пользователь с таким логином уже существует");

        var role = roleRepository.findById(1L).get();

        var user = User.builder()
                .roles(List.of(role))
                .login(login)
                .password(
                        encoder.encode(password)
                )
                .build();

        return repository.save(user);
    }

    public User findUser(String userName) {
        return repository.findByLogin(userName)
                .orElseThrow(EntityNotFoundException::new);
    }
}
