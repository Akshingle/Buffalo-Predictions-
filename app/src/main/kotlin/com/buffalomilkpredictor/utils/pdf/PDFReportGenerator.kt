package com.buffalomilkpredictor.utils.pdf

import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import com.buffalomilkpredictor.data.model.BuffaloAnalysis
import com.itextpdf.text.Document
import com.itextpdf.text.Element
import com.itextpdf.text.Font
import com.itextpdf.text.Image
import com.itextpdf.text.PageSize
import com.itextpdf.text.Paragraph
import com.itextpdf.text.Phrase
import com.itextpdf.text.pdf.PdfPCell
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.pdf.PdfWriter
import timber.log.Timber
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PDFReportGenerator(private val context: Context) {

    fun generateReport(analysis: BuffaloAnalysis, imagePaths: List<String> = emptyList()): File? {
        return try {
            val fileName = "Buffalo_Report_${System.currentTimeMillis()}.pdf"
            val file = File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), fileName)

            val document = Document(PageSize.A4)
            PdfWriter.getInstance(document, FileOutputStream(file))

            document.open()

            // Add title
            addTitle(document, analysis)

            // Add basic information
            addBasicInfo(document, analysis)

            // Add analysis scores
            addAnalysisScores(document, analysis)

            // Add milk prediction
            addMilkPrediction(document, analysis)

            // Add recommendation
            addRecommendation(document, analysis)

            // Add images if available
            if (imagePaths.isNotEmpty()) {
                addImages(document, imagePaths)
            }

            // Add diseases and defects
            addDiseasesAndDefects(document, analysis)

            // Add notes
            if (analysis.notes.isNotEmpty()) {
                addNotes(document, analysis.notes)
            }

            document.close()
            Timber.d("PDF report generated: ${file.absolutePath}")
            file
        } catch (e: Exception) {
            Timber.e(e, "Error generating PDF report")
            null
        }
    }

    private fun addTitle(document: Document, analysis: BuffaloAnalysis) {
        val titleFont = Font(Font.FontFamily.HELVETICA, 20f, Font.BOLD)
        val dateFont = Font(Font.FontFamily.HELVETICA, 10f, Font.ITALIC)

        val title = Paragraph("Buffalo Analysis Report", titleFont)
        title.alignment = Element.ALIGN_CENTER
        document.add(title)

        val dateStr = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date())
        val date = Paragraph("Generated: $dateStr", dateFont)
        date.alignment = Element.ALIGN_CENTER
        document.add(date)

        document.add(Paragraph("\n"))
    }

    private fun addBasicInfo(document: Document, analysis: BuffaloAnalysis) {
        val sectionFont = Font(Font.FontFamily.HELVETICA, 14f, Font.BOLD)
        val contentFont = Font(Font.FontFamily.HELVETICA, 11f)

        document.add(Paragraph("Basic Information", sectionFont))

        val table = PdfPTable(2)
        table.widthPercentage = 100f

        addTableRow(table, "Breed", analysis.breed, contentFont)
        addTableRow(table, "Breed Confidence", "${(analysis.breedConfidence * 100).toInt()}%", contentFont)
        addTableRow(table, "Estimated Age", analysis.ageEstimate, contentFont)
        addTableRow(table, "Pregnancy Status", if (analysis.isPregnant) "Yes" else "No", contentFont)
        addTableRow(table, "Analysis Date", SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(analysis.analysisDate), contentFont)

        document.add(table)
        document.add(Paragraph("\n"))
    }

    private fun addAnalysisScores(document: Document, analysis: BuffaloAnalysis) {
        val sectionFont = Font(Font.FontFamily.HELVETICA, 14f, Font.BOLD)
        val contentFont = Font(Font.FontFamily.HELVETICA, 11f)

        document.add(Paragraph("Analysis Scores", sectionFont))

        val table = PdfPTable(2)
        table.widthPercentage = 100f

        addTableRow(table, "Body Frame Score", "%.2f/1.00".format(analysis.bodyFrameScore), contentFont)
        addTableRow(table, "Body Condition Score", "%.2f/1.00".format(analysis.bodyConditionScore), contentFont)
        addTableRow(table, "Udder Score", "%.2f/1.00".format(analysis.udderScore), contentFont)
        addTableRow(table, "Teat Score", "%.2f/1.00".format(analysis.teatScore), contentFont)
        addTableRow(table, "Leg Score", "%.2f/1.00".format(analysis.legScore), contentFont)
        addTableRow(table, "Disease Score", "%.2f/1.00".format(analysis.diseaseScore), contentFont)
        addTableRow(table, "Walking Score", "%.2f/1.00".format(analysis.walkingScore), contentFont)
        addTableRow(table, "Overall Confidence", "%.2f/1.00".format(analysis.overallConfidence), contentFont)

        document.add(table)
        document.add(Paragraph("\n"))
    }

    private fun addMilkPrediction(document: Document, analysis: BuffaloAnalysis) {
        val sectionFont = Font(Font.FontFamily.HELVETICA, 14f, Font.BOLD)
        val contentFont = Font(Font.FontFamily.HELVETICA, 11f)

        document.add(Paragraph("Milk Production Prediction", sectionFont))

        val table = PdfPTable(2)
        table.widthPercentage = 100f

        addTableRow(table, "Prediction Category", analysis.milkPredictionCategory, contentFont)
        addTableRow(table, "Expected Production Range", analysis.milkProductionRange, contentFont)

        document.add(table)
        document.add(Paragraph("\n"))
    }

    private fun addRecommendation(document: Document, analysis: BuffaloAnalysis) {
        val sectionFont = Font(Font.FontFamily.HELVETICA, 14f, Font.BOLD)
        val contentFont = Font(Font.FontFamily.HELVETICA, 11f)

        document.add(Paragraph("Buy Recommendation", sectionFont))

        val recommendations = mapOf(
            "Excellent Purchase" to "Highly recommended. Buffalo shows excellent milk production potential with good health indicators.",
            "Good Purchase" to "Recommended. Buffalo has good potential but may have minor issues to monitor.",
            "Average" to "Consider carefully. Buffalo has average potential with some concerns.",
            "Avoid Buying" to "Not recommended. Buffalo has significant health or production concerns.",
            "Reject Immediately" to "Do not purchase. Buffalo has critical health or structural defects."
        )

        val table = PdfPTable(1)
        table.widthPercentage = 100f

        val cell = PdfPCell(Paragraph(analysis.buyRecommendation, Font(Font.FontFamily.HELVETICA, 12f, Font.BOLD)))
        cell.backgroundColor = when (analysis.buyRecommendation) {
            "Excellent Purchase" -> com.itextpdf.text.BaseColor(76, 175, 80)
            "Good Purchase" -> com.itextpdf.text.BaseColor(255, 193, 7)
            "Average" -> com.itextpdf.text.BaseColor(255, 152, 0)
            "Avoid Buying" -> com.itextpdf.text.BaseColor(255, 87, 34)
            else -> com.itextpdf.text.BaseColor(244, 67, 54)
        }
        table.addCell(cell)

        document.add(table)

        val explanation = Paragraph(
            recommendations[analysis.buyRecommendation] ?: "No explanation available.",
            contentFont
        )
        document.add(explanation)
        document.add(Paragraph("\n"))
    }

    private fun addImages(document: Document, imagePaths: List<String>) {
        val sectionFont = Font(Font.FontFamily.HELVETICA, 14f, Font.BOLD)

        document.add(Paragraph("Captured Images", sectionFont))

        for (imagePath in imagePaths.take(4)) { // Limit to 4 images
            try {
                val img = Image.getInstance(imagePath)
                img.scaleToFit(400f, 300f)
                img.alignment = Element.ALIGN_CENTER
                document.add(img)
                document.add(Paragraph("\n"))
            } catch (e: Exception) {
                Timber.w(e, "Could not add image: $imagePath")
            }
        }
    }

    private fun addDiseasesAndDefects(document: Document, analysis: BuffaloAnalysis) {
        if (analysis.diseaseDetected.isEmpty() && analysis.defectsDetected.isEmpty()) {
            return
        }

        val sectionFont = Font(Font.FontFamily.HELVETICA, 14f, Font.BOLD)
        val contentFont = Font(Font.FontFamily.HELVETICA, 11f)

        document.add(Paragraph("Diseases & Defects Detected", sectionFont))

        if (analysis.diseaseDetected.isNotEmpty()) {
            document.add(Paragraph("Diseases:", Font(Font.FontFamily.HELVETICA, 12f, Font.BOLD)))
            for (disease in analysis.diseaseDetected) {
                document.add(Paragraph("• $disease", contentFont))
            }
        }

        if (analysis.defectsDetected.isNotEmpty()) {
            document.add(Paragraph("Defects:", Font(Font.FontFamily.HELVETICA, 12f, Font.BOLD)))
            for (defect in analysis.defectsDetected) {
                val severity = when (defect.severity) {
                    com.buffalomilkpredictor.data.model.DefectSeverity.RED -> "[CRITICAL]"
                    com.buffalomilkpredictor.data.model.DefectSeverity.YELLOW -> "[WARNING]"
                    com.buffalomilkpredictor.data.model.DefectSeverity.GREEN -> "[MINOR]"
                }
                document.add(Paragraph("• $severity ${defect.name} - ${defect.location}", contentFont))
            }
        }

        document.add(Paragraph("\n"))
    }

    private fun addNotes(document: Document, notes: String) {
        val sectionFont = Font(Font.FontFamily.HELVETICA, 14f, Font.BOLD)
        val contentFont = Font(Font.FontFamily.HELVETICA, 11f)

        document.add(Paragraph("Notes", sectionFont))
        document.add(Paragraph(notes, contentFont))
        document.add(Paragraph("\n"))
    }

    private fun addTableRow(table: PdfPTable, label: String, value: String, font: Font) {
        val labelCell = PdfPCell(Paragraph(label, Font(Font.FontFamily.HELVETICA, 11f, Font.BOLD)))
        labelCell.backgroundColor = com.itextpdf.text.BaseColor(200, 200, 200)
        table.addCell(labelCell)

        val valueCell = PdfPCell(Paragraph(value, font))
        table.addCell(valueCell)
    }
}
