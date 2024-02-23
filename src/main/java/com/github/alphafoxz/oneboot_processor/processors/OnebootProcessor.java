package com.github.alphafoxz.oneboot_processor.processors;

import com.github.alphafoxz.oneboot_processor.annotations.OnebootMapper;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.tools.Diagnostic;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.annotation.Annotation;
import java.util.Set;

public abstract class OnebootProcessor<T extends Annotation> extends AbstractProcessor {
    private Messager messager;

    protected void printError(String msg) {
        printError(msg, null);
    }

    protected void printError(String msg, Throwable e) {
        if (e != null) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            msg += "\n" + sw;
        }
        messager.printMessage(Diagnostic.Kind.ERROR, msg);
    }

    protected void printNote(String msg) {
        messager.printMessage(Diagnostic.Kind.NOTE, msg);
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        this.messager = processingEnv.getMessager();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Func.getSupportAnnotations(OnebootMapper.class.getSimpleName());
    }
}
