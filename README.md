### Module Admin Backend

### Requisitos
- Oracle 19c
- Java JDK 20
- IntelliJ IDEA 2023.1 (Ultimate Edition)

### Configuración de la base de datos
En caso de no tener instalado Oracle19c

Opcionalmente se puede ejecutar el docker-compose adjunto en este proyecto
ejecutando el siguiente comando en la raiz del proyecto ya sea en la terminal de intelij o en la terminal de su sistema operativo

```shell
docker-compose up
```

Credenciales requeridas en caso de ya tener Oracle19c

```shell
spring.datasource.url=jdbc:oracle:thin:@localhost:1521:xe
spring.datasource.username=system
spring.datasource.password=oracle
```

#### 1. Crear Secuencias

```sql
CREATE SEQUENCE user_sequence START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE token_sequence START WITH 1 INCREMENT BY 1;
```

#### 2. Crear Tablas

```sql
CREATE TABLE role_app (
    id VARCHAR2(10) PRIMARY KEY,
    name VARCHAR2(50) NOT NULL
);


CREATE TABLE app_user (
    id NUMBER(10) PRIMARY KEY,
    name VARCHAR2(90) NOT NULL,
    email VARCHAR2(60) NOT NULL UNIQUE,
    password VARCHAR2(255) NOT NULL,
    role_id VARCHAR2(10) NOT NULL,
    CONSTRAINT fk_role
        FOREIGN KEY (role_id)
        REFERENCES role_app (id)
);

CREATE TABLE token (
  id NUMBER PRIMARY KEY,
  token VARCHAR2(255) NOT NULL UNIQUE,
  token_type VARCHAR2(15) NOT NULL,
  revoked NUMBER(1) DEFAULT 0,
  expired NUMBER(1) DEFAULT 0,
  user_id NUMBER,
  CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES app_user(id)
);
```

#### 3. Crear datos de usuarios de prueba


##### 3.1 Crear roles

```sql

INSERT INTO role_app (id, name)
VALUES ('admin', 'ADMIN');

INSERT INTO role_app (id, name)
VALUES ('readonly', 'READONLY');

```

##### 3.2 Crear usuarios

```sql
INSERT INTO app_user (id, name, email, password,role_id)
VALUES (user_sequence.NEXTVAL, 'Edwin Gutierrez', 'edwin.gutierrez@example.com', '$2a$12$T6ptiROxa1eMgpqEx1HKcOXo/BWUhS5tdFcJWB/HO3i8wEp9Lr06K','admin');

INSERT INTO app_user (id, name, email, password,role_id)
VALUES (user_sequence.NEXTVAL, 'Edwin', 'john.doe@example.com', '$2a$12$T6ptiROxa1eMgpqEx1HKcOXo/BWUhS5tdFcJWB/HO3i8wEp9Lr06K','readonly');

```

El password encryptado equivale a: password123


### Iniciar proyecto utilizando IntelliJ IDEA

