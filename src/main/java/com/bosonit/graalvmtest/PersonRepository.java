package com.bosonit.graalvmtest;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface PersonRepository extends ReactiveCrudRepository<Person, Integer> {

}
