
# Ecoesfera CRM

## 1. Descripción del proyecto

Ecoesfera CRM es un sistema de gestión para una tienda de productos naturales, desarrollado como parte del proyecto formativo del programa Análisis y Desarrollo de Software (ADSO) del SENA.

El proyecto busca facilitar la gestión de productos y servir como base para incorporar nuevas funcionalidades relacionadas con clientes, proveedores, inventario y ventas.

## 2. Objetivo general

Desarrollar una aplicación web que permita gestionar información de productos naturales y establecer una base para la administración de las relaciones con los clientes.

## 3. Tecnologías utilizadas

- Java 21.
- Spring Boot.
- Maven.
- MySQL.
- HTML5.
- CSS3.
- JavaScript.
- Git y GitHub.
- Visual Studio Code.

## 4. Funcionalidades implementadas

- Panel principal de la aplicación.
- Consulta de productos mediante un servicio web.
- Registro de productos.
- Almacenamiento de productos en MySQL.
- Visualización de productos con imágenes.
- Control de versiones mediante Git.

## 5. Servicio web de productos

La aplicación dispone de un servicio REST para consultar y registrar productos.

- Método GET: `/api/productos`
- Método POST: `/api/productos`

El registro de productos utiliza información enviada desde el formulario de la interfaz web.

## 6. Ejecución del proyecto

### Requisitos previos

- Java 21 instalado.
- Maven o el Maven Wrapper del proyecto.
- Servidor MySQL disponible.
- Base de datos `ecoesfera` configurada.

### Pasos

1. Clonar el repositorio de GitHub.
2. Configurar la conexión a MySQL en los archivos de configuración de la aplicación.
3. Iniciar el servidor MySQL.
4. Abrir una terminal en la carpeta del proyecto.
5. Ejecutar:

```bash
mvn spring-boot:run
```

6. Abrir en el navegador:

- Panel principal: `http://localhost:8080/index.html`
- Catálogo de productos: `http://localhost:8080/productos.html`

## 7. Repositorio

https://github.com/PipeAl/ecoesfera-crm

## 8. Control de versiones

El proyecto utiliza Git para registrar los cambios realizados durante el desarrollo y GitHub para alojar el repositorio remoto.

## 9. Estado del proyecto

El módulo de productos cuenta con funcionalidades de consulta, registro y visualización. Las demás funcionalidades se desarrollarán progresivamente según los requerimientos del proyecto formativo.

## 10. Autoría

Andrés Felipe Alvarez Martinez, aprendiz de ADSO cuyo proposito es la realización del Proyecto formativo del programa Análisis y Desarrollo de Software (ADSO) del SENA.
