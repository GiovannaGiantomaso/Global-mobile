package com.example.globalsolution_mb

import android.app.Dialog
import android.content.Context
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class EditConsumoDialog(
    context: Context,
    private val consumo: Consumo,
    private val onSave: (Consumo) -> Unit
) : Dialog(context) {

    init {
        setContentView(R.layout.dialog_edit_consumo)

        val editDataRegistro = findViewById<EditText>(R.id.editDataRegistro)
        val editConsumoKwh = findViewById<EditText>(R.id.editConsumoKwh)
        val buttonSave = findViewById<Button>(R.id.buttonSave)
        val buttonCancel = findViewById<Button>(R.id.buttonCancel)

        editDataRegistro?.setText(consumo.dataRegistro)
        editConsumoKwh?.setText(consumo.consumoKwh.toString())

        buttonSave?.setOnClickListener {
            val novaData = editDataRegistro?.text.toString()
            val novoConsumoKwh = editConsumoKwh?.text.toString().toDoubleOrNull()

            if (novaData.isNotEmpty() && novoConsumoKwh != null) {
                val novoConsumo = Consumo(novaData, novoConsumoKwh, consumo.id)
                onSave(novoConsumo)
                dismiss()
            } else {
                Toast.makeText(context, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
            }
        }

        buttonCancel?.setOnClickListener {
            dismiss()
        }
    }
}
