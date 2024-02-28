package com.github.alphafoxz.oneboot_processor.example.converter;

import com.github.alphafoxz.oneboot_processor.annotations.DefineConverter;
import com.github.alphafoxz.oneboot_processor.annotations.Mapping;
import com.github.alphafoxz.oneboot_processor.example.converter.beans.ConverterBean001;
import com.github.alphafoxz.oneboot_processor.example.converter.beans.ConverterBean003;
import com.github.alphafoxz.oneboot_processor.example.converter.beans._compile_only.ConverterBean001Fields;
import com.github.alphafoxz.oneboot_processor.example.converter.beans._compile_only.ConverterBean003Fields;

import java.util.Date;

@DefineConverter
public interface DemoConverter {
    default ConverterBean001 convert(ConverterBean003 source) {
        ConverterBean001 result = new ConverterBean001();
        result.setId(source.getId());
        result.setName(source.getUsername());
        result.setAge(source.getAge());
        if (source.getCreateTime() != null) {
            result.setCreateTime(new Date(source.getCreateTime()));
        }
        return result;
    }

//    default Function<ConverterBean001, ConverterBean002> convert() {
//        return (bean001) -> {
//            return new ConverterBean002();
//        };
//    }

    @Mapping(source = ConverterBean001Fields.NAME, target = ConverterBean003Fields.USERNAME)
    ConverterBean003 convert1To3(ConverterBean001 bean);
}
