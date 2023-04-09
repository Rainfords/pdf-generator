package net.rainfords.pdfgenerator.controller

import net.rainfords.mappers.EmployeeMapper
import net.rainfords.model.EmployeeDTO
import net.rainfords.pdfgenerator.service.DocumentGeneratorService
import org.springframework.core.io.ByteArrayResource
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class DocumentGeneratorController(
    val documentGeneratorService: DocumentGeneratorService
) {

    @PostMapping("employee/document")
    fun createDocument(@RequestBody body: EmployeeDTO): ResponseEntity<ByteArrayResource> {
        val mapper = EmployeeMapper().setData(body)
        val documentData = documentGeneratorService.createPdf("employee/template", "test.pdf", mapper)
        return ResponseEntity.ok()
            .headers(documentData.headers)
            .body(documentData.content)
    }
}