package com.example.globalsolution_mb

import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class Ver_consumo : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var consumoAdapter: ConsumoAdapter
    private val consumos = mutableListOf<Consumo>()
    private lateinit var db: FirebaseFirestore
    private val userId = FirebaseAuth.getInstance().currentUser?.uid

    private var lastDocumentSnapshot: DocumentSnapshot? = null
    private var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ver_consumo)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val botaoVoltar = findViewById<ImageButton>(R.id.botao_voltar)
        botaoVoltar.setOnClickListener {
            finish()
        }

        db = FirebaseFirestore.getInstance()

        recyclerView = findViewById(R.id.recyclerViewConsumo)
        consumoAdapter = ConsumoAdapter(consumos, { consumo ->
            editarConsumo(consumo)
        }, { consumo ->
            deletarConsumo(consumo)
        })

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = consumoAdapter

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                if (!isLoading && layoutManager.findLastVisibleItemPosition() == consumos.size - 1) {
                    carregarConsumos(paginar = true)
                }
            }
        })

        carregarConsumos()
    }

    private fun carregarConsumos(paginar: Boolean = false) {
        if (userId == null) {
            Toast.makeText(this, "Usuário não autenticado.", Toast.LENGTH_SHORT).show()
            return
        }

        if (isLoading) return
        isLoading = true

        val query = db.collection("usuarios").document(userId).collection("consumo")
            .orderBy("data_registro")
            .limit(15)

        val finalQuery = if (paginar && lastDocumentSnapshot != null) {
            query.startAfter(lastDocumentSnapshot)
        } else {
            query
        }

        finalQuery.get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    Toast.makeText(this, "Nenhum consumo adicional encontrado.", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    lastDocumentSnapshot = documents.documents.last()
                    for (document in documents) {
                        val dataRegistro = document.getTimestamp("data_registro")?.toDate() ?: Date()
                        val consumoKwh = document.getDouble("consumo_kwh") ?: 0.0
                        consumos.add(Consumo(dataRegistro, consumoKwh, document.id))
                    }
                    consumoAdapter.notifyDataSetChanged()
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Erro ao carregar consumos: ${e.message}", Toast.LENGTH_SHORT)
                    .show()
            }
            .addOnCompleteListener {
                isLoading = false
            }
    }

    private fun editarConsumo(consumo: Consumo) {
        val dialog = EditConsumoDialog(this, consumo) { novoConsumo ->
            val docRef = db.collection("usuarios").document(userId!!).collection("consumo")
                .document(consumo.id)

            docRef.update(
                "data_registro", novoConsumo.dataRegistro,
                "consumo_kwh", novoConsumo.consumoKwh
            ).addOnSuccessListener {
                Toast.makeText(this, "Consumo atualizado com sucesso!", Toast.LENGTH_SHORT).show()

                val index = consumos.indexOfFirst { it.id == consumo.id }
                if (index != -1) {
                    consumos[index] = novoConsumo
                    consumoAdapter.notifyItemChanged(index)
                }
            }.addOnFailureListener { e ->
                Toast.makeText(this, "Erro ao atualizar consumo: ${e.message}", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        dialog.show()
    }

    private fun deletarConsumo(consumo: Consumo) {
        val docRef = db.collection("usuarios").document(userId!!).collection("consumo")
            .document(consumo.id)

        docRef.delete()
            .addOnSuccessListener {
                Toast.makeText(this, "Consumo deletado com sucesso!", Toast.LENGTH_SHORT).show()
                consumos.remove(consumo)
                consumoAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Erro ao deletar consumo: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}

