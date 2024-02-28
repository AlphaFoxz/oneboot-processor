package com.github.alphafoxz.oneboot_processor.example.mapper;

import com.github.alphafoxz.oneboot_processor.example.mapper._compile_only.MapperBean001Fields;
import com.github.alphafoxz.oneboot_processor.example.mapper._compile_only.MapperBean003Fields;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface DemoMapper {
    @Mapping(source = "name", target = "username")
    MapperBean003 convert(MapperBean001 source);
}
