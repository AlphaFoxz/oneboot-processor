/**
 * impl processor for annotations
 */
package com.github.alphafoxz.oneboot_processor.processors;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ParameterSpec;

import javax.lang.model.element.VariableElement;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
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

    static <T extends Annotation> AnnotationSpec fromAnnotation(T anno) {
        return AnnotationSpec.get(anno);
    }

    @SuppressWarnings("unchecked")
    public static <A extends Annotation> A createAnnotation(Class<A> annotationType, Map<String, Object> values) {
        return (A) Proxy.newProxyInstance(annotationType.getClassLoader(), new Class[]{annotationType},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        String methodName = method.getName();
                        if (values.containsKey(methodName)) {
                            return values.get(methodName);
                        }
                        // 调用默认方法
                        return method.getDefaultValue();
                    }
                });
    }
}
