# 📚 Proyecto Literalura

Bienvenido al proyecto **Literalura**, una aplicación diseñada para gestionar libros y autores de manera eficiente. Este proyecto forma parte del desafío de **Alura Challenge** y está construido con Java, Spring Boot y Maven. 🚀

## 🌟 Características principales

- 📝 Gestión de autores y libros.
- 🔍 Búsqueda optimizada de libros.
- 📊 Organización de datos con patrones de diseño y arquitectura clara.

## 📂 Estructura del proyecto

El proyecto sigue una arquitectura bien definida:

- **Models**: 📦 Contiene las entidades principales del proyecto, como `Autor` y `Libro`.
- **Repository**: 🗄️ Interfaces para la interacción con la base de datos.
- **Service**: 🔧 Lógica de negocio y servicios auxiliares.
- **Principal**: 🚦 Punto de entrada de la aplicación.

## 🛠️ Tecnologías utilizadas

- **Java 17** ☕
- **Spring Boot** 🌱
- **Maven** 📦
- **PostgreSQL** 🐘
- **Lombok** 🍃

## ⚙️ Requisitos previos

Asegúrate de tener instalado lo siguiente:

- Java 17 o superior ☕
- Maven 📦
- PostgreSQL 🐘

## 🚀 Cómo ejecutar el proyecto

1. Clona este repositorio:
   ```bash
   git clone https://github.com/tu-usuario/literalura.git
   ```

2. Accede al directorio del proyecto:
   ```bash
   cd literalura
   ```

3. Configura la base de datos en el archivo `application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/literalura
   spring.datasource.username=tu-usuario
   spring.datasource.password=tu-contraseña
   ```

4. Compila y ejecuta el proyecto:
   ```bash
   mvn spring-boot:run
   ```

## 🤝 Contribuciones

¡Las contribuciones son bienvenidas! 🙌 Por favor, sigue estos pasos:

1. Haz un fork del proyecto 🍴.
2. Crea una rama para tu funcionalidad (`git checkout -b nueva-funcionalidad`).
3. Realiza los cambios y haz commit (`git commit -m 'Añadir nueva funcionalidad'`).
4. Envía un pull request 📬.

## 📝 Licencia

Este proyecto está bajo la Licencia MIT. 📄 Puedes consultar el archivo `LICENSE` para más información.

---

¡Gracias por tu interés en el proyecto Literalura! 💡
