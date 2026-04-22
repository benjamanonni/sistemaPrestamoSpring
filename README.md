# Sistema de Préstamos - Java + Spring Boot

Tercera versión de un sistema de gestión de préstamos desarrollada en Java con Spring Boot, enfocada en mejorar la arquitectura del proyecto, el modelado del dominio y la persistencia mediante JPA/Hibernate.

## Descripción

Este proyecto representa una evolución de un sistema de préstamos previamente desarrollado en versiones sin persistencia y luego con MySQL + JDBC.  
En esta etapa, el sistema fue reorganizado utilizando Spring Boot, Spring Data JPA y una separación más clara entre dominio, datos y servicios.

El objetivo principal fue aplicar conceptos de programación orientada a objetos, arquitectura en capas, relaciones entre entidades y reglas de negocio reales sobre usuarios, recursos, préstamos y sanciones.

## Funcionalidades

- Gestión de usuarios
- Gestión de recursos
- Gestión de préstamos
- Gestión de sanciones
- Validación de límites por tipo de usuario
- Restricción de recursos según tipo de usuario
- Registro de devoluciones
- Identificación de préstamos vencidos
- Consulta de historial por recurso
- Persistencia con Spring Data JPA

## Tecnologías utilizadas

- Java
- Spring Boot
- Spring Data JPA
- MySQL
- Lombok
- Maven
- Logback

## Estructura del proyecto

- `DOMINIO`: entidades y enums del sistema
- `DATOS`: repositorios JPA
- `SERVICIO`: lógica de negocio
- `DemoApplication.java`: punto de entrada de la aplicación
- `application.properties`: configuración principal
- `logback-spring.xml`: configuración de logs

## Configuración principal

En `application.properties` se utilizan configuraciones como:

- `spring.jpa.hibernate.ddl-auto=none`
- `spring.jpa.show-sql=true`
- `spring.main.web-application-type=none`

### Significado

- `ddl-auto=none`: evita que Hibernate modifique automáticamente la base de datos
- `show-sql=true`: muestra las consultas generadas
- `web-application-type=none`: ejecuta la aplicación en modo consola, sin levantar servidor web

## Conceptos aplicados

- Programación orientada a objetos
- Arquitectura en capas
- Inyección de dependencias
- Persistencia con JPA/Hibernate
- Relaciones entre entidades (`@ManyToOne`, `@JoinColumn`)
- Repositorios con `JpaRepository`
- Servicios con `@Service`
- Validaciones con `@NotNull`, `@NotBlank`, `@Email`
- Transacciones de modificación con `@Transactional`
- Consultas derivadas de Spring Data JPA

## Aprendizajes clave

Durante el desarrollo de esta versión se trabajó especialmente en:

- entender la diferencia entre trabajar con objetos en JPA y trabajar con claves manuales en JDBC
- mapear entidades y relaciones hacia tablas de MySQL
- reemplazar acceso manual con JDBC por repositorios basados en JPA
- desacoplar mejor la lógica de negocio del acceso a datos
- comprender el rol de `@Service`, `@Autowired`, `@Modifying` y `@Transactional`
- optimizar consultas evitando filtros innecesarios en memoria

## Cambios respecto a versiones anteriores

Esta versión forma parte de una evolución en tres etapas:

1. **Sin MySQL**: modelado del dominio y lógica de negocio en memoria
2. **Con MySQL + JDBC**: persistencia relacional manual mediante DAOs
3. **Con Spring Boot + JPA**: reorganización del sistema con repositorios, entidades relacionadas y servicios

## Posibles mejoras

- Mejorar el manejo de excepciones con una jerarquía más específica
- Reemplazar inyección por campo por inyección por constructor
- Optimizar algunas consultas de negocio
- Incorporar controladores REST
- Exponer el sistema como API web
- Refactorizar algunos servicios para un desacople mayor

## Estado del proyecto

Proyecto funcional de práctica, conservado como tercera versión evolutiva del sistema.

## Autor

Proyecto desarrollado como práctica de aprendizaje en Ingeniería en Sistemas.

