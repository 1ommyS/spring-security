package ru.itpark.ssg;

/*
auth - authentication
authz - authorization
 */

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/authz")
public class AuthzController {
    private final AuthzService authzService;
    private final JwtService jwtService;

    @PostMapping("login")
    public JwtResponse auth(@RequestBody AuthQuery authRequest) {
        var user = authzService.auth(authRequest.login(), authRequest.password());

        return new JwtResponse(
                jwtService.generateAccessToken(user),
                jwtService.generateRefreshToken(user)
        );
    }

    @PostMapping("signup")
    public JwtResponse signup(@RequestBody AuthQuery authRequest) {
        var user = authzService.signup(authRequest.login(), authRequest.password());

        return new JwtResponse(
                jwtService.generateAccessToken(user),
                jwtService.generateRefreshToken(user)
        );
    }

    @PostMapping("refresh")
    public JwtResponse refresh(@RequestBody String refreshToken) {
        final String userName = jwtService.extractUsername(refreshToken);
        var user = authzService.findUser(userName);

        if (jwtService.isTokenValid(refreshToken, user)) {
            return new JwtResponse(
                    jwtService.generateAccessToken(user),
                    jwtService.generateRefreshToken(user)
            );
        }

        throw new RuntimeException("Токен истек");
    }
}
