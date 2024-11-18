package com.example.globalsolution_mb

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.Timestamp
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class Registrar_consumo : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private val userId = FirebaseAuth.getInstance().currentUser?.uid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registrar_consumo)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        db = FirebaseFirestore.getInstance()

        val botaoSalvarConsumo = findViewById<Button>(R.id.botaoSalvarConsumo)
        val dataRegistroEditText = findViewById<EditText>(R.id.dataRegistro)
        val consumoKwhEditText = findViewById<EditText>(R.id.consumoKwh)

        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        dateFormat.isLenient = false

        botaoSalvarConsumo.setOnClickListener {
            val dataRegistroString = dataRegistroEditText.text.toString()
            val consumoKwh = consumoKwhEditText.text.toString().toDoubleOrNull()

            try {
                val parsedDate = dateFormat.parse(dataRegistroString)

                if (parsedDate != null && consumoKwh != null) {
                    val dataRegistro = Timestamp(parsedDate)

                    if (userId != null) {
                        val consumoData = hashMapOf(
                            "data_registro" to dataRegistro,
                            "consumo_kwh" to consumoKwh
                        )

                        db.collection("usuarios").document(userId)
                            .collection("consumo")
                            .add(consumoData)
                            .addOnSuccessListener {
                                Toast.makeText(this, "Consumo registrado com sucesso!", Toast.LENGTH_SHORT).show()
                                limparCampos(dataRegistroEditText, consumoKwhEditText) // Limpa os campos após salvar
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(this, "Erro ao salvar consumo: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                    } else {
                        Toast.makeText(this, "Usuário não autenticado.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Por favor, preencha todos os campos corretamente.", Toast.LENGTH_SHORT).show()
                }
            } catch (e: ParseException) {
                Toast.makeText(this, "Formato de data inválido. Use: dd/MM/yyyy", Toast.LENGTH_SHORT).show()
            }
        }

        // Configura o botão voltar para finalizar a Activity
        val botaoVoltar = findViewById<ImageButton>(R.id.botao_voltar)
        botaoVoltar.setOnClickListener {
            finish() // Finaliza a Activity atual, retornando à anterior
        }
    }

    private fun limparCampos(dataRegistroEditText: EditText, consumoKwhEditText: EditText) {
        dataRegistroEditText.text.clear()
        consumoKwhEditText.text.clear()
    }
}
