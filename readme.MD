## GraalVM Test

Aplicación con Spring Reactive y base de datos R2DBC con H2,  que  muestra las diferencias entre compilar nativamente y con javac.

Para compilar a nativo usar el comando: 

> mvn -Pnative spring-boot:build-image

**Nota**: NO es necesario tener la VM de GraalVM.

Tener en cuenta que la compilación a nativo lleva mucho tiempo. En mi equipo alrededor de 8 minutos. Ejemplo de salida final después de la compilación nativa.

```
[INFO] Successfully built image 'docker.io/library/graalvm-test:0.0.1-SNAPSHOT'
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  07:20 min

```
En este ejemplo se muestra como el uso de reflexión hace que la función falle en el endpoint 'hello' cuando se compila nativamente.



**Aclaración:** En windows es complicado compilar nativamente. Usaremos compilación a imagen docker.

- Crear la imagen con spring Native (graalvm)
Una vez generada la imagen docker la ejecutamos:

> docker run --rm  -it --name graalvm-test-native -p 8081:8080 docker.io/library/graalvm-test:0.0.1-SNAPSHOT 

- Crear la imagen en docker SIN spring native
```
 mvn package 
 docker build . -t graalvm-test
 docker run --rm  -it --name graalvm-test -p 8080:8080 graalvm-test 
```
