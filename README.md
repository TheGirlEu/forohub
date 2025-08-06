# 💬 MiniForo - API REST de Práctica con Spring Boot

**MiniForo** es una API REST creada como parte de mi aprendizaje en desarrollo backend con **Java** y **Spring Boot**. Permite crear y gestionar tópicos de discusión y respuestas entre usuarios.  
Aún tiene algunos errores que iré corrigiendo más adelante, ya que sigo practicando y mejorando día a día 🚧🙂.

---

## ⚙️ Funcionalidades principales

- Crear, listar, actualizar y eliminar tópicos.
- Crear y ver respuestas asociadas a los tópicos.
- Registro y login de usuarios con autenticación JWT.
- Validación básica de datos.
- Uso de DTOs y controladores RESTful.
- Paginación de resultados.

---

## 🧰 Tecnologías utilizadas

- Java 17  
- Spring Boot  
- Spring Security  
- Spring Data JPA  
- MySQL  
- JWT  
- Maven  
- Lombok  

---

## 📌 Endpoints

### 🔸 Tópicos
- `GET /topicos` → Listar todos los tópicos  
- `POST /topicos` → Crear nuevo tópico  
- `PUT /topicos/{id}` → Actualizar tópico  
- `DELETE /topicos/{id}` → Eliminar tópico  

### 🔸 Respuestas
- `POST /respuestas` → Crear respuesta a un tópico  
- `GET /respuestas` → Ver respuestas disponibles  
- `DELETE /respuestas/{id}` → Eliminar respuesta  

### 🔸 Usuarios
- `POST /register` → Registrar nuevo usuario  
- `POST /login` → Iniciar sesión  

---

## 📦 Ejemplos de uso

### 🔹 Registro de usuario

```json
POST /register
{
  "nombre": "Ana",
  "apellido": "López",
  "email": "ana@example.com",
  "contrasenia": "clave123"
}

```Crear un tópico
POST /topicos
{
  "titulo": "¿Recomiendan algún curso de Java?",
  "mensaje": "Estoy comenzando y busco recursos.",
  "curso": "Java",
  "autorId": 2
}

```Responder a un tópico
POST /respuestas
{
  "mensaje": "Puedes empezar con la documentación oficial de Spring.",
  "topicoId": 1,
  "usuarioId": 3
}


🛠️ Este proyecto sigue en desarrollo. Hay errores pendientes por corregir, pero estoy usando esta experiencia como parte de mi crecimiento como desarrolladora backend. 😄
