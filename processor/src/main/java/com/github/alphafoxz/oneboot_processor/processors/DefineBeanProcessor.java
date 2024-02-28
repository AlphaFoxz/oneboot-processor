package com.github.alphafoxz.oneboot_processor.processors;

import com.github.alphafoxz.oneboot_processor.annotations.DefineBean;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.*;
import javax.lang.model.util.Elements;
import java.util.Set;

@AutoService(Processor.class)
public class DefineBeanProcessor extends AbstractProcessor<DefineBean> {
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnv) {
        Elements elementUtils = processingEnv.getElementUtils();
        if (roundEnv.processingOver()) {
            // 处理完成，不做任何操作
            return false;
        }
        if (roundEnv.getRootElements().isEmpty()) {
            // 没有根元素，可能是后续轮次
            return false;
        }
        for (Element element : roundEnv.getElementsAnnotatedWith(DefineBean.class)) { //遍历每个类
            try {
                if (ElementKind.CLASS != element.getKind()) {
                    continue;
                }
                String packageName = elementUtils.getPackageOf(element).getQualifiedName().toString() + "._compile_only";
                String targetInterName = element.getSimpleName() + "Fields";
                TypeSpec.Builder interBuilder = TypeSpec.interfaceBuilder(targetInterName)
                        .addModifiers(Modifier.PUBLIC);
                for (Element enclosedElement : element.getEnclosedElements()) { //遍历每个方法/成员变量
                    // TODO 考虑RECORD的情况
                    if (enclosedElement.getKind() != ElementKind.METHOD) {
                        continue;
                    }
//                    printNote(enclosedElement.getSimpleName().toString());
                    ExecutableElement executableElement = (ExecutableElement) enclosedElement;
                    String fieldName = executableElement.getSimpleName().toString();
                    if (fieldName.startsWith("get") && !"get".equals(fieldName)) {
                        fieldName = fieldName.toLowerCase().charAt(3) + fieldName.substring(4);
                    } else if (fieldName.startsWith("is") && !"is".equals(fieldName)) {
                        fieldName = fieldName.toLowerCase().charAt(2) + fieldName.substring(3);
                    } else {
                        continue;
                    }
                    FieldSpec fieldSpec = FieldSpec.builder(String.class, Func.camelToUnderline(fieldName).toUpperCase(), Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                            .initializer("$S", fieldName)
                            .build();
                    interBuilder.addField(fieldSpec);
                }
                JavaFile file = JavaFile.builder(packageName, interBuilder.build())
                        .build();
                file.writeTo(processingEnv.getFiler());
            } catch (Exception e) {
                printError("ERROR: Failed to process for type: " + element.getSimpleName(), e);
            }
        }
        roundEnv.processingOver();
        return false;
    }
}