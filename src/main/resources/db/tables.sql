CREATE SCHEMA db_pacientes;
USE db_pacientes;
CREATE TABLE paciente (
      id                BIGINT AUTO_INCREMENT NOT NULL PRIMARY KEY,
      nombres           VARCHAR(100) NOT NULL,
      apellidos         VARCHAR(100) NOT NULL,
      documento         VARCHAR(20)  NOT NULL,
      fecha_nacimiento  DATE         NOT NULL,
      activo            BOOLEAN      NOT NULL,
        CONSTRAINT UQ_PACIENTE_DOCUMENTO UNIQUE (documento)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;
