package com.github.alphafoxz.oneboot_processor.processors;

import com.github.alphafoxz.oneboot_processor.toolkit.PrintUtil;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Set;

public abstract class AbstractProcessor<T extends Annotation> extends javax.annotation.processing.AbstractProcessor {
    private Messager messager;

    protected Messager getMessager() {
        return messager;
    }

    protected void printError(String msg) {
        printError(msg, null);
    }

    protected void printError(String msg, Throwable e) {
        PrintUtil.printError(messager, msg, e);
    }

    protected void printNote(String msg) {
        PrintUtil.printNote(messager, msg);
    }

    protected void printWarning(String msg) {
        PrintUtil.printWarning(messager, msg);
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.messager = processingEnv.getMessager();
    }

    /**
     * @return 返回true代表当前注解已经处理完成，false代表当前处理器没有认领这些注解，其他处理器可以继续对这些注解进行处理
     */
    abstract public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment);

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Set.of(Const.ANNOTATIONS_PREFIX + getSimpleName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_17;
    }

    private String getSimpleName() {
        // 获取子类的类型
        Type genericSuperclass = getClass().getGenericSuperclass();
        // 确保这个类型是参数化的
        if (genericSuperclass instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            if (actualTypeArguments.length > 0) {
                // 获取第一个泛型参数的类型
                Type actualType = actualTypeArguments[0];
                // 尝试转换为Class对象以获取简单名称
                if (actualType instanceof Class<?>) {
                    return ((Class<?>) actualType).getSimpleName();
                }
            }
        }
        return "Unknown";
    }
}
