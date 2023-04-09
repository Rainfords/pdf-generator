package net.rainfords.pdfgenerator.generator


import com.itextpdf.html2pdf.ConverterProperties
import com.itextpdf.html2pdf.HtmlConverter
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Service
import org.thymeleaf.context.Context
import org.thymeleaf.spring6.SpringTemplateEngine
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver
import org.thymeleaf.templatemode.TemplateMode
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter


@Service
class PdfDocumentGenerator(
    val templateEngine: SpringTemplateEngine,
    val applicationContext: ApplicationContext
) {
    init {
        initEngine()
    }

    private fun initEngine() {
        val templateResolver = SpringResourceTemplateResolver()
        with(templateResolver) {
            suffix = ".html"
            setApplicationContext(applicationContext)
            templateMode = TemplateMode.HTML
            isCacheable = true
            order = 1
            prefix = "classpath:/templates/"
            characterEncoding = "UTF-8"
        }
        templateEngine.setTemplateResolver(templateResolver)
    }

    private fun createTempOutputFile(prefix: String): File = File.createTempFile(prefix, "pdfGen")
    fun generateHtml(templateName: String, attributes: Map<String, Any?>): File {
        val tempFile = createTempOutputFile(templateName)
        val writer = BufferedWriter(FileWriter(tempFile))

        writer.use { it ->
            it.write(Context().apply
            { setVariables(attributes) }.let
            { templateEngine.process(templateName, it) })
        }
        return tempFile
    }

    fun generatePdf(htmlFile: File): File {
        val pdfFile = createTempOutputFile("pdf")
        val defaultFont = DefaultFontProvider(false, true, false)
        val converterProperties = ConverterProperties()
        converterProperties.setFontProvider(defaultFont)
        HtmlConverter.convertToPdf(htmlFile, pdfFile, converterProperties)
        return pdfFile
    }

}