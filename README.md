# 📦 API para Gestión de Inventarios

## 📝 Descripción del Dominio del Negocio

Esta **API RESTful** está diseñada para la **Gestión Integral de Inventarios**. Su arquitectura modular contempla los siguientes módulos operativos:

- **Módulo de Catálogo:** Administración CRUD (GET, POST, PUT, DELETE) de productos, categorías y proveedores.
- **Módulo de Inventario y Sucursales:** Control dinámico de existencias físicas por sucursal mediante operaciones de incremento y decremento de stock.
- **Módulo de Transacciones:** Registro inmutable de movimientos (entradas y salidas) y trazabilidad de transferencias entre sucursales.
- **Módulo de Seguridad:** Autenticación de usuarios, gestión de roles y asignación de privilegios sobre el sistema.

## 👥 Integrantes del Equipo de Desarrollo

| Nombre Completo                  | Carnet  |
| -------------------------------- | ------- |
| Daniel Eduardo Yanes Hércules    | YH25001 |
| Jesús Francisco Recinos Pinzón   | RP09016 |
| William Eduardo Torres Serrano   | TS25003 |
| Emerson Daniel Sagastume Munguia | SM06032 |

## 🛠️ Stack Tecnológico y Requisitos Previos

Para compilar, probar y ejecutar este proyecto en un entorno local necesitas:

* **Java Development Kit (JDK):** Versión 21.

* **Gestor de Construcción:** Gradle con Kotlin DSL (incluido mediante Gradle Wrapper `./gradlew`).

* **Base de Datos:** PostgreSQL 14 o superior.

* **Documentación OpenAPI:** `springdoc-openapi-starter-webmvc-ui` (v3.1.1).

* **Pruebas Unitarias:** JUnit 5 y Mockito.

* **Contenedores / Orquestación (Opcional):** Docker y Kubernetes (`kubectl` / Kustomize).

* **Cliente HTTP (Opcional):** Apidog para importar y ejecutar las colecciones de prueba.

## 🏗️ Arquitectura y Estructura del Proyecto

La aplicación implementa una **arquitectura en capas** bajo el patrón REST, separando la exposición de endpoints, la lógica de negocio, el mapeo de DTOs y el acceso a datos:

```
gestion-inventarios-api/
├── docs/
│   ├── api/
│   │   ├── openapi/
│   │   │   └── openapi.json                         # Contrato OpenAPI 3 exportado
│   │   └── colecciones/
│   │       └── apidog/                              # Colecciones de prueba por módulo
│   │           ├── categorias.json
│   │           ├── proveedores.json
│   │           ├── productos.json
│   │           ├── sucursales.json
│   │           ├── stock_sucursales.json
│   │           └── movimientos_inventario.json
│   ├── db/
│   │   ├── script_ddl_postgresql.sql                # Creación de tablas y restricciones
│   │   └── script_insercion_registros_postgresql.sql # Datos iniciales de prueba
│   └── diagramas/                                   # Diagramas ER, de clases y casos de uso
├── k8s/
│   └── base/
│       ├── deployment.yaml                          # Manifiesto de despliegue en Kubernetes
│       └── kustomization.yaml                       # Configuración de Kustomize
├── src/
│   ├── main/
│   │   ├── java/ues/ids/poo/api/
│   │   │   ├── config/                              # Configuración de OpenAPI (OpenApiConfig)
│   │   │   ├── controller/                          # Controladores REST y HealthController
│   │   │   ├── dto/                                 # Request y Response DTOs
│   │   │   ├── enums/                               # Enumeraciones (TipoMovimiento, UsuarioRol)
│   │   │   ├── exception/                           # Manejo global de errores (GlobalExceptionHandler)
│   │   │   ├── mapper/                              # Conversión entre entidades y DTOs
│   │   │   ├── model/                               # Entidades JPA (@Entity)
│   │   │   ├── repository/                          # Repositorios Spring Data JPA
│   │   │   └── service/                             # Lógica de negocio (@Service)
│   │   └── resources/
│   │       └── application.properties               # Configuración de Spring Boot
│   └── test/
│       └── java/ues/ids/poo/api/
│           ├── controller/                          # Pruebas unitarias de controladores
│           └── service/                             # Pruebas unitarias de servicios
├── Dockerfile                                       # Imagen Docker de la aplicación
├── build.gradle.kts                                 # Dependencias y configuración de Gradle
└── README.md
```

## 📊 Diagramas del Sistema

Los diagramas de diseño y modelado del proyecto se encuentran disponibles en la carpeta `docs/diagramas/`:

* **Diagrama Entidad-Relación:** `docs/diagramas/diagrama_er.png` (fuente en `.drawio`)

* **Diagrama de Clases:** `docs/diagramas/diagrama_clases.png` (fuente en `.puml`)

* **Diagrama de Casos de Uso:** `docs/diagramas/diagrama_casos_uso.png` (fuente en `.puml`)

## ⚙️ Configuración de Base de Datos y Variables de Entorno

### 1. Inicializar la base de datos en PostgreSQL

Crea una base de datos en tu instancia local de PostgreSQL (por ejemplo, `gestion_inventarios_db`) y ejecuta los scripts ubicados en `docs/db/` en el siguiente orden:

1. `docs/db/script_ddl_postgresql.sql` (estructura de tablas y llaves foráneas).

2. `docs/db/script_insercion_registros_postgresql.sql` (registros iniciales para pruebas).

### 2. Configurar variables de entorno

La conexión definida en `src/main/resources/application.properties` lee las credenciales desde variables de entorno. Configúralas en tu sistema operativo o en la configuración de ejecución (*Run Configuration*) de tu IDE:

| Variable | Descripción | Ejemplo Local |
| ----- | ----- | ----- |
| `DB_URL` | Cadena de conexión JDBC a PostgreSQL | `jdbc:postgresql://localhost:5432/gestion_inventarios_api` |
| `DB_USERNAME` | Usuario de la base de datos | `postgres` |
| `DB_PASSWORD` | Contraseña del usuario de PostgreSQL | `tu_contraseña_local` |

## 🚀 Despliegue y Ejecución

### Ejecución Local con Gradle

1. **Clonar el repositorio:**

   ```
   git clone https://github.com/DanielYanes-YH25001/gestion-inventarios-api.git
   cd gestion-inventarios-api
   ```

2. **Compilar el proyecto y ejecutar pruebas:**

   * **Linux / macOS:**

     ```
     ./gradlew clean build
     ```

   * **Windows (CMD / PowerShell):**

     ```
     gradlew.bat clean build
     ```

3. **Levantar la aplicación:**

   * **Linux / macOS:**

     ```
     ./gradlew bootRun
     ```

   * **Windows (CMD / PowerShell):**

     ```
     gradlew.bat bootRun
     ```

La API quedará disponible en `http://localhost:8080`.

### Despliegue con Docker y Kubernetes

* **Construir y ejecutar con Docker:**

  ```
  docker build -t gestion-inventarios-api:latest .
  docker run -p 8080:8080 \
    -e DB_URL="jdbc:postgresql://host.docker.internal:5432/gestion_inventarios_db" \
    -e DB_USERNAME="postgres" \
    -e DB_PASSWORD="tu_contraseña_local" \
    gestion-inventarios-api:latest
  ```

* **Despliegue en Kubernetes:**
  Los manifiestos base se encuentran en `k8s/base/` (`deployment.yaml` y `kustomization.yaml`) y pueden aplicarse mediante:

  ```
  kubectl apply -k k8s/base
  ```

### CI/CD local con Act

La automatización local está definida en `.github/workflows/ci-cd-local.yml`.
El pipeline ejecuta las pruebas JUnit, empaqueta la aplicación, construye la imagen
Docker y despliega los manifiestos de Kubernetes.

El workflow está pensado para ejecutarse localmente con Act y usa
`.kube-local/config`, que no debe publicarse. Los datos de PostgreSQL se guardan
en `.kind-data/postgres`. La guía de reinicio o limpieza de esos datos y del
clúster local se mantiene en el script `scripts/local-ci.sh`.

Los manifiestos siguen la convención de Kustomize: `k8s/base` contiene los
recursos comunes de la API; `k8s/overlays/local` añade kind y PostgreSQL local;
`k8s/overlays/production` configura la API para conectarse a un PostgreSQL
administrado aparte y no crea una base de datos. El overlay local usa
`imagePullPolicy: Never` porque la imagen se carga directamente en kind; el
base usa `IfNotPresent` para permitir imágenes ya disponibles en otros entornos.

## 📖 Documentación Interactiva (Swagger / OpenAPI)

La configuración personalizada de OpenAPI se encuentra en `OpenApiConfig.java`. Con el servidor en ejecución puedes acceder a:

* **Interfaz Swagger UI:** <http://localhost:8080/swagger-ui/index.html>

* **Contrato OpenAPI (JSON en vivo):** <http://localhost:8080/v3/api-docs>

* **Contrato OpenAPI estático:** `docs/api/openapi/openapi.json`

## 🌐 Endpoints Principales

Todas las rutas responden en formato `application/json` y siguen las convenciones REST (sin barra diagonal `/` al final de la URL):

| Módulo | Controlador | Ruta Base | Operaciones |
| ----- | ----- | ----- | ----- |
| **Estado del Servicio** | `HealthController` | `/api/ping` | `GET` |
| **Categorías** | `CategoriaController` | `/api/categorias` | `GET`, `POST`, `PUT`, `DELETE` |
| **Proveedores** | `ProveedorController` | `/api/proveedores` | `GET`, `POST`, `PUT`, `DELETE` |
| **Productos** | `ProductoController` | `/api/productos` | `GET`, `POST`, `PUT`, `DELETE` |
| **Sucursales** | `SucursalController` | `/api/sucursales` | `GET`, `POST`, `PUT`, `DELETE` |
| **Stock por Sucursal** | `StockSucursalController` | `/api/stock-sucursales` | `GET`, `POST`, `PUT`, `DELETE` |
| **Movimientos de Inventario** | `MovimientoInventarioController` | `/api/movimientos-inventario` | `GET`, `POST` |

### Ejemplo de Petición (`POST /api/proveedores`)

**Request Body:**

```
{
  "nombre": "Confecciones del Pacífico S.A. de C.V.",
  "contacto": "José Martínez",
  "telefono": "+50375556789",
  "direccion": "Boulevard Los Próceres 25, La Libertad"
}
```

**Response (`201 Created`):**

```
{
  "idProveedor": 8,
  "nombre": "Confecciones del Pacífico S.A. de C.V.",
  "contacto": "José Martínez",
  "telefono": "+50375556789",
  "direccion": "Boulevard Los Próceres 25, La Libertad"
}
```

## 🧪 Comandos de Prueba y Colecciones de Apidog

### 1. Pruebas Unitarias (JUnit y Mockito)

Para ejecutar la suite completa de pruebas de controladores y servicios ubicada en `src/test/`:

* **Linux / macOS:**

  ```
  ./gradlew test
  ```

* **Windows:**

  ```
  gradlew.bat test
  ```

### 2. Colecciones de Apidog

Las colecciones exportadas con casos de prueba exitosos (`200`, `201`, `204`) y de error (`400`, `404`) se encuentran en `docs/api/colecciones/apidog/`.

**Pasos para importarlas en Apidog:**

1. Abre tu proyecto en **Apidog** y ve a **Configuración (Settings) > Importar Datos (Import Data)**.

2. Selecciona el formato **Apidog** y carga los archivos `.json` desde `docs/api/colecciones/apidog/`.

3. Verifica que la URL base apunte a `http://localhost:8080` y ejecuta los casos de prueba.

### 3. Verificación rápida desde terminal

```
curl -I http://localhost:8080/swagger-ui/index.html
```
