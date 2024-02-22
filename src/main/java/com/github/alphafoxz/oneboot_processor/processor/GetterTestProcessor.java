package com.github.alphafoxz.oneboot_processor.processor;

import com.github.alphafoxz.oneboot_processor.anno.GetterTest;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.util.Set;

@SupportedAnnotationTypes("com.github.alphafoxz.oneboot_annotation.*") // 指定处理器应处理的注解类型
@SupportedSourceVersion(SourceVersion.RELEASE_17) // 指定支持的源代码版本
@AutoService(Processor.class)
public class GetterTestProcessor extends AbstractProcessor {
    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.messager = processingEnv.getMessager();
    }

    @Override
    public synchronized boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(GetterTest.class)) {
//            if (element.getKind() != ElementKind.FIELD) {
//                messager.printMessage(Diagnostic.Kind.ERROR, "GetterTest annotation can only be applied to fields.");
//                return true;
//            }
            String packageName = processingEnv.getElementUtils().getPackageOf(element).getQualifiedName().toString();
            messager.printMessage(Diagnostic.Kind.NOTE, "packageName: " + packageName);
            String className = element.getSimpleName() + "Getter";
            messager.printMessage(Diagnostic.Kind.NOTE, "className: " + className);
            String getterMethodName = "get" + element.getSimpleName().toString();
            messager.printMessage(Diagnostic.Kind.NOTE, "getterMethodName: " + getterMethodName);

            MethodSpec getterMethod = MethodSpec.methodBuilder(getterMethodName)
                    .addModifiers(Modifier.PUBLIC)
                    .returns(String.class)
                    .addStatement("return null") // Replace with actual getter logic
                    .build();

            TypeSpec getterClass = TypeSpec.classBuilder(className)
                    .addModifiers(Modifier.PUBLIC)
                    .addMethod(getterMethod)
                    .build();

            JavaFile javaFile = JavaFile.builder(packageName, getterClass)
                    .build();

            try {
                javaFile.writeTo(processingEnv.getFiler());
            } catch (IOException e) {
                messager.printMessage(Diagnostic.Kind.ERROR, "Failed to generate class for " + element.getSimpleName());
            }
        }
        return true;
    }
}
