package com.github.alphafoxz.oneboot_processor.example.mapstruct_plus;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

@AutoMapper(target = MapstructBean001.class)
@Data
public class MapstructBean002 {
    private long id;
    private String name;
    private int age;
}
