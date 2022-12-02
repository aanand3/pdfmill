package com.ap.pdfmill.da4856

import android.util.Log
import com.tom_roush.pdfbox.pdmodel.PDDocument
import java.io.ByteArrayOutputStream
import java.io.InputStream


fun exportPdf(inputStream: InputStream?, da4856: Da4856): ByteArrayOutputStream {
    PDDocument.load(inputStream).use { pdDocument ->
        pdDocument.isAllSecurityToBeRemoved = true
        val acroForm = pdDocument.documentCatalog.acroForm.apply { xfa = null }

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

        val result = ByteArrayOutputStream()
        pdDocument.save(result)

        return result
    }
}

