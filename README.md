# Rest Api for user creation - Code Challenge
api rest para la creación de usuarios

## Step by Step Guide
Las instrucciones basicamente son:

    1.Correr la clase 'CodechallengeApplication.java'
    2.Una vez corriendo la aplicación, podes ir al siguiente link -> http://localhost:3000/h2-ui
    3.Loguearse en la interface de h2 usando la db -> jdbc:h2:mem:challenge

## Swagger
Una vez levantado el ambiente podemos usar swagger en la siguiente url

 - http://localhost:3000/swagger-ui/index.html#/User/

#### Autenticacion con swagger
una vez en la ui de swagger, podemos hacer un signup, y luego un signin para obtener el token e ingresarlo en las request que tienen el 
candado de authorize

### Roles - creación

    podemos correr el siguiente script en la UI de h2
    
    INSERT INTO roles(name) VALUES('ROLE_USER');
    INSERT INTO roles(name) VALUES('ROLE_MODERATOR');
    INSERT INTO roles(name) VALUES('ROLE_ADMIN');
