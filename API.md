
# Documentación de servicios web - Ecoesfera CRM

## 1. Información general

**Proyecto:** Ecoesfera CRM  
**Programa:** Análisis y Desarrollo de Software (ADSO) - SENA  
**Tecnología backend:** Java y Spring Boot  
**Tipo de servicio:** API REST  
**Formato de intercambio:** JSON  
**Base de datos:** MySQL  

## 2. Descripción

La API REST de Ecoesfera CRM permite consultar, registrar, actualizar y eliminar productos naturales. Los servicios reciben solicitudes HTTP y se comunican con la capa de servicio de la aplicación para gestionar la información de los productos.

## 3. URL base

Durante el desarrollo local, la dirección base es:

`http://localhost:8080/api/productos`

Para utilizarla, el servidor Spring Boot debe estar ejecutándose.

## 4. Servicios disponibles

### 4.1. Listar productos

- **Método:** GET
- **Ruta:** `/api/productos`
- **Descripción:** Consulta la lista de productos registrados.
- **Respuesta esperada:** `200 OK`
- **Formato:** Arreglo JSON de productos.

Ejemplo de solicitud:

`GET http://localhost:8080/api/productos`

### 4.2. Consultar producto por ID

- **Método:** GET
- **Ruta:** `/api/productos/{id}`
- **Descripción:** Busca un producto por su identificador.
- **Respuesta esperada:** `200 OK` si existe.
- **Respuesta alternativa:** `404 Not Found` si no existe.

Ejemplo:

`GET http://localhost:8080/api/productos/1`

El número 1 es un ejemplo; debe reemplazarse por el ID de un producto existente.

### 4.3. Registrar producto

- **Método:** POST
- **Ruta:** `/api/productos`
- **Descripción:** Registra un producto nuevo.
- **Encabezado:** `Content-Type: application/json`
- **Respuesta esperada:** `201 Created`

Ejemplo de cuerpo JSON:

```json
{
  "nombre": "Omega 3",
  "imagen": "omega3.jpg",
  "descripcion": "Suplemento de omega 3",
  "precioVenta": 45000,
  "stock": 20
}
```

Los campos del ejemplo deben coincidir con los atributos definidos en la entidad Producto. Si la entidad exige otros campos, deben incluirse según corresponda.

### 4.4. Actualizar producto

- **Método:** PUT
- **Ruta:** `/api/productos/{id}`
- **Descripción:** Actualiza la información de un producto existente.
- **Encabezado:** `Content-Type: application/json`
- **Respuesta esperada:** `200 OK` si se actualiza.
- **Respuesta alternativa:** `404 Not Found` si el ID no existe.

Ejemplo:

`PUT http://localhost:8080/api/productos/1`

El cuerpo JSON debe contener los datos que espera la entidad Producto.

### 4.5. Eliminar producto

- **Método:** DELETE
- **Ruta:** `/api/productos/{id}`
- **Descripción:** Elimina el producto identificado por su ID.
- **Respuesta esperada:** `204 No Content` si se elimina.
- **Respuesta alternativa:** `404 Not Found` si el ID no existe.

Ejemplo:

`DELETE http://localhost:8080/api/productos/1`

## 5. Códigos de respuesta HTTP

| Código | Significado |
|---|---|
| 200 OK | Solicitud procesada correctamente. |
| 201 Created | Recurso creado correctamente. |
| 204 No Content | Recurso eliminado correctamente. |
| 404 Not Found | El producto solicitado no existe. |

## 6. Pruebas

Los servicios pueden probarse mediante Postman enviando solicitudes HTTP al servidor local.

Se recomienda verificar:
- La consulta de todos los productos.
- La búsqueda de un producto existente y uno inexistente.
- El registro de un producto.
- La actualización de un producto existente.
- La eliminación de un producto existente y la consulta posterior.

Los resultados de las pruebas deben registrarse mediante capturas de pantalla para la evidencia del proyecto.

## 7. Repositorio

https://github.com/PipeAl/ecoesfera-crm

## 8. Consideraciones

La API está configurada para aceptar solicitudes de cualquier origen mediante CORS. Esta configuración es útil durante el desarrollo, pero debe restringirse antes de un despliegue productivo.

La disponibilidad de los servicios depende de que Spring Boot, MySQL y la configuración de la base de datos estén funcionando correctamente.
