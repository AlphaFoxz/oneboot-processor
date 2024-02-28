package com.github.alphafoxz.oneboot_processor.example.converter.beans;

import com.github.alphafoxz.oneboot_processor.annotations.DefineBean;
import lombok.Data;

@DefineBean
@Data
public class ConverterBean003 {
    private Long id;
    private String username;
    private Integer age;
    private Long createTime;
}
