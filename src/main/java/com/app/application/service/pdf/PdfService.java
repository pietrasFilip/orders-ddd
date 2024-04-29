package com.app.application.service.pdf;

import java.util.List;

public interface PdfService {
    byte[] generatePdf(List<String> content, String filename);
}
