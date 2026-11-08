# 🌿 Ecoesfera CRM

### Sistema de gestión de productos naturales

## 📌 Descripción del proyecto

Ecoesfera CRM es un sistema de información desarrollado como proyecto de formación del programa **Análisis y Desarrollo de Software (ADSO) del SENA**.

Su objetivo es facilitar la gestión y administración de un negocio dedicado a la comercialización de productos naturales, permitiendo organizar la información de productos, categorías, clientes, proveedores y ventas.

El sistema busca mejorar el control de la información, optimizar los procesos administrativos y facilitar la consulta de los datos del negocio.

## 🎯 Objetivo general

Desarrollar una aplicación web que permita gestionar de manera organizada la información de un negocio de productos naturales mediante una interfaz intuitiva y una API REST conectada a una base de datos.

## 🛠️ Tecnologías utilizadas

- **Java 21:** lenguaje de programación.
- **Spring Boot:** desarrollo del backend y servicios web.
- **Spring Data JPA:** persistencia y gestión de datos.
- **MySQL:** sistema de gestión de bases de datos.
- **HTML5:** estructura de las interfaces.
- **CSS3:** diseño y estilos visuales.
- **JavaScript:** funcionalidades e interacción del frontend.
- **Postman:** pruebas de los servicios web.
- **Git y GitHub:** control de versiones.

## 📂 Estructura del proyecto

```text
crm/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/sena/adso/crm/
│       │       ├── controller/
│       │       ├── entidades/
│       │       ├── repository/
│       │       ├── servicio/
│       │       └── CrmApplication.java
│       └── resources/
│           ├── static/
│           │   ├── css/
│           │   ├── img/
│           │   ├── index.html
│           │   ├── productos.html
│           │   ├── categorias.html
│           │   └── usuarios.html
│           └── application.properties
├── docs/
│   └── API.md
├── pom.xml
└── README.md
```

## 🗄️ Base de datos

El proyecto utiliza MySQL con una base de datos denominada:

`ecoesfera`

La conexión se configura mediante el archivo `application.properties`.

## 🚀 Ejecución del proyecto

1. Clonar el repositorio:

```bash
git clone https://github.com/PipeAl/ecoesfera-crm.git
```

2. Ingresar a la carpeta:

```bash
cd ecoesfera-crm
```

3. Verificar la configuración de conexión a MySQL en `application.properties`.

4. Ejecutar la aplicación:

```bash
mvn spring-boot:run
```

5. Abrir el navegador:

```text
http://localhost:8080/index.html
```

## 🔗 API REST

La API permite gestionar la información de los productos.

**URL base:**

```text
http://localhost:8080/api/productos
```

Las operaciones disponibles se documentan en el archivo `docs/API.md`.

## 👨‍💻 Autor

**Andrés Felipe Alvarez Martínez**

Tecnólogo en formación – Análisis y Desarrollo de Software (ADSO).

**SENA – Servicio Nacional de Aprendizaje**

## 📌 Estado del proyecto

Proyecto en desarrollo.
