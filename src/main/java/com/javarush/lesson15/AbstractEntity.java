package com.javarush.lesson15;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public abstract class AbstractEntity<T extends Serializable> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    T id;

}


