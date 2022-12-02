package com.ap.pdfmill.da4856

import android.graphics.Bitmap
import com.tom_roush.pdfbox.pdmodel.PDDocument
import com.tom_roush.pdfbox.pdmodel.PDPageContentStream
import com.tom_roush.pdfbox.pdmodel.common.PDRectangle
import com.tom_roush.pdfbox.pdmodel.graphics.image.JPEGFactory
import com.tom_roush.pdfbox.pdmodel.interactive.form.PDAcroForm
import java.io.ByteArrayOutputStream
import java.io.InputStream


fun exportPdf(inputStream: InputStream?, da4856: Da4856, signature: Bitmap): ByteArrayOutputStream {
    PDDocument.load(inputStream).use { pdDocument ->
        pdDocument.isAllSecurityToBeRemoved = true
        val acroForm = pdDocument.documentCatalog.acroForm.apply { xfa = null }

        fillTextFields(acroForm, da4856)

        fillSignatureField(
            acroForm.getField(Da4856Field.SIGNATURE_COUNSELOR.fieldName).widgets[0].rectangle,
            pdDocument,
            signature
        )

        val result = ByteArrayOutputStream()
        pdDocument.save(result)

        return result
    }
}

fun fillSignatureField(
    pdRectangle: PDRectangle,
    pdfDocument: PDDocument,
    signature: Bitmap,
) {
    val scalingFactor = pdRectangle.height / signature.height

    PDPageContentStream(
        pdfDocument,
        pdfDocument.getPage(1),
        PDPageContentStream.AppendMode.APPEND,
        true,
        true
    )
        .use {
            it.drawImage(
                JPEGFactory.createFromImage(PDDocument(), signature),
                pdRectangle.lowerLeftX,
                pdRectangle.lowerLeftY,
                signature.width * scalingFactor + 100,
                signature.height * scalingFactor
            )
        }
}

private fun fillTextFields(
    acroForm: PDAcroForm,
    da4856: Da4856
) {
    acroForm.getField(Da4856Field.NAME.fieldName).apply {
        setValue(da4856.name)
    }
    acroForm.getField(Da4856Field.DATE_COUNSELING.fieldName).apply {
        setValue(da4856.date)
    }
    acroForm.getField(Da4856Field.RANK_GRADE.fieldName).apply {
        setValue(da4856.rank)
    }
    acroForm.getField(Da4856Field.ORGANIZATION.fieldName).apply {
        setValue(da4856.organization)
    }
    acroForm.getField(Da4856Field.NAME_TITLE_COUNSELOR.fieldName).apply {
        setValue(da4856.counselorTitle)
    }
    acroForm.getField(Da4856Field.PURPOSE_COUNSELING.fieldName).apply {
        setValue(da4856.purpose)
    }
    acroForm.getField(Da4856Field.PLAN_OF_ACTION.fieldName).apply {
        setValue(da4856.planOfAction)
    }
}

