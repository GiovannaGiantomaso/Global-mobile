package com.example.globalsolution_mb

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Principal : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_principal)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        
        val botaoRegistrarConsumo = findViewById<Button>(R.id.botaoRegistrarConsumo)
        botaoRegistrarConsumo.setOnClickListener {
            val intent = Intent(this, Registrar_consumo::class.java)
            startActivity(intent)
        }

        val botaoVerHistorico = findViewById<Button>(R.id.botaoVerHistorico)
        botaoVerHistorico.setOnClickListener {
            val intent = Intent(this, Ver_consumo::class.java)
            startActivity(intent)
        }

        val botaoExibirGrafico = findViewById<Button>(R.id.botaoExibirGrafico)
        botaoExibirGrafico.setOnClickListener {
            val intent = Intent(this, Grafico::class.java)
            startActivity(intent)
        }
    }
}
