package com.github.alphafoxz.oneboot_processor.processors;

import com.github.alphafoxz.oneboot_processor.annotations.OnebootMapper;
import com.github.alphafoxz.oneboot_processor.annotations.OnebootMapping;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.tools.Diagnostic;
import java.util.Set;

@SupportedAnnotationTypes(Const.ANNOTATIONS_PACKAGE + ".OnebootMapper") // 指定处理器应处理的注解类型
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
        for (Element element : roundEnv.getElementsAnnotatedWith(OnebootMapper.class)) {
            if (!ElementKind.INTERFACE.equals(element.getKind()) && !ElementKind.CLASS.equals(element.getKind())) {
                messager.printMessage(Diagnostic.Kind.ERROR, "Only interfaces or classes can be annotated with @OnebootMapper.");
                return true;
            }
            String packageName = processingEnv.getElementUtils().getPackageOf(element).getQualifiedName().toString();
            String className = element.getSimpleName() + "Impl";

            TypeSpec.Builder clazzBuilder = TypeSpec.classBuilder(className)
                    .addModifiers(Modifier.PUBLIC);
            for (Element member : processingEnv.getElementUtils().getAllMembers((TypeElement) element)) {
                if (!ElementKind.FIELD.equals(member.getKind())) {
                    continue;
                }
                VariableElement fieldElement = (VariableElement) member;
                OnebootMapping anno = fieldElement.getAnnotation(OnebootMapping.class);
                if (anno == null) {
                    continue;
                }
                for (Class<?> target : Set.of(anno.targets())) {
                    MethodSpec method = MethodSpec.methodBuilder(fieldElement.getSimpleName().toString())
                            .addModifiers(Modifier.PUBLIC)
                            .returns(target)
                            .addStatement("return null") // Replace with actual getter logic
                            .build();
                    clazzBuilder.addMethod(method);
                }
            }
            JavaFile file = JavaFile.builder(packageName, clazzBuilder.build())
                    .build();
            try {
                file.writeTo(processingEnv.getFiler());
            } catch (Exception e) {
                messager.printMessage(Diagnostic.Kind.ERROR, "Failed to generate class for " + element.getSimpleName());
            }
        }
        return true;
    }
}
