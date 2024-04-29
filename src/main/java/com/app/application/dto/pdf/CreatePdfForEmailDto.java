package com.app.application.dto.pdf;

import com.app.application.dto.email.CreateEmailDto;

import java.util.List;

public record CreatePdfForEmailDto(CreateEmailDto createEmailDto, List<String> pdfContent) {
}
