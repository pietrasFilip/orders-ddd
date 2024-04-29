package com.app.infrastructure.api.events.listener;

import com.app.application.dto.pdf.CreatePdfForEmailDto;
import com.app.application.service.pdf.PdfService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class CreatePdfListener {
    private final PdfService pdfService;
    private final ApplicationEventPublisher applicationEventPublisher;
    private static final Logger logger = LogManager.getRootLogger();

    @Async
    @EventListener
    public void createPdf(CreatePdfForEmailDto createPdfForEmailDto) {
        logger.info("Creating pdf file...");
        pdfService.generatePdf(createPdfForEmailDto.pdfContent(), createPdfForEmailDto.createEmailDto().filename());

        logger.info("Pdf file created.");
        applicationEventPublisher.publishEvent(createPdfForEmailDto.createEmailDto());
    }
}
