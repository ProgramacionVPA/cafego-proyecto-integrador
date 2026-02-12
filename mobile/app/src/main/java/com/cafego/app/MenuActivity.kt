package com.cafego.app

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cafego.app.api.RetrofitClient
import com.cafego.app.models.Product
import com.google.android.material.badge.BadgeDrawable // Importante
import com.google.android.material.badge.BadgeUtils    // Importante
import com.google.android.material.badge.ExperimentalBadgeUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// Esta línea elimina las advertencias rojas en toda la clase
@androidx.annotation.OptIn(ExperimentalBadgeUtils::class)
class MenuActivity : AppCompatActivity() {

    // 1. VARIABLE GLOBAL: Aquí guardamos "el globito" para no perderlo
    private var badge: BadgeDrawable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val rvProducts = findViewById<RecyclerView>(R.id.rvProducts)
        rvProducts.layoutManager = LinearLayoutManager(this)

        val btnCart = findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.btnGoToCart)

        btnCart.setOnClickListener {
            val intent = android.content.Intent(this, CartActivity::class.java)
            startActivity(intent)
        }

        RetrofitClient.instance.getProducts().enqueue(object : Callback<List<Product>> {
            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                if (response.isSuccessful) {
                    val products = response.body() ?: emptyList()
                    rvProducts.adapter = ProductAdapter(products)
                }
            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                Toast.makeText(applicationContext, "Error al cargar menú", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        // Cada vez que volvemos a esta pantalla, refrescamos el número
        updateCartBadge()
    }

    fun updateCartBadge() {
        val btnCart = findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.btnGoToCart)
        val count = com.cafego.app.models.CartManager.products.size

        if (count > 0) {
            // Si el badge NO existe todavía, lo creamos UNA SOLA VEZ
            if (badge == null) {
                badge = BadgeDrawable.create(this)
                badge?.backgroundColor = android.graphics.Color.parseColor("#FF7043")
                badge?.badgeGravity = BadgeDrawable.TOP_END
                // Lo pegamos al botón
                BadgeUtils.attachBadgeDrawable(badge!!, btnCart)
            }

            // Actualizamos el número y aseguramos que se vea
            badge?.number = count
            badge?.isVisible = true

        } else {
            // SI EL CARRITO ESTÁ VACÍO:
            // Simplemente lo ocultamos. Esto es mucho más seguro que intentar "borrarlo".
            badge?.isVisible = false
        }
    }
}