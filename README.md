# ☕ CafeGo - Backend API

Proyecto Integrador Final desarrollado en **Kotlin** y **Spring Boot**.
API REST para la gestión de pedidos, productos y facturación de una cafetería, utilizando una arquitectura limpia y base de datos contenerizada.

---

## 🛠️ Tecnologías Utilizadas

* **Lenguaje:** Kotlin
* **Framework:** Spring Boot 3
* **Base de Datos:** PostgreSQL (Docker)
* **Persistencia:** Spring Data JPA
* **Testing:** JUnit 5 + Mockito (100% Coverage en Servicios)
* **Herramientas:** Docker Compose, Gradle, Postman.

---

## 🚀 1. Cómo levantar el entorno (Base de Datos)

Este proyecto utiliza una base de datos **PostgreSQL** externa ejecutada mediante Docker Compose. No se utiliza base de datos en memoria (H2).

**Requisito previo:** Tener Docker Desktop instalado y corriendo.

1.  Abrir una terminal en la raíz del proyecto.
2.  Ejecutar el siguiente comando para descargar y levantar el contenedor de la base de datos:


docker compose up -d

Verificar que el contenedor está corriendo:

docker ps

(Debería aparecer un contenedor llamado postgres-db o similar en el puerto 5432).

▶️ 2. Cómo ejecutar la aplicación

Una vez que la base de datos está corriendo (Paso 1):

Desde IntelliJ IDEA / Android Studio:

Abrir el archivo principal BackendApplication.kt (o la clase Main).

Hacer clic en el botón Run (Triángulo verde) al lado de la clase.

Desde Terminal:

./gradlew bootRun

La API estará disponible en: http://localhost:8080

🧪 3. Cómo correr Tests y ver Coverage

El proyecto cumple con el requisito obligatorio de 100% de Coverage en la capa de Servicios (Services).

Ejecutar Tests:

./gradlew test

Ver Reporte de Coverage (IntelliJ IDEA):

En el explorador del proyecto, ir a: src/test/kotlin.

Hacer clic derecho sobre la carpeta services.

Seleccionar la opción: "Run 'Tests in 'services'' with Coverage".

El reporte aparecerá en el panel lateral derecho, mostrando el 100% de cobertura en Clases, Métodos y Líneas.

📡 4. Colección de Endpoints (Postman)

Se incluye el archivo de colección completo en la raíz de este repositorio: 📄 Archivo: postman_collection.json

Instrucciones de uso:

Abrir Postman.

Hacer clic en el botón Import (esquina superior izquierda).

Arrastrar o seleccionar el archivo postman_collection.json de este proyecto.

Una vez importado, verá una carpeta "CafeGo_integrador" con todos los endpoints organizados.

Asegúrese de que el servidor esté corriendo y presione Send en cualquier petición.

Endpoints Principales:
Productos: GET /api/products, POST /api/products

Usuarios: POST /api/users/identify, GET /api/users

Facturas: POST /api/invoices (Crear pedido), PUT /api/invoices/{id}/dispatch (Despachar).

