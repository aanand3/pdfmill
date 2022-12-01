package com.ap.pdfmill.da4856

import android.util.Log
import com.tom_roush.pdfbox.pdmodel.PDDocument
import java.io.ByteArrayOutputStream
import java.io.InputStream


fun exportPdf(inputStream: InputStream?, da4856: Da4856): ByteArrayOutputStream {
    PDDocument.load(inputStream).use { pdDocument ->
        pdDocument.isAllSecurityToBeRemoved = true
        val acroForm = pdDocument.documentCatalog.acroForm.apply { xfa = null }

        // todo: add this type of function call for all the data the user has entered
        acroForm.getField(Da4856Field.NAME.fieldName).apply {
            setValue(da4856.name)
        }

        val result = ByteArrayOutputStream()
        pdDocument.save(result)

        return result
    }
}

