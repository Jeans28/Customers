
# Microservicio Customers

Esta API proporciona endpoints para gestionar clientes y obtener indicadores de natalidad entro otros.

## Requisitos

- Java 8 o superior
- MySQL 8
- Maven

## Instalación

1. Clona este repositorio:

      bash
      git clone [https://github.com/jeans28/ms-customers.git](https://github.com/Jeans28/Customers.git)

2. Configura la base de datos MySQL en el archivo application.properties:

      spring.datasource.url=jdbc:mysql://localhost:3306/intercop
      
      spring.datasource.username=usuario
      
      spring.datasource.password=contraseña
      
      nota: Puedes cambiar de motor de base de datos agregando el driver en la dependencia del maven y cambiar los properties de conexion
  
3. Compila el proyecto con Maven:
   
        mvn clean package
  
4. Ejecuta la aplicación:
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
