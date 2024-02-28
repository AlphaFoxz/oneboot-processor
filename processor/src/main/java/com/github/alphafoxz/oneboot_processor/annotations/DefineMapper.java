package com.github.alphafoxz.oneboot_processor.annotations;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.lang.annotation.*;

@Inherited
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.CLASS)
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public @interface DefineMapper {
}
