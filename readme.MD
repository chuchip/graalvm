**Aclaración:** En windows es complicado compilar nativamente. Usaremos compilacion a imagen docker.

- Crear la imagen con spring Native (graalvm)
Una vez generada la imagen docker la ejecutamos:

> docker run --rm  -it --name graalvm-test-native -p 8081:8080 docker.io/library/graalvm-test:0.0.1-SNAPSHOT 

- Crear la imagen en docker SIN spring native
```
 mvn package 
 docker build . -t graalvm-test
 docker run --rm  -it --name graalvm-test -p 8080:8080 graalvm-test 
```
