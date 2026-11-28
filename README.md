# 📦 API para Gestión de Inventarios

## 📝 Descripción del Dominio del Negocio

Esta **API RESTful** está diseñada para la **Gestión Integral de Inventarios**. Su arquitectura modular contempla los siguientes módulos operativos:

- **Módulo de Catálogo:** Administración CRUD (GET, POST, PUT, DELETE) de productos, categorías y proveedores.
- **Módulo de Inventario y Sucursales:** Consulta de existencias por sucursal.
- **Módulo de Movimientos:** Registro e historial de movimientos de inventario asociados a productos, sucursales y usuarios.
- **Usuarios y roles:** El modelo contempla usuarios y roles para asociarlos a los movimientos; la API no implementa autenticación ni endpoints de administración de usuarios por el momento.

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

* **Base de Datos:** PostgreSQL 18 o superior.

* **Documentación OpenAPI:** `springdoc-openapi-starter-webmvc-ui` (v3.2.0).

* **Pruebas Unitarias:** JUnit 5 y Mockito.

* **Contenedores / Orquestación (Opcional):** Docker y Kubernetes (`kubectl` / Kustomize).

* **Cliente HTTP (Opcional):** Apidog para importar y ejecutar las colecciones de prueba.

## 🏗️ Arquitectura y Estructura del Proyecto

La aplicación implementa una **arquitectura en capas** bajo el patrón REST: los controladores exponen los endpoints, los servicios contienen la lógica de negocio, los mappers convierten entidades y DTOs, y los repositorios acceden a los datos.

```
gestion-inventarios-api/
├── .github/
│   └── workflows/
│       └── ci-cd-local.yml                         # Pruebas, construcción de imagen y despliegue local
├── docs/
│   ├── api/
│   │   ├── openapi/
│   │   │   └── openapi.json                        # Contrato OpenAPI exportado
│   │   └── colecciones/
│   │       └── apidog/                             # Colecciones de prueba por módulo
│   ├── db/
│   │   ├── script_ddl_postgresql.sql               # Estructura de tablas
│   │   └── script_insercion_registros_postgresql.sql # Datos iniciales
│   └── diagramas/                                  # Diagramas ER, de clases y casos de uso
├── gradle/
│   └── wrapper/                                    # Archivos del Gradle Wrapper
├── k8s/
│   ├── base/                                       # Deployment y Service comunes
│   └── overlays/
│       ├── local/                                  # API y PostgreSQL para entorno local
│       └── production/                             # Configuración base para producción
├── scripts/
│   └── local-cd.sh                                 # Carga de imagen y despliegue local
├── src/
│   ├── main/
│   │   ├── java/ues/ids/poo/api/
│   │   │   ├── config/                             # Configuración OpenAPI
│   │   │   ├── controller/                         # Controladores REST
│   │   │   ├── dto/
│   │   │   │   ├── request/                        # DTOs de entrada
│   │   │   │   └── response/                       # DTOs de salida
│   │   │   ├── enums/                              # TipoMovimiento y UsuarioRol
│   │   │   ├── exception/                          # Manejo global de errores
│   │   │   ├── mapper/                             # Conversión entre entidades y DTOs
│   │   │   ├── model/                              # Entidades JPA
│   │   │   ├── repository/                         # Repositorios Spring Data JPA
│   │   │   └── service/                            # Lógica de negocio
│   │   └── resources/
│   │       └── application.properties              # Configuración de Spring Boot
│   └── test/
│       └── java/ues/ids/poo/api/                   # Pruebas de controladores y servicios
├── .actrc                                           # Configuración local de Act
├── Dockerfile                                      # Imagen Docker de la aplicación
├── build.gradle.kts                                # Dependencias y configuración de Gradle
├── gradlew / gradlew.bat                           # Gradle Wrapper para Linux/macOS y Windows
├── settings.gradle.kts
└── README.md
```

## 📊 Diagramas del Sistema

Los diagramas de diseño y modelado del proyecto se encuentran disponibles en la carpeta `docs/diagramas/`:

* **Diagrama Entidad-Relación:** `docs/diagramas/diagrama_er.png` (fuente en `.drawio`)

* **Diagrama de Clases:** `docs/diagramas/diagrama_clases.png` (fuente en `.puml`)

* **Diagrama de Casos de Uso:** `docs/diagramas/diagrama_casos_uso.png` (fuente en `.puml`)

## ⚙️ Configuración de Base de Datos y Variables de Entorno

### 1. Inicializar la base de datos en PostgreSQL

Crea una base de datos en tu instancia local de PostgreSQL llamada `gestion_inventarios_db`. Para cargar el esquema y los datos de prueba, ejecuta los scripts de `docs/db/` en este orden:

1. `docs/db/script_ddl_postgresql.sql` (estructura de tablas y llaves foráneas).

2. `docs/db/script_insercion_registros_postgresql.sql` (registros iniciales para pruebas).

### 2. Configurar variables de entorno

La conexión definida en `src/main/resources/application.properties` lee las credenciales desde variables de entorno. Configúralas en tu sistema operativo o en la configuración de ejecución (*Run Configuration*) de tu IDE:

| Variable | Descripción | Ejemplo Local |
| ----- | ----- | ----- |
| `DB_URL` | Cadena de conexión JDBC a PostgreSQL | `jdbc:postgresql://localhost:5432/gestion_inventarios_db` |
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
  Los manifiestos usan Kustomize: `k8s/base/` contiene los recursos comunes y
  `k8s/overlays/` define las configuraciones local y de producción. Para aplicar
  el entorno local, primero prepara el Secret `app-secret` con la clave
  `DB_PASSWORD` y el ConfigMap `postgres-initdb` con los scripts SQL de
  `docs/db/`; el manifiesto local los referencia. El volumen persistente local
  está asociado al nodo `gestion-inventarios-control-plane`. Luego aplica:

  ```
  kubectl apply -k k8s/overlays/local
  ```

#### Automatizar el despliegue local con Act

También puedes ejecutar el flujo local de CI/CD con [Act](https://nektosact.com/).
Necesitas Docker y Act, un clúster Kubernetes local accesible, y `kubectl`
disponible dentro del runner de Act. Prepara los recursos `app-secret` y
`postgres-initdb` descritos arriba. Guarda el kubeconfig en
`.kube-local/config`; este archivo está excluido de Git y el workflow lo utiliza
para conectarse al clúster.

Desde la raíz del proyecto, inicia el workflow con:

```
act workflow_dispatch -W .github/workflows/ci-cd-local.yml
```

El workflow ejecuta las pruebas, construye la imagen Docker, verifica el acceso
al clúster y delega el despliegue en `scripts/local-cd.sh`. Este script aplica
`k8s/overlays/local` y espera a que se completen los despliegues de PostgreSQL y
de la API.

El overlay `production` es un punto de partida: antes de usarlo, configura
`DB_URL` para apuntar a la instancia de PostgreSQL administrada y proporciona el
Secret `app-secret` en el clúster. No incluye una base de datos PostgreSQL.

## 📖 Documentación Interactiva (Swagger / OpenAPI)

La configuración personalizada de OpenAPI se encuentra en `OpenApiConfig.java`. Con el servidor en ejecución puedes acceder a:

* **Interfaz Swagger UI:** <http://localhost:8080/swagger-ui/index.html>

* **Contrato OpenAPI (JSON en vivo):** <http://localhost:8080/v3/api-docs>

* **Contrato OpenAPI estático:** `docs/api/openapi/openapi.json`

## 🌐 Endpoints Principales

Los endpoints usan JSON para las solicitudes y respuestas que incluyen contenido; las operaciones `DELETE` responden `204 No Content`.

| Módulo | Controlador | Ruta Base | Operaciones |
| ----- | ----- | ----- | ----- |
| **Estado del Servicio** | `HealthController` | `/api/ping` | `GET` |
| **Categorías** | `CategoriaController` | `/api/categorias` | `GET`, `POST`, `PUT`, `DELETE` |
| **Proveedores** | `ProveedorController` | `/api/proveedores` | `GET`, `POST`, `PUT`, `DELETE` |
| **Productos** | `ProductoController` | `/api/productos` | `GET`, `POST`, `PUT`, `DELETE` |
| **Sucursales** | `SucursalController` | `/api/sucursales` | `GET`, `POST`, `PUT`, `DELETE` |
| **Stock por Sucursal** | `StockSucursalController` | `/api/stocksucursales/{idSucursal}` | `GET` |
| **Movimientos de Inventario** | `MovimientoInventarioController` | `/api/movimientos` | `GET`, `POST` |

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
