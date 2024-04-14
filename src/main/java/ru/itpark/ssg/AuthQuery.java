package ru.itpark.ssg;

public record AuthQuery(
        String login,
        String password
) {
}
