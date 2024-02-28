package com.github.alphafoxz.oneboot_processor.example.converter.beans;

import com.github.alphafoxz.oneboot_processor.annotations.DefineBean;
import lombok.Data;

import java.util.Date;

@DefineBean
@Data
public class ConverterBean001 {
    private Long id;
    private String name;
    private Integer age;
    private Date createTime;
}
