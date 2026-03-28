# 🛒 Sistema de Gestión de Inventario (Supermercado) - Práctica 2

Este proyecto es una solución **Full-Stack** móvil diseñada para la gestión de productos en tiempo real. La aplicación conecta una interfaz moderna en **Android (Jetpack Compose)** con un backend robusto basado en **Spring Boot** y **PostgreSQL**, todo orquestado mediante **Docker**.

---

## Arquitectura del Sistema

La solución se divide en tres capas principales que interactúan de forma asíncrona:

1.  **Capa de Presentación (Android):** Construida con **Kotlin** y **Jetpack Compose**, siguiendo patrones reactivos para la actualización de la interfaz.
2.  **Capa de Servicios (API REST):** Un servidor **Spring Boot** que expone endpoints protegidos para el CRUD de productos y la gestión de usuarios.
3.  **Capa de Datos:** Base de datos **PostgreSQL** persistida, garantizando la integridad de la información del inventario.

---

## 🛠️ Stack Tecnológico

### Frontend Móvil
*   **Jetpack Compose:** Para una UI declarativa y moderna.
*   **Retrofit 2:** Cliente HTTP para el consumo de la API REST.
*   **Gson:** Serialización y deserialización de objetos JSON.
*   **Navigation Compose:** Gestión del flujo entre pantallas (Login, Registro, Lista, Formulario).
*   **SharedPreferences:** Almacenamiento local del **Token JWT** para sesiones persistentes.

### Backend y DevOps
*   **Docker & Docker Compose:** Contenerización de la base de datos y el servidor.
*   **Spring Boot:** Framework para la lógica de negocio.
*   **PostgreSQL:** Motor de base de datos relacional.

*   ---

##  Funcionalidades Implementadas (CRUD)

| Funcionalidad | Descripción | Método HTTP |
| :--- | :--- | :--- |
| **Autenticación** | Registro e Inicio de sesión con generación de JWT. | `POST` |
| **Lectura** | Visualización dinámica de productos desde el servidor. | `GET` |
| **Escritura** | Alta de nuevos productos con validación de campos. | `POST` |
| **Actualización** | Edición de productos existentes (Nombre, Precio, Stock). | `PUT` |
| **Eliminación** | Borrado físico de registros directamente en la BD. | `DELETE` |

---

##  Configuración del Entorno de Desarrollo

### 1. Despliegue del Backend
Desde la raíz del proyecto donde se encuentra el archivo `docker-compose.yml`, ejecutar:
```bash
docker-compose up -d

Para que el celular físico reconozca al servidor, se configuró la IP local del host en RetrofitClient.kt

private const val BASE_URL = "[http://192.168.](http://192.168.)X.X:8080/api/"
```
---
Captura registro de usuario:

![WhatsApp Image 2026-03-27 at 6 17 54 PM](https://github.com/user-attachments/assets/b93bd76d-4db9-4dc7-b85a-0d9a3974c5b1)

Captura inicio de sesion:

![WhatsApp Image 2026-03-27 at 6 17 54 PM (1)](https://github.com/user-attachments/assets/349fa194-908b-409f-98e2-1f4c4435e50c)

Captura Agregar Producto:

![WhatsApp Image 2026-03-27 at 6 17 54 PM (3)](https://github.com/user-attachments/assets/747620da-6dfa-49ab-bb67-00b50bcd83e8)

![WhatsApp Image 2026-03-27 at 6 17 54 PM (4)](https://github.com/user-attachments/assets/f22cfb23-29f1-438b-9555-b3cfe2ddc38a)

Captura Editar Producto:

![WhatsApp Image 2026-03-27 at 6 17 55 PM](https://github.com/user-attachments/assets/d6374181-b4a9-4373-9ff0-6b04088a1dfc)

Captura Eliminar Prodcuto: 

![WhatsApp Image 2026-03-27 at 6 17 55 PM (1)](https://github.com/user-attachments/assets/340dc342-7b00-4029-9a38-bbfe5c05505a)


## 👥 Equipo de Trabajo

  
*   **Pérez Olivares José Julio**: Desarrollo de Backend y Orquestación Docker.
*   **Nava Villar Eric**: Diseño de UI y Componentes Compose.
*   **Perez Hernandez Tony Saifi**: Integración de API, Gestión de Red y Lógica de Navegación.

---

## 📄 Conclusiones
La práctica permitió comprender el ciclo completo de una petición REST, desde el disparo del evento en la UI hasta la persistencia en disco dentro de un contenedor. La arquitectura implementada asegura que el sistema sea escalable y fácil de mantener.
