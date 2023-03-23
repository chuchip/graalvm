package com.bosonit.graalvmtest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;

@SpringBootApplication
@Slf4j
public class GraalvmTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(GraalvmTestApplication.class, args);
	}

	@Bean
	RouterFunction<ServerResponse> updateRegsister(PersonRepository personRepository)
	{

		return RouterFunctions.route(PUT("/{id}"), request -> {
			int id = Integer.valueOf(request.pathVariable("id"));
			log.info("En update: "+id);
			Mono<Person> myRequestMono = request.bodyToMono(Person.class);
			return myRequestMono.flatMap(personBody -> {
				Mono<Person> myRegistryDB = personRepository.findById(id);
				return myRegistryDB.flatMap(personDB -> {
					personBody.setId(personDB.getId());
					Mono<Person> savedMyRegistryMono = personRepository.save(personBody);
					return savedMyRegistryMono.flatMap(savedMyRegistry -> ServerResponse.ok().bodyValue(savedMyRegistry));
				}).switchIfEmpty(ServerResponse.notFound().build());
			});
		});
	}
}
