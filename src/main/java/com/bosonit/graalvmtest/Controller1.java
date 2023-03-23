package com.bosonit.graalvmtest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

@RestController
@RequiredArgsConstructor
@Slf4j
public class Controller1 {

    private final PersonRepository personRepository;
    @GetMapping("hello")
    Mono<String>  saluda() throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException {
        log.info("EN function saluda");
        Class clase=Class.forName("com.bosonit.graalvmtest.ClaseExterna");
        Constructor constructor =  Arrays.stream(clase.getDeclaredConstructors()).findFirst().orElseThrow();
        Object objeto=constructor.newInstance();
        String result="<NOT EXECUTED>";
        for (var method: clase.getDeclaredMethods())
        {
            if ( method.getName().equals("saluda"))
                result=method.invoke(objeto,"Chuchi").toString();
        };
        return Mono.just(result);
    }

    @PostMapping("/")
    Mono<Person> add(@RequestBody Person person)
    {
        log.info("Function add");
        return personRepository.save(person);
    }
    @DeleteMapping("/{id}")
    Mono delete(@PathVariable final int id)
    {
        log.info("Function delete: "+id);

        return personRepository.findById(id)
                .flatMap(existingUser -> personRepository.delete(existingUser)
                        .then(Mono.just(existingUser)));
    }
    @GetMapping("/")
    Flux<Person> list()
    {
        log.info("Function List");
        return personRepository.findAll();
    }


}
