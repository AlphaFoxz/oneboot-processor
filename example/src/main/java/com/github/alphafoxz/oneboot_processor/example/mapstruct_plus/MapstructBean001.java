package com.github.alphafoxz.oneboot_processor.example.mapstruct_plus;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

@AutoMapper(target = MapstructBean002.class)
@Data
public class MapstructBean001 {
    private long id;
    private String name;
    private int age;
}
