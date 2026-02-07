package com.abbtech.dto.security;

public record TokenResponseDto(
        String accessToken,
        String refreshToken
) {}

