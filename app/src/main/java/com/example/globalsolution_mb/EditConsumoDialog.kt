package com.example.globalsolution_mb

import android.app.Dialog
import android.content.Context
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class EditConsumoDialog(
    context: Context,
    private val consumo: Consumo,
    private val onSave: (Consumo) -> Unit
) : Dialog(context) {

    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    init {
        setContentView(R.layout.dialog_edit_consumo)

        val editDataRegistro = findViewById<EditText>(R.id.editDataRegistro)
        val editConsumoKwh = findViewById<EditText>(R.id.editConsumoKwh)
        val buttonSave = findViewById<Button>(R.id.buttonSave)
        val buttonCancel = findViewById<Button>(R.id.buttonCancel)

        editDataRegistro?.setText(dateFormat.format(consumo.dataRegistro))
        editConsumoKwh?.setText(consumo.consumoKwh.toString())

        buttonSave?.setOnClickListener {
            val novaDataString = editDataRegistro?.text.toString()
            val novoConsumoKwh = editConsumoKwh?.text.toString().toDoubleOrNull()

            try {
                val novaData = dateFormat.parse(novaDataString)

                if (novaData != null && novoConsumoKwh != null) {
                    val novoConsumo = Consumo(novaData, novoConsumoKwh, consumo.id)
                    onSave(novoConsumo)
                    dismiss()
                } else {
                    Toast.makeText(context, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
                }
            } catch (e: ParseException) {
                Toast.makeText(context, "Formato de data inválido.", Toast.LENGTH_SHORT).show()
            }
        }

        buttonCancel?.setOnClickListener {
            dismiss()
        }
    }
}
