package net.rainfords.pdfgenerator.service

import addPdfHeaders
import net.rainfords.pdfgenerator.generator.PdfDocumentGenerator
import net.rainfords.pdfgenerator.service.dto.DocumentDTO
import org.springframework.core.io.ByteArrayResource
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Service

@Service
class DocumentGeneratorService(
    val pdfGenerator: PdfDocumentGenerator
) {
    fun createPdf(templateName: String, filename: String, attributes: Map<String, Any?>): DocumentDTO {
        val htmlFile = pdfGenerator.generateHtml(templateName, attributes)
        val pdfFile = pdfGenerator.generatePdf(htmlFile)
        val pdfData = pdfFile.readBytes()
        htmlFile.delete()
        pdfFile.delete()
        return DocumentDTO(
            headers = HttpHeaders().addPdfHeaders(filename, pdfData.size.toLong()),
            content = ByteArrayResource(pdfData),
            filename = filename
        )
    }
}