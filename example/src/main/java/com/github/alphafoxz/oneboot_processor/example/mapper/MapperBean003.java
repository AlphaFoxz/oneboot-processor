package com.github.alphafoxz.oneboot_processor.example.mapper;

import com.github.alphafoxz.oneboot_processor.annotations.DefineBean;
import lombok.Data;

@DefineBean
@Data
public class MapperBean003 {
    private Long id;
    private String username;
    private Integer age;

    public Long value1() {
        return id;
    }
//    private Long createTime;
}
