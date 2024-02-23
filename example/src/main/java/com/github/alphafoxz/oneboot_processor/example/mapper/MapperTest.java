package com.github.alphafoxz.oneboot_processor.example.mapper;

import com.github.alphafoxz.oneboot_processor.annotations.OnebootMapper;
import com.github.alphafoxz.oneboot_processor.example.mapper.Bean002;

@OnebootMapper
public interface MapperTest {
    Bean002 toBean002(Bean002 bean001);
}
