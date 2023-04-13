# sumarize-api

## Entorno de desarrollo

### Requisitos

- JDK 17 o superior, Maven
- Editor de código preferido (ej. Intellij)
- Servidor PostgreSql (puede usarse un container docker)

### Pasos

- Clonar el repositorio
- Abrir el proyecto con el editor de código
- Para levantar el servidor postgresql en un container, ejecutar la siguiente instrucción en la consola:

```sh
docker run -d --name db_dev \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=postgres \
  -p 5432:5432 \
  -v dbdata:/var/lib/postgresql/data_dev \
  postgres:14.1-alpine
```
- Configurar los datos de conexión a la base de datos, en el archivo application.properties. (Si se usó docker, dejarlo como está).
- Ejecutar la aplicación desde el editor de código
- Importar en Postman la collection desde el archivo tenpo_api.json
- Probar los endpoints desde la collección en Postman


## Contaneirización

- Seguir los pasos indicados para el Entorno de desarrollo
- Ejecutar en la consola en la raíz del proyecto
```sh
mvn clean package
docker-compose up
```
- Probar los endpoints desde la colección de Postman




