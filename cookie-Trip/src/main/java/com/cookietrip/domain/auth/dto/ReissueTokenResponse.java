package com.cookietrip.domain.auth.dto;

public record ReissueTokenResponse(
        String newAccessToken
) {
    public static ReissueTokenResponse of(String newAccessToken) {
        return new ReissueTokenResponse(newAccessToken);
    }
}