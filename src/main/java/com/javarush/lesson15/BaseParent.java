package com.javarush.lesson15;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
@SuperBuilder
@NoArgsConstructor
public class BaseParent extends AbstractEntity<Long> {
    String name;
    String email;
}
