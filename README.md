# Práctica 2: Operaciones CRUD con Servicio REST Autenticado

* **Integrantes del Equipo:**
  1. Pérez Olivares José Julio - *(Arquitectura Backend y Docker)*
  2. [Nombre del Integrante 2] - *(Diseño UX/UI Android)*
  3. [Nombre del Integrante 3] - *(Integración y Cliente HTTP)*

---

El proyecto consiste en el desarrollo de una aplicación móvil nativa en Android capaz de realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sobre una entidad de "Productos" (simulando el inventario de un supermercado) a través de un servicio RESTful. 

Para garantizar la seguridad y persistencia de los datos, la lógica se dividió en una arquitectura cliente-servidor con las siguientes características:
* **Backend:** Construido con Spring Boot (Java), encargado de exponer los endpoints REST.
* **Base de Datos y Contenedores:** Se utilizó PostgreSQL como motor de base de datos. Todo el entorno del servidor fue dockerizado utilizando `Dockerfile` y `docker-compose.yml` para asegurar la portabilidad y facilitar la ejecución del proyecto.
* **Seguridad y Autenticación:** Se implementó un sistema de registro y login. Las contraseñas de los usuarios no se almacenan en texto plano, sino que son encriptadas utilizando el algoritmo **BCrypt**. Para el manejo de sesiones seguras y stateless (sin estado), se integró **JWT (JSON Web Tokens)**. El dispositivo móvil debe enviar este token en los *Headers* de cada petición para ser autorizado por el servidor.
* **Frontend (Móvil):** Interfaz desarrollada en Android (XML/Jetpack Compose) con navegación intuitiva, consumiendo la API mediante un cliente HTTP (Retrofit) apuntando a la dirección local del emulador (`10.0.2.2`).
