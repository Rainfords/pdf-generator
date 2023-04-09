import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType

/**
 * Add standard [HttpHeaders] for PDF files,
 * inserting the filename into the Content-Disposition and setting the Content-Length.
 */
fun HttpHeaders.addPdfHeaders(filename: String, length: Long): HttpHeaders {
    contentType = MediaType.APPLICATION_PDF
    contentLength = length
    set("Content-Disposition", "inline; filename=$filename")
    set("Cache-Control", "no-cache, no-store, must-revalidate") // HTTP 1.1.
    set("Pragma", "no-cache")
    set("Expires", "0")

    return this
}

fun HttpHeaders.addHTMLHeaders(): HttpHeaders {
    contentType = MediaType.TEXT_HTML
    set("Cache-Control", "no-cache, no-store, must-revalidate") // HTTP 1.1.
    set("Pragma", "no-cache")
    set("Expires", "0")

    return this
}