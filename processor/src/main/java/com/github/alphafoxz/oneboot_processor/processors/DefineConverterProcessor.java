package com.github.alphafoxz.oneboot_processor.processors;

import com.github.alphafoxz.oneboot.core.toolkit.tuple.Tuples;
import com.github.alphafoxz.oneboot_processor.annotations.DefineConverter;
import com.github.alphafoxz.oneboot_processor.visitor.BeanMergeVisitor;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.*;

import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.*;
import javax.lang.model.util.Elements;
import java.util.List;
import java.util.Set;

@AutoService(Processor.class)
public class DefineConverterProcessor extends AbstractProcessor<DefineConverter> {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Elements elementUtils = processingEnv.getElementUtils();
        for (Element element : roundEnv.getElementsAnnotatedWith(DefineConverter.class)) { //遍历每个类
            try {
                if (ElementKind.INTERFACE != element.getKind()) {
                    printError("ERROR: Only interfaces can be annotated with @DefineConverter. " + element.getSimpleName());
                    continue;
                }
                String packageName = elementUtils.getPackageOf(element).getQualifiedName().toString();
                String targetClassName = element.getSimpleName() + "Impl";
                TypeSpec.Builder classBuilder = TypeSpec.classBuilder(targetClassName)
                        .addSuperinterface(element.asType())
                        .addModifiers(Modifier.PUBLIC);
                for (Element enclosedElement : element.getEnclosedElements()) { //遍历每个方法/成员变量
                    if (enclosedElement.getKind() != ElementKind.METHOD) {
                        continue;
                    }
                    ExecutableElement executableElement = (ExecutableElement) enclosedElement;
                    if (executableElement.isDefault()) {
                        continue;
                    }
                    TypeElement returnTypeEle = elementUtils.getTypeElement(executableElement.getReturnType().toString());
                    List<? extends VariableElement> parameters = executableElement.getParameters();
                    List<ParameterSpec> parameterSpecs = Func.toParameterSpecList(executableElement.getParameters());
                    MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder(enclosedElement.getSimpleName().toString())
                            .addModifiers(Modifier.PUBLIC)
                            .addParameters(parameterSpecs)
                            .returns(TypeName.get(returnTypeEle.asType()));
                    CodeBlock.Builder codeBlock = CodeBlock.builder();
                    codeBlock.add(returnTypeEle.accept(new BeanMergeVisitor(), Tuples.of(processingEnv, parameters)));
                    methodBuilder.addCode(codeBlock.build());
                    classBuilder.addMethod(methodBuilder.build());
                }
                JavaFile file = JavaFile.builder(packageName, classBuilder.build())
                        .build();
                file.writeTo(processingEnv.getFiler());
            } catch (Exception e) {
                printError("ERROR: Failed to process for type: " + element.getSimpleName(), e);
            }
        }
        return true;
    }
}
