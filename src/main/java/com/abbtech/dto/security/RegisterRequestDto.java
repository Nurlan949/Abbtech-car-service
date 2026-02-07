package com.abbtech.dto.security;

public record RegisterRequestDto(
        String username,
        String fullName,
        String email,
        String password
) {}

