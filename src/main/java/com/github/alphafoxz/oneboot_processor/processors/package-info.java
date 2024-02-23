/**
 * impl processor for annotations
 */
package com.github.alphafoxz.oneboot_processor.processors;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.ParameterSpec;

import javax.lang.model.element.VariableElement;
import java.lang.annotation.Annotation;
import java.util.*;

class Const {
    static final String ANNOTATIONS_PREFIX = "com.github.alphafoxz.oneboot_processor.annotations.";
}

class Func {
    static Set<String> getSupportAnnotations(String... classNames) {
        Set<String> result = new HashSet<>();
        for (String className : classNames) {
            result.add(Const.ANNOTATIONS_PREFIX + className);
        }
        return result;
    }

    static List<ParameterSpec> fromParamElements(List<? extends VariableElement> params) {
        List<ParameterSpec> result = new ArrayList<>();
        for (VariableElement param : params) {
            result.add(ParameterSpec.get(param));
        }
        return result;
    }

    static <T extends Annotation> AnnotationSpec createAnnotationSpec(Class<T> clazz, Map<String, String> members) {
        AnnotationSpec.Builder builder = AnnotationSpec.builder(clazz);
        for (Map.Entry<String, String> entry : members.entrySet()) {
            builder.addMember(entry.getKey(), entry.getValue());
        }
        return builder.build();
    }

    static CodeBlock createCodeBlock(String code) {
        return CodeBlock.builder().addStatement(code).build();
    }
}
