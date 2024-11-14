package com.example.globalsolution_mb

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button

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

        // Configura o botão "Registrar Consumo" para abrir a página Registrar_consumo
        val botaoRegistrarConsumo = findViewById<Button>(R.id.botaoRegistrarConsumo)
        botaoRegistrarConsumo.setOnClickListener {
            val intent = Intent(this, Registrar_consumo::class.java)
            startActivity(intent)
        }
    }
}
