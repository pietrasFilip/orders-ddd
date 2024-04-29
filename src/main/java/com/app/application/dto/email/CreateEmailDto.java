package com.app.application.dto.email;

public record CreateEmailDto(String email, String filename, String subject, String emailContent) {
}
