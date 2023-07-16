package br.ifnmg.edu.partyrent.modules.users.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

public class LoginResponseDTO {
    UUID userId;
    LocalDateTime expiresAt;
    String token;

    public LoginResponseDTO(UUID userId, LocalDateTime expiresAt, String token) {
        this.userId = userId;
        this.expiresAt = expiresAt;
        this.token = token;
    }

    public UUID getUserId() {
        return userId;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public String getToken() {
        return token;
    }
}
