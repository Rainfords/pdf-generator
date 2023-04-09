package net.rainfords.pdfgenerator.service.dto

import org.springframework.core.io.ByteArrayResource
import org.springframework.http.HttpHeaders

data class DocumentDTO(
    val headers: HttpHeaders,
    val content: ByteArrayResource,
    val filename: String?
)
