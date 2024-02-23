package com.github.alphafoxz.oneboot_processor.processors;

import com.github.alphafoxz.oneboot_processor.annotations.OnebootTest;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import java.io.IOException;
import java.util.Set;

@SupportedSourceVersion(SourceVersion.RELEASE_17) // 指定支持的源代码版本
@AutoService(Processor.class)
public class OnebootTestProcessor extends OnebootProcessor<OnebootTest> {
    @Override
    public synchronized boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(OnebootTest.class)) {
            String testPackageName = processingEnv.getElementUtils().getPackageOf(element).getQualifiedName().toString();
            printNote("testPackageName: " + testPackageName);
            String testClassName = element.getSimpleName() + "Impl";
            printNote("testClassName: " + testClassName);
            String testMethodName = "get" + element.getSimpleName().toString();
            printNote("testMethodName: " + testMethodName);

            MethodSpec testMethod = MethodSpec.methodBuilder(testMethodName)
                    .addModifiers(Modifier.PUBLIC)
                    .returns(String.class)
                    .addStatement("return null") // Replace with actual getter logic
                    .build();

            TypeSpec testClass = TypeSpec.classBuilder(testClassName)
                    .addModifiers(Modifier.PUBLIC)
                    .addMethod(testMethod)
                    .build();

            JavaFile javaFile = JavaFile.builder(testPackageName, testClass)
                    .build();

            try {
                javaFile.writeTo(processingEnv.getFiler());
            } catch (IOException e) {
                printError("Failed to generate class for " + element.getSimpleName());
            }
        }
        return true;
    }
}
