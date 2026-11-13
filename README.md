# API REST de Pacientes — Spring Boot + MySQL

API REST para el **registro de pacientes ficticios**, desarrollada como laboratorio individual del curso *Desarrollo de Servicios Web I* (IDAT). Permite crear, listar, consultar, actualizar y eliminar pacientes almacenados en una única tabla MySQL.

> Todos los datos de ejemplo son ficticios.

## Tecnologías

| Componente | Versión / detalle |
|---|---|
| Java | 21 |
| Spring Boot | 4.1.2 |
| Build | Maven (wrapper `mvnw` incluido) |
| Dependencias | Spring Web MVC, Spring Data JPA, Validation, MySQL Connector/J, Lombok |
| Base de datos | MySQL 8 (InnoDB, utf8mb4) |
| Pruebas HTTP | Postman |

## Estructura del proyecto

```
src/main/java/com/idat/evcontinua03
├── controller/   PacienteController        → rutas HTTP /api/pacientes
├── service/      IPacienteService, PacienteService → reglas de negocio
├── repository/   IPacienteRepository       → JpaRepository<Paciente, Long>
├── model/        Paciente                  → entidad JPA + validaciones
└── exception/    GlobalExceptionHandler y excepciones → respuestas 400/404/409
src/main/resources
├── application.properties                  → configuración por variables de entorno
└── db/tables.sql                           → script de la tabla
```

Flujo: `Cliente HTTP → Controller → Service → Repository → MySQL → respuesta JSON`

## Modelo de datos

Una sola tabla, `paciente`, con seis campos:

| Campo | Tipo SQL | Tipo Java | Restricciones |
|---|---|---|---|
| id | BIGINT AUTO_INCREMENT | Long | PK, generado por MySQL |
| nombres | VARCHAR(100) | String | NOT NULL |
| apellidos | VARCHAR(100) | String | NOT NULL |
| documento | VARCHAR(20) | String | NOT NULL, UNIQUE |
| fecha_nacimiento | DATE | LocalDate | NOT NULL, debe ser pasada |
| activo | BOOLEAN | Boolean | NOT NULL |

## Instalación y ejecución

### 1. Requisitos previos
- JDK 21
- MySQL 8 en ejecución (puerto 3306 por defecto)

### 2. Crear la base de datos
Ejecuta el script en tu cliente MySQL:

```bash
mysql -u root -p < src/main/resources/db/tables.sql
```

Datos de prueba opcionales:

```sql
INSERT INTO paciente (nombres, apellidos, documento, fecha_nacimiento, activo) VALUES
 ('Jorge Luis',   'Mendoza Salas', '70000001', '1985-03-14', TRUE),
 ('María Elena',  'Paredes Rojas', '70000002', '1990-11-02', TRUE),
 ('Lucía Andrea', 'Ramos Quispe',  '70000003', '1992-08-21', TRUE),
 ('Carlos Iván',  'Huamán Cruz',   '70000004', '1978-06-30', FALSE),
 ('Rosa Isabel',  'Vargas Ticona', '70000005', '2001-01-09', TRUE);
```

### 3. Configurar variables de entorno
Las credenciales **no** están en el código. Copia `.env.example` a `.env` (ya está en `.gitignore`) y completa los valores:

```properties
DB_URL=jdbc:mysql://localhost:3306/db_pacientes
DB_USER=tu_usuario
DB_PASSWORD=tu_contraseña
```

También puedes definirlas como variables de entorno del sistema o en la configuración de ejecución de IntelliJ.

### 4. Ejecutar

```bash
# Linux / macOS
./mvnw spring-boot:run

# Windows
mvnw.cmd spring-boot:run
```

La API queda disponible en `http://localhost:8081`.

> Se usa `spring.jpa.hibernate.ddl-auto=validate`: Hibernate no crea ni modifica la tabla, solo verifica que coincida con la entidad. Si falla al iniciar, revisa que la tabla se haya creado con el script.

## Endpoints

Base: `http://localhost:8081/api/pacientes`

| Método | Ruta | Descripción | Respuestas |
|---|---|---|---|
| POST | `/api/pacientes` | Registra un paciente (sin `id`) | 201, 400, 409 |
| GET | `/api/pacientes` | Lista todos los pacientes | 200 |
| GET | `/api/pacientes/{id}` | Obtiene un paciente | 200, 404 |
| PUT | `/api/pacientes/{id}` | Actualiza los cinco campos | 200, 400, 404, 409 |
| DELETE | `/api/pacientes/{id}` | Elimina un paciente | 204, 404 |

### Ejemplos

**Registrar**

```http
POST /api/pacientes
Content-Type: application/json

{
  "nombres": "Carmen Rosa",
  "apellidos": "Flores Huamán",
  "documento": "70000006",
  "fecha_nacimiento": "1995-04-17",
  "activo": true
}
```

Respuesta `201 Created`:

```json
{
  "id": 6,
  "nombres": "Carmen Rosa",
  "apellidos": "Flores Huamán",
  "documento": "70000006",
  "fecha_nacimiento": "1995-04-17",
  "activo": true
}
```

**Actualizar**

```http
PUT /api/pacientes/6
Content-Type: application/json

{
  "nombres": "Carmen Rosa",
  "apellidos": "Flores Huamán de Ríos",
  "documento": "70000006",
  "fecha_nacimiento": "1995-04-17",
  "activo": false
}
```

**Eliminar**

```http
DELETE /api/pacientes/6
```

Respuesta `204 No Content`.

**Con curl**

```bash
curl http://localhost:8081/api/pacientes
curl http://localhost:8081/api/pacientes/1
curl -X POST http://localhost:8081/api/pacientes -H "Content-Type: application/json" \
  -d '{"nombres":"Ana","apellidos":"Ruiz Soto","documento":"70000010","fecha_nacimiento":"1998-05-20","activo":true}'
curl -X DELETE http://localhost:8081/api/pacientes/1
```

## Validaciones y errores

| Caso | Código | Ejemplo de respuesta |
|---|---|---|
| Campo obligatorio vacío o fecha futura | 400 | `{"mensaje": "Los datos enviados no son validos.", "errores": {"nombres": "Los nombres son obligatorios"}}` |
| JSON mal formado o tipo inválido | 400 | `{"mensaje": "El cuerpo JSON es invalido o contiene un tipo de dato incorrecto."}` |
| ID inexistente | 404 | `{"mensaje": "Paciente no encontrado: 999"}` |
| Documento duplicado | 409 | `{"mensaje": "Ya existe un paciente con el documento: 70000001"}` |

## Autor

**Christopher Edilson Capcha Gamarra** — IDAT, Desarrollo de Servicios Web I (2026).
