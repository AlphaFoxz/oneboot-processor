package com.github.alphafoxz.oneboot_processor.spi;

import org.mapstruct.ap.spi.DefaultAccessorNamingStrategy;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeKind;

public class OnebootAccessorNamingStrategy extends DefaultAccessorNamingStrategy {
    @Override
    public boolean isGetterMethod(ExecutableElement getterMethod) {
        if (!getterMethod.getParameters().isEmpty()
                || !getterMethod.getModifiers().contains(Modifier.PUBLIC)
                || getterMethod.getModifiers().contains(Modifier.STATIC)
        ) {
            return false;
        }
        String methodName = getterMethod.getSimpleName().toString();
        if (methodName.startsWith("is")
                && methodName.length() > 2
                && getterMethod.getReturnType().getKind() == TypeKind.BOOLEAN
        ) {
            return true;
        }
        if (methodName.startsWith("get")
                && methodName.length() > 3
                && getterMethod.getReturnType().getKind() != TypeKind.VOID
        ) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isSetterMethod(ExecutableElement setterMethod) {
        String methodName = setterMethod.getSimpleName().toString();
        return methodName.startsWith("set")
                && methodName.length() > 3
                && setterMethod.getModifiers().contains(Modifier.PUBLIC)
                && !setterMethod.getModifiers().contains(Modifier.STATIC)
                && setterMethod.getParameters().size() == 1;
    }
}
