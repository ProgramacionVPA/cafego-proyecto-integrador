package com.cafego.app

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cafego.app.api.RetrofitClient
import com.cafego.app.models.CartManager
import com.cafego.app.models.InvoiceItemRequest
import com.cafego.app.models.OrderRequest
import com.google.android.material.button.MaterialButton // 🟢 Para el botón moderno
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        val rvCartItems = findViewById<RecyclerView>(R.id.rvCartItems)
        val tvTotal = findViewById<TextView>(R.id.tvTotalAmount)
        // 🟢 Usamos MaterialButton para que coincida con el nuevo XML
        val btnConfirm = findViewById<MaterialButton>(R.id.btnConfirmOrder)

        rvCartItems.layoutManager = LinearLayoutManager(this)
        rvCartItems.adapter = CartAdapter(CartManager.products)

        // Formato profesional para el total (evita los decimales largos)
        tvTotal.text = String.format("TOTAL: $ %.2f", CartManager.getTotal())

        btnConfirm.setOnClickListener {
            if (CartManager.products.isEmpty()) {
                Toast.makeText(this, "El carrito está vacío 🛒", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 1. Obtenemos el ID del usuario que guardamos en el Login
            val idUsuario = CartManager.userIdLogueado

            // 2. Mapeamos explícitamente para evitar errores de inferencia
            val itemsEnvio = CartManager.products.map { producto ->
                InvoiceItemRequest(
                    productId = producto.id,
                    quantity = 1
                )
            }

            // 3. Creamos la orden con la estructura que espera tu Backend
            val orden = OrderRequest(
                userId = idUsuario,
                items = itemsEnvio
            )

            // 4. Envío al servidor (Ruta: /api/invoices)
            RetrofitClient.instance.createOrder(orden).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        Toast.makeText(applicationContext, "¡PEDIDO CONFIRMADO! 🚀", Toast.LENGTH_LONG).show()

                        // 1. Limpiamos la lista en memoria
                        CartManager.clearCart()

                        // 2. Cerramos esta actividad para volver al MenuActivity
                        finish()
                    } else {
                        Toast.makeText(applicationContext, "Error del servidor: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(applicationContext, "Fallo de red: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}