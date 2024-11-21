package com.example.globalsolution_mb

import android.graphics.Color
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Grafico : AppCompatActivity() {

    private lateinit var lineChart: LineChart
    private lateinit var feedbackMessage: TextView
    private lateinit var db: FirebaseFirestore
    private val userId = FirebaseAuth.getInstance().currentUser?.uid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grafico)

        // botão voltar
        val botaoVoltar = findViewById<ImageButton>(R.id.botao_voltar)
        botaoVoltar.bringToFront()
        botaoVoltar.setOnClickListener {
            finish()
        }

        // inicia de outros componentes
        lineChart = findViewById(R.id.lineChart)
        feedbackMessage = findViewById(R.id.feedbackMessage)
        db = FirebaseFirestore.getInstance()

        lineChart.apply {
            setTouchEnabled(false)
        }

        carregarDados()
    }


    private fun carregarDados() {
        if (userId == null) {
            feedbackMessage.text = "Erro: usuário não autenticado."
            return
        }

        // consulta os dados no Firestore
        db.collection("usuarios").document(userId).collection("consumo")
            .orderBy("data_registro")
            .get()
            .addOnSuccessListener { documents ->
                val consumos = mutableListOf<Pair<Long, Float>>()

                for (document in documents) {
                    val dataRegistro = document.getTimestamp("data_registro")?.toDate()?.time
                    val consumoKwh = document.getDouble("consumo_kwh")?.toFloat()

                    if (dataRegistro != null && consumoKwh != null) {
                        consumos.add(Pair(dataRegistro, consumoKwh))
                    }
                }

                if (consumos.size < 2) {
                    feedbackMessage.text = "Dados insuficientes para comparação."
                    return@addOnSuccessListener
                }

                gerarGrafico(consumos)
            }
            .addOnFailureListener { e ->
                feedbackMessage.text = "Erro ao carregar dados: ${e.message}"
            }
    }

    private fun gerarGrafico(consumos: List<Pair<Long, Float>>) {
        val entries = consumos.mapIndexed { index, pair ->
            Entry(index.toFloat(), pair.second)
        }

        val dataSet = LineDataSet(entries, "Consumo (kWh)").apply {
            color = Color.BLUE
            valueTextColor = Color.BLACK
            lineWidth = 2f
            circleRadius = 4f
        }

        lineChart.apply {
            data = LineData(dataSet)
            description.isEnabled = false
            axisLeft.textColor = Color.BLACK
            axisRight.isEnabled = false
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.textColor = Color.BLACK
            invalidate()
        }

        exibirFeedback(consumos)
    }

    private fun exibirFeedback(consumos: List<Pair<Long, Float>>) {
        val ultimoConsumo = consumos.last().second
        val penultimoConsumo = consumos[consumos.size - 2].second

        feedbackMessage.apply {
            text = if (ultimoConsumo > penultimoConsumo) {
                setTextColor(Color.RED)
                "Atenção! Seu consumo de energia aumentou em ${String.format("%.2f", (ultimoConsumo - penultimoConsumo))} kWh. Considere hábitos e seja mais econômico."
            } else {
                setTextColor(Color.GREEN)
                "Ótimo trabalho! Seu consumo diminuiu em ${String.format("%.2f", (penultimoConsumo - ultimoConsumo))} kWh. Continue assim!"
            }
        }

    }
}
