package com.javarush.kovalinsky.lesson14.repository;

import java.util.Collection;

public interface Repository<T> {

    Collection<T> getAll();
}
