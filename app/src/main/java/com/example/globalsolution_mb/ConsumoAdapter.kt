package com.example.globalsolution_mb

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class ConsumoAdapter(
    private val consumos: List<Consumo>,
    private val onEditClick: (Consumo) -> Unit,
    private val onDeleteClick: (Consumo) -> Unit
) : RecyclerView.Adapter<ConsumoAdapter.ConsumoViewHolder>() {

    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    inner class ConsumoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val dataRegistro: TextView = view.findViewById(R.id.textDataRegistro)
        val consumoKwh: TextView = view.findViewById(R.id.textConsumoKwh)
        val buttonEdit: Button = view.findViewById(R.id.buttonEdit)
        val buttonDelete: Button = view.findViewById(R.id.buttonDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConsumoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_consumo, parent, false)
        return ConsumoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ConsumoViewHolder, position: Int) {
        val consumo = consumos[position]
        holder.dataRegistro.text = dateFormat.format(consumo.dataRegistro)
        holder.consumoKwh.text = "${consumo.consumoKwh} kWh"
        holder.buttonEdit.setOnClickListener { onEditClick(consumo) }
        holder.buttonDelete.setOnClickListener { onDeleteClick(consumo) }
    }

    override fun getItemCount() = consumos.size
}

