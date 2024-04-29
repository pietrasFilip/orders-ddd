package com.app.application.service.pdf;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class PdfServiceImpl implements PdfService{

    @Value("${pdf.path}")
    private String pdfPath;

    private static final Logger logger = LogManager.getRootLogger();

    @Override
    public byte[] generatePdf(List<String> content, String filename) {
        var path = new File(pdfPath + "%s.pdf".formatted(filename))
                .getAbsoluteFile().getPath();
        try (var pdf = new PdfDocument(new PdfWriter(path))){
            var document = new Document(pdf);

            content.forEach(data -> document.add(new Paragraph(data)));
            document.close();
            return Files.readAllBytes(Path.of(path));
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return new byte[] {0};
    }
}
