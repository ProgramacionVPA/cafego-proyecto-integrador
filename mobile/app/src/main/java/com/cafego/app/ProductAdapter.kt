package com.cafego.app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cafego.app.models.Product

class ProductAdapter(private var products: List<Product>) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.tvProductName)
        val description: TextView = view.findViewById(R.id.tvProductDescription)
        val price: TextView = view.findViewById(R.id.tvProductPrice)
        val btnAdd: Button = view.findViewById(R.id.btnAdd)
        val cgTags: com.google.android.material.chip.ChipGroup = view.findViewById(R.id.cgTags)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]
        holder.name.text = product.name
        holder.description.text = product.description ?: "Sin descripción disponible" // Usa el campo del backend
        holder.price.text = String.format("$ %.2f", product.price)

        // --- ESTO ES LO NUEVO ---
        holder.btnAdd.setOnClickListener {
            // 1. Guardar en el carrito (ya lo tienes)
            com.cafego.app.models.CartManager.addProduct(product)

            // 2. Avisar al usuario (ya lo tienes)
            android.widget.Toast.makeText(holder.itemView.context, "Agregado: ${product.name}", android.widget.Toast.LENGTH_SHORT).show()

            // 3. ACTUALIZAR EL CONTADOR (NUEVO)
            (holder.itemView.context as? MenuActivity)?.updateCartBadge()
        }
        // 1. Limpiar etiquetas anteriores para evitar que se repitan al bajar y subir
        holder.cgTags.removeAllViews()

// 2. Si el producto tiene tags, creamos un Chip por cada uno
        product.tags?.forEach { tagName: String -> // 👈 Añadimos ": String" para ayudar al compilador
            val chip = com.google.android.material.chip.Chip(holder.itemView.context)
            chip.text = tagName
            chip.setChipBackgroundColorResource(R.color.tag_background)
            chip.setTextColor(android.graphics.Color.BLACK)
            holder.cgTags.addView(chip)
        }

        // ------------------------
    }

    override fun getItemCount() = products.size
}