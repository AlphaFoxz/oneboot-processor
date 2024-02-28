package com.github.alphafoxz.oneboot_processor.example;

import com.github.alphafoxz.oneboot_processor.example.mapstruct_plus.MapstructBean001;
import com.github.alphafoxz.oneboot_processor.example.mapstruct_plus.MapstructBean002;
import io.github.linpeilie.Converter;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

@SpringBootTest
@ComponentScan("com.github.alphafoxz.oneboot_processor.example.*")
public class MapstructPlusTest {
    @Resource
    private Converter converter;

    @Test
    public void test001() {
        MapstructBean001 bean001 = new MapstructBean001();
        bean001.setAge(1);
        bean001.setId(1);
        bean001.setName("bill");
        MapstructBean002 bean002 = converter.convert(bean001, MapstructBean002.class);
        System.out.println(bean002);
    }
}
