package com.abbtech.dto.security;

public record LoginRequestDto(
        String username,
        String password
) {}

