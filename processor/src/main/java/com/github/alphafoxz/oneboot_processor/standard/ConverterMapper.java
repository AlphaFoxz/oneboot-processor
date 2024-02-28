package com.github.alphafoxz.oneboot_processor.standard;

import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public interface ConverterMapper<S, T> {
    T convert(S var1);

    T convert(S var1, @MappingTarget T var2);

    default List<T> convert(List<S> sourceList) {
        return sourceList == null ? null : sourceList.stream().map(this::convert).collect(Collectors.toList());
    }

    default Set<T> convert(Set<S> sourceSet) {
        return sourceSet == null ? null : sourceSet.stream().map(this::convert).collect(Collectors.toSet());
    }
}
