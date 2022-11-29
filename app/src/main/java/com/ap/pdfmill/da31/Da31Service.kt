package com.ap.pdfmill.da31

import android.util.Log
import com.tom_roush.pdfbox.pdmodel.PDDocument
import com.tom_roush.pdfbox.pdmodel.interactive.form.PDField
import com.tom_roush.pdfbox.pdmodel.interactive.form.PDTerminalField
import java.io.ByteArrayOutputStream
import java.io.InputStream


fun exportPdf(inputStream: InputStream?, da31: Da31): ByteArrayOutputStream {
    PDDocument.load(inputStream).use { pdDocument ->
        pdDocument.isAllSecurityToBeRemoved = true
        val acroForm = pdDocument.documentCatalog.acroForm.apply { xfa = null }

        // this (should) print out all the names of the fields in the form
        // we should end up seeing something like these:
        // https://gitlab.create.army.mil/mysquad/mysquad-bhl/-/blob/main/backend/src/main/kotlin/mil/army/futures/mysquad/counseling/export/Da4856Field.kt
        // might need to play around with different DA31's, specifically one that will open in Chrome/Preview and isn't Adobe locked like the ArmyPubs one
        val fieldTreeIterator: Iterator<PDField> = acroForm.fieldTree.iterator()
        while (fieldTreeIterator.hasNext()) {
            val field = fieldTreeIterator.next()
            if (field is PDTerminalField) {
                val fullyQualifiedName = field.getFullyQualifiedName()
                Log.d("Field name", fullyQualifiedName)
            }
        }

        // this is where we would do something like this for each field
        acroForm.fields.forEach {
            it.apply { setValue(da31.name) }
        }

        val result = ByteArrayOutputStream()
        pdDocument.save(result)

        return result
    }
}

