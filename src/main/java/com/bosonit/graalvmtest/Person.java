package com.bosonit.graalvmtest;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.UUID;


@Data
public class Person {
    @Id
    int id;

    String name;
    String city;

}
