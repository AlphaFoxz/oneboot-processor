package com.github.alphafoxz.oneboot_processor.visitor;

import com.github.alphafoxz.oneboot.core.toolkit.coding.StrUtil;

import javax.lang.model.element.*;
import javax.lang.model.type.TypeKind;
import java.util.ArrayList;
import java.util.List;

class Func {
    static List<ExecutableElement> setterMethods(TypeElement typeElement) {
        if (typeElement == null) {
            return null;
        }
        List<ExecutableElement> result = new ArrayList<>();
        for (Element innerElement : typeElement.getEnclosedElements()) {
            if (innerElement.getKind() != ElementKind.METHOD) {
                continue;
            }
            ExecutableElement setterMethod = (ExecutableElement) innerElement;
            if (!setterMethod.toString().startsWith("set")
                    || setterMethod.toString().length() < 4
                    || !setterMethod.getModifiers().contains(Modifier.PUBLIC)
                    || setterMethod.getParameters().size() != 1
            ) {
                continue;
            }
            result.add(setterMethod);
        }
        return result;
    }

    static List<ExecutableElement> getterMethods(Element typeElement) {
        if (typeElement == null) {
            return null;
        }
        List<ExecutableElement> result = new ArrayList<>();
        for (Element innerElement : typeElement.getEnclosedElements()) {
            if (innerElement.getKind() != ElementKind.METHOD) {
                continue;
            }
            ExecutableElement getterMethod = (ExecutableElement) innerElement;
            if (!getterMethod.toString().startsWith("get")
                    || getterMethod.toString().length() < 4
                    || !getterMethod.getModifiers().contains(Modifier.PUBLIC)
                    || !getterMethod.getParameters().isEmpty()
                    || getterMethod.getReturnType().getKind() == TypeKind.VOID
            ) {
                continue;
            }
            result.add(getterMethod);
        }
        return result;
    }

    static boolean isSameFieldName(ExecutableElement element1, ExecutableElement element2) {
        String fieldName1 = fieldNameFromGetterOrSetter(element1);
        String fieldName2 = fieldNameFromGetterOrSetter(element2);
        if (fieldName1 == null || fieldName2 == null) {
            return false;
        }
        return fieldName1.equals(fieldName2);
    }

    static String fieldNameFromGetterOrSetter(ExecutableElement element) {
        String fieldName = getFieldNameFromGetter(element);
        if (fieldName == null) {
            fieldName = getFieldNameFromSetter(element);
        }
        return fieldName;
    }

    @Deprecated
    static String getFieldNameFromGetter(ExecutableElement executableElement) {
        return getFieldNameFromGetter(executableElement, false);
    }

    static String getFieldNameFromGetter(ExecutableElement executableElement, boolean isRecord) {
        if (!executableElement.getParameters().isEmpty()
                || executableElement.getReturnType().getKind() == TypeKind.VOID
                || !executableElement.getModifiers().contains(Modifier.PUBLIC)
                || executableElement.getModifiers().contains(Modifier.STATIC)) {
            return null;
        }
        String funName = executableElement.getSimpleName().toString();
        if (isRecord
                && !"hashCode".equals(funName)
                && !"toString".equals(funName)
//                && !"equals".equals(funName)
                && !"getClass".equals(funName)
                && !"notify".equals(funName)
                && !"notifyAll".equals(funName)
                && !"wait".equals(funName)
        ) {
            return null;
        }
        String getterName = executableElement.getSimpleName().toString();
        if (getterName.startsWith("get") && getterName.length() > 3) {
            return StrUtil.removePreAndLowerFirst(getterName, "get");
        } else if (getterName.startsWith("is")
                && getterName.length() > 2
                && executableElement.getReturnType().getKind() == TypeKind.BOOLEAN
        ) {
            return StrUtil.removePreAndLowerFirst(getterName, "is");
        }
        return null;
    }

    static String getFieldNameFromSetter(ExecutableElement executableElement) {
        if (executableElement.getParameters().size() != 1
                || !executableElement.getModifiers().contains(Modifier.PUBLIC)
                || executableElement.getModifiers().contains(Modifier.STATIC)
        ) {
            return null;
        }
        String funName = executableElement.getSimpleName().toString();
        if (funName.startsWith("set") && funName.length() > 3) {
            return StrUtil.removePreAndLowerFirst(funName, "set");
        }
        return null;
    }
}