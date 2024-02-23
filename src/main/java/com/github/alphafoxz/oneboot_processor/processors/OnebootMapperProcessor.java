package com.github.alphafoxz.oneboot_processor.processors;

import com.github.alphafoxz.oneboot_processor.annotations.OnebootMapper;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeSpec;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SupportedSourceVersion(SourceVersion.RELEASE_17) // 指定支持的源代码版本
@AutoService(Processor.class)
public class OnebootMapperProcessor extends OnebootProcessor<OnebootMapper> {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (roundEnv.processingOver()) {
            // 处理完成，不做任何操作
            return false;
        }
        if (roundEnv.getRootElements().isEmpty()) {
            // 没有根元素，可能是后续轮次
            return false;
        }
        Mapper mapper = Func.createAnnotation(Mapper.class, Map.of("componentModel", MappingConstants.ComponentModel.SPRING));
        for (Element element : roundEnv.getElementsAnnotatedWith(OnebootMapper.class)) { //遍历每个类
            try {
                if (ElementKind.INTERFACE != element.getKind()) {
                    printError(": Only interfaces can be annotated with @OnebootMapper.");
                    return true;
                }
                String packageName = processingEnv.getElementUtils().getPackageOf(element).getQualifiedName().toString();
                String targetClassName = "I" + element.getSimpleName();
                TypeSpec.Builder interBuilder = TypeSpec.interfaceBuilder(targetClassName)
                        .addAnnotation(Func.fromAnnotation(mapper))
                        .addModifiers(Modifier.PUBLIC);
                for (Element enclosedElement : element.getEnclosedElements()) { //遍历每个方法/成员变量
                    if (enclosedElement.getKind() != ElementKind.METHOD) {
                        continue;
                    }
                    ExecutableElement executableElement = (ExecutableElement) enclosedElement;
                    List<ParameterSpec> parameterSpecs = Func.fromParamElements(executableElement.getParameters());
                    MethodSpec method = MethodSpec.methodBuilder(enclosedElement.getSimpleName().toString())
                            .addModifiers(Modifier.PUBLIC)
                            .addParameters(parameterSpecs)
                            .returns(executableElement.getReturnType().getClass())
                            .addStatement("return null")
                            .build();
                    interBuilder.addMethod(method);
                }
                JavaFile file = JavaFile.builder(packageName, interBuilder.build())
                        .build();
                file.writeTo(processingEnv.getFiler());
            } catch (Exception e) {
                printError("Failed to generate class for " + element.getSimpleName() + ": " + e.getMessage());
            }
        }
        return true;
    }
}
