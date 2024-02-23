package com.github.alphafoxz.oneboot_processor.processors;

import com.github.alphafoxz.oneboot_processor.annotations.OnebootTest;
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

@SupportedSourceVersion(SourceVersion.RELEASE_17) // 指定支持的源代码版本
@AutoService(Processor.class)
public class OnebootTestProcessor extends AbstractProcessor {
    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.messager = processingEnv.getMessager();
    }

    @Override
    public synchronized boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(OnebootTest.class)) {
//            if (element.getKind() != ElementKind.FIELD) {
//                messager.printMessage(Diagnostic.Kind.ERROR, "GetterTest annotation can only be applied to fields.");
//                return true;
//            }
            String testPackageName = processingEnv.getElementUtils().getPackageOf(element).getQualifiedName().toString();
            messager.printMessage(Diagnostic.Kind.NOTE, "testPackageName: " + testPackageName);
            String testClassName = element.getSimpleName() + "Impl";
            messager.printMessage(Diagnostic.Kind.NOTE, "testClassName: " + testClassName);
            String testMethodName = "get" + element.getSimpleName().toString();
            messager.printMessage(Diagnostic.Kind.NOTE, "testMethodName: " + testMethodName);

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
                messager.printMessage(Diagnostic.Kind.ERROR, "Failed to generate class for " + element.getSimpleName());
            }
        }
        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Set.of(Const.ANNOTATIONS_PREFIX + OnebootTest.class.getSimpleName());
    }
}
