package ru.itpark.ssg;

import lombok.Data;

public record JwtResponse (
        String accessToken,
        String refreshToken
){
}
