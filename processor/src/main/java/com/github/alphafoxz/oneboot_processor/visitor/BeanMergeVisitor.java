package com.github.alphafoxz.oneboot_processor.visitor;

import com.github.alphafoxz.oneboot.core.toolkit.tuple.Tuple2;
import com.github.alphafoxz.oneboot_processor.toolkit.PrintUtil;
import com.squareup.javapoet.CodeBlock;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeanMergeVisitor implements ElementVisitor<CodeBlock, Tuple2<ProcessingEnvironment, List<? extends VariableElement>>> {
    @Override
    public CodeBlock visit(Element element, Tuple2<ProcessingEnvironment, List<? extends VariableElement>> objects) {
        return null;
    }

    @Override
    public CodeBlock visitPackage(PackageElement packageElement, Tuple2<ProcessingEnvironment, List<? extends VariableElement>> objects) {
        return null;
    }

    @Override
    public CodeBlock visitType(TypeElement typeElement, Tuple2<ProcessingEnvironment, List<? extends VariableElement>> param) {
        Messager messager = param.getIndex0().getMessager();
        ProcessingEnvironment processEnv = param.getIndex0();
        List<? extends VariableElement> paramList = param.getIndex1();
        CodeBlock.Builder codeBlock = CodeBlock.builder();
        if (paramList == null) {
            return codeBlock.addStatement("return null").build();
        }
        Map<String, Boolean> varMap = new HashMap<>();
        codeBlock.addStatement("$T __result = new $T()", typeElement, typeElement);
        for (ExecutableElement setterElement : Func.setterMethods(typeElement)) {
            boolean hasSet = false;
            for (int i = paramList.size() - 1; i >= 0; --i) {
                VariableElement paramVarEle = paramList.get(i);
                TypeElement paramTypeEle = processEnv.getElementUtils().getTypeElement(paramVarEle.asType().toString());
                for (ExecutableElement getterElement : Func.getterMethods(paramTypeEle)) {
                    if (Func.isSameFieldName(getterElement, setterElement)) {
                        if (processEnv.getTypeUtils().isSameType(getterElement.getReturnType(), setterElement.getParameters().get(0).asType())) {
                            codeBlock.addStatement("__result.$L($L.$L())",
                                    setterElement.getSimpleName().toString(),
                                    paramVarEle.getSimpleName().toString(),
                                    getterElement.getSimpleName().toString()
                            );
                            hasSet = true;
                            break;
                        }
                    }
                }
            }
            if (!hasSet) {
                PrintUtil.printWarning(messager, "target field not found: " + setterElement.toString());
            }
        }
        codeBlock.addStatement("return __result");
        return codeBlock.build();
    }

    @Override
    public CodeBlock visitVariable(VariableElement variableElement, Tuple2<ProcessingEnvironment, List<? extends VariableElement>> objects) {
        return null;
    }

    @Override
    public CodeBlock visitExecutable(ExecutableElement executableElement, Tuple2<ProcessingEnvironment, List<? extends VariableElement>> objects) {
        return null;
    }

    @Override
    public CodeBlock visitTypeParameter(TypeParameterElement typeParameterElement, Tuple2<ProcessingEnvironment, List<? extends VariableElement>> objects) {
        return null;
    }

    @Override
    public CodeBlock visitUnknown(Element element, Tuple2<ProcessingEnvironment, List<? extends VariableElement>> objects) {
        return null;
    }
}
