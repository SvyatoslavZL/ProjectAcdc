package com.javarush.khmelov.config;

import lombok.SneakyThrows;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;

public class NanoSpring {

    private static final Map<Class<?>, Object> components = new HashMap<>();

    @SuppressWarnings("unchecked")
    @SneakyThrows
    public static <T> T find(Class<T> aClass) {
        if (beanDefinitions.isEmpty()) {
            init(); //1.add abstraction<?>
        }
        Object component = components.get(aClass);
        if (component == null) {
            Constructor<?> constructor = aClass.getConstructors()[0];
            Class<?>[] parameterTypes = constructor.getParameterTypes();
            Type[] genericParameterTypes = constructor.getGenericParameterTypes(); //2.
            Object[] parameters = new Object[parameterTypes.length];
            for (int i = 0; i < parameters.length; i++) {
                Class<?> impl = findImpl(parameterTypes[i], genericParameterTypes[i]); //3.
                parameters[i] = find(impl);
            }
            Object newInstance = constructor.newInstance(parameters);
            components.put(aClass, newInstance);
        }
        return (T) components.get(aClass);
    }


    //********************* add support abstraction<?>  ************************

    private static final List<Class<?>> beanDefinitions = new ArrayList<>();


    @SneakyThrows
    private static void init() {
        URL resource = NanoSpring.class.getResource("NanoSpring.class");
        URI uri = Objects.requireNonNull(resource).toURI();
        Path appRoot = Path.of(uri).getParent().getParent();
        scanPackages(appRoot);
    }

    public static void scanPackages(Path appPackage, String... excludes) {
        String prefix = "/classes/";
        int offset = prefix.length();
        String end = ".class";
        try (Stream<Path> walk = Files.walk(appPackage)) {                          //в папке
            List<String> names = walk.map(Path::toString)                          //рекурсия по
                    .filter(o -> o.endsWith(end))                                 //всем классам
                    .filter(o -> Arrays.stream(excludes).noneMatch(o::contains)) //кроме запрещенных
                    .map(s -> s.substring(s.indexOf(prefix) + offset)) //del ".../classes/"
                    .map(s -> s.replace(end, ""))                    //и ".class"
                    .map(s -> s.replace(File.separator, "."))       //через точки
                    .toList();                                               //собранные как строки
            for (String name : names) {                                     //которые переведем
                beanDefinitions.add(Class.forName(name));                  //в классы
            }                                                             //готово
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static Class<?> findImpl(Class<?> aClass, Type type) {
        for (Class<?> beanDefinition : beanDefinitions) {
            boolean assignable = aClass.isAssignableFrom(beanDefinition);
            boolean nonGeneric = beanDefinition.getTypeParameters().length == 0;
            boolean nonInterface = !beanDefinition.isInterface();
            boolean nonAbstract = !Modifier.isAbstract(beanDefinition.getModifiers());
            boolean checkGenerics = checkGenerics(type, beanDefinition);
            if (assignable & nonGeneric & nonInterface & nonAbstract && checkGenerics) {
                return beanDefinition;
            }
        }
        throw new RuntimeException("Not found impl for %s (type=%s)".formatted(aClass, type));
    }

    private static boolean checkGenerics(Type baseType, Class<?> impl) {
        Class<?>[] baseGeneric = getInfoGeneric(baseType);
        if (baseGeneric.length == 0) {
            return true;
        }
        List<Type> types = new ArrayList<>();
        while (impl != null) {
            types.add(impl.getGenericSuperclass());
            types.addAll(List.of(impl.getGenericInterfaces()));
            impl = impl.getSuperclass();
        }
        for (Type implType : types) {
            if (implType != null) {
                Class<?>[] implGeneric = getInfoGeneric(implType);
                if (implGeneric.length == baseGeneric.length) {
                    boolean ok = true;
                    for (int i = 0; i < baseGeneric.length; i++) {
                        ok = ok && baseGeneric[i].equals(implGeneric[i]);
                    }
                    if (ok) return true;
                }
            }
        }
        return false;
    }

    private static Class<?>[] getInfoGeneric(Type type) {
        int start = type.getTypeName().indexOf("<") + 1;
        if (start < 1) {
            return new Class[0];
        }
        int stop = type.getTypeName().indexOf(">");
        String[] types = type.getTypeName().substring(start, stop).split(",");
        Class<?>[] classes = new Class[types.length];
        for (int i = 0, typesLength = types.length; i < typesLength; i++) {
            try {
                classes[i] = Class.forName(types[i]); //конкретный тип
            } catch (ClassNotFoundException e) {
                classes[i] = null; // T, K, E, V, ?, ? extends, ? super
            }
        }
        return classes;
    }
}
