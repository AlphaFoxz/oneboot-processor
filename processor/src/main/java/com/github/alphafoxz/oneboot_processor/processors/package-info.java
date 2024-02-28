/**
 * impl processor for annotations
 */
package com.github.alphafoxz.oneboot_processor.processors;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ParameterSpec;

import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class Const {
    static final String ANNOTATIONS_PREFIX = "com.github.alphafoxz.oneboot_processor.annotations.";
}

class Func {
    static List<ParameterSpec> toParameterSpecList(List<? extends VariableElement> params) {
        List<ParameterSpec> result = new ArrayList<>();
        for (VariableElement param : params) {
            result.add(ParameterSpec.get(param));
        }
        return result;
    }

    static List<TypeElement> toTypeElementList(List<? extends VariableElement> variableElements, Elements util) {
        List<TypeElement> result = new ArrayList<>();
        for (VariableElement variableElement : variableElements) {
            result.add(util.getTypeElement(variableElement.asType().toString()));
        }
        return result;
    }

    static List<String> toStringList(List<? extends VariableElement> variableElements) {
        List<String> result = new ArrayList<>();
        for (VariableElement variableElement : variableElements) {
            result.add(variableElement.getSimpleName().toString());
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

    static String camelToUnderline(String str) {
        StringBuilder result = new StringBuilder();
        boolean upperCase = false;
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == '_') {
                upperCase = true;
            } else if (upperCase) {
                result.append(Character.toUpperCase(c));
                upperCase = false;
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }
}
