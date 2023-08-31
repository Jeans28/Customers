
# Microservicio Customer

Esta API proporciona endpoints para gestionar clientes y obtener indicadores de natalidad entro otros.

## Requisitos

- Java 8 o superior
- MySQL 8
- Maven

## Instalación

1. Clona este repositorio:

bash
git clone https://github.com/tuusuario/ms-customers.git

Configura la base de datos MySQL en el archivo application.properties:
spring.datasource.url=jdbc:mysql://localhost:3306/intercop
spring.datasource.username=usuario
spring.datasource.password=contraseña

Nota: Puedes cambiar de motor de base de datos agregando el driver en la dependencia del maven y cambiar los properties de conexion.

Compila el proyecto con Maven:
mvn clean package

Ejecuta la aplicación:
java -jar target/ms-customers.jar
La API estará disponible en http://localhost:8090/api/customer.

Endpoints
Registrar Cliente Nuevo
Endpoint: POST /api/customer

Permite registrar un nuevo cliente.

Consultar Clientes
Endpoint: GET /api/customer

Permite consultar clientes por DNI, email o sin filtros.

Consultar Indicadores de Natalidad
Endpoint: GET /api/customer/indicadores

Permite consultar indicadores de natalidad, nacidos por mes, entre otros.

Documentación
La documentación completa de la API se puede encontrar en http://localhost:8090/swagger-ui.html.

Autor
Jeanspierre Calzado
Correo electrónico: jeansc42@gmail.com
