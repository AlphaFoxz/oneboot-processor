package com.github.alphafoxz.oneboot_processor.processors;

import com.github.alphafoxz.oneboot_processor.annotations.OnebootMapper;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.Set;

@SupportedSourceVersion(SourceVersion.RELEASE_17) // 指定支持的源代码版本
@AutoService(Processor.class)
public class OnebootMapperProcessor extends AbstractProcessor {
    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.messager = processingEnv.getMessager();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(OnebootMapper.class)) { //遍历每个类
            try {
                if (ElementKind.INTERFACE != element.getKind()) {
                    messager.printMessage(Diagnostic.Kind.ERROR, element.getSimpleName() + ": Only interfaces can be annotated with @OnebootMapper.");
                    return true;
                }
                String packageName = processingEnv.getElementUtils().getPackageOf(element).getQualifiedName().toString();
                String targetClassName = element.getSimpleName() + "Impl";
                TypeSpec.Builder clazzBuilder = TypeSpec.classBuilder(targetClassName)
                        .addModifiers(Modifier.PUBLIC);
                for (Element enclosedElement : element.getEnclosedElements()) { //遍历每个方法/成员变量
                    if (enclosedElement.getKind() != ElementKind.METHOD) {
                        continue;
                    }
                    MethodSpec method = MethodSpec.methodBuilder(enclosedElement.getSimpleName().toString())
                            .addModifiers(Modifier.PUBLIC)
                            .returns(Void.class)
                            .addStatement("return null")
                            .build();
                    clazzBuilder.addMethod(method);
                }
                JavaFile file = JavaFile.builder(packageName, clazzBuilder.build())
                        .build();
                file.writeTo(processingEnv.getFiler());
            } catch (Exception e) {
                messager.printMessage(Diagnostic.Kind.ERROR, "Failed to generate class for " + element.getSimpleName() + ": " + e.getMessage());
            }
        }
        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Set.of(Const.ANNOTATIONS_PREFIX + OnebootMapper.class.getSimpleName());
    }
}
