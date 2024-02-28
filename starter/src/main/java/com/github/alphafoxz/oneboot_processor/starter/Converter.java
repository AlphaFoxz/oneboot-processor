package com.github.alphafoxz.oneboot_processor.starter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@Slf4j
@Component
public class Converter {
    public <S, T> T convert(S source, Class<T> targetClass) throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        try {
            return convert(source, targetClass.getDeclaredConstructor().newInstance());
        } catch (InvocationTargetException
                 | InstantiationException
                 | IllegalAccessException
                 | NoSuchMethodException e) {
            throw e;
        }
    }

    public <S, T> T convert(S source, T target) {
        return null;
    }

    public <S, T> Iterable<T> convert(Iterable<S> source, Class<T> targetClass) {
        // TODO
        return null;
    }

    public <T> T convert(Map<String, ?> map, Class<T> targetClass) {
        return null;
    }
}
