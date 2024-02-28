package com.github.alphafoxz.oneboot_processor.example.mapper;

import com.github.alphafoxz.oneboot_processor.annotations.DefineBean;
import lombok.Data;

import java.util.Date;

@DefineBean
@Data
public class MapperBean001 {
    private Long id;
    private String name;
    private Integer age;
    private Date createTime;
}
