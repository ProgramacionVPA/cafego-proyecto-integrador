package com.cafego.app

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cafego.app.api.RetrofitClient
import com.cafego.app.models.CartManager
import com.cafego.app.models.User
import com.cafego.app.models.UserRequest
import com.google.android.material.button.MaterialButton // Importante para el nuevo botón
import com.google.android.material.textfield.TextInputEditText // Importante para el nuevo campo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 🟢 Ajustamos los tipos de vista al nuevo diseño Premium
        val etCedula = findViewById<TextInputEditText>(R.id.etCedula)
        val btnLogin = findViewById<MaterialButton>(R.id.btnLogin)

        btnLogin.setOnClickListener {
            val cedula = etCedula.text.toString()

            if (cedula.isEmpty()) {
                // Usamos el mensaje de error del propio diseño para que se vea Pro
                etCedula.error = "Ingresa tu cédula"
                return@setOnClickListener
            }

            // Datos para identificar al usuario
            val request = UserRequest(cedula, "Cliente", "email@test.com")

            RetrofitClient.instance.identifyUser(request).enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful) {
                        val usuario = response.body()

                        if (usuario != null) {
                            // Guardamos el ID que el servidor nos devuelve
                            CartManager.userIdLogueado = usuario.id
                            Toast.makeText(applicationContext, "¡Bienvenido, ${usuario.fullName}!", Toast.LENGTH_SHORT).show()
                        }

                        // Navegación al Menú
                        startActivity(Intent(applicationContext, MenuActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(applicationContext, "Cédula no reconocida", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Toast.makeText(applicationContext, "Error de conexión: Verifica tu servidor", Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}