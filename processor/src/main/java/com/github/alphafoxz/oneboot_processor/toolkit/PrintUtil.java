package com.github.alphafoxz.oneboot_processor.toolkit;

import javax.annotation.processing.Messager;
import javax.tools.Diagnostic;
import java.io.PrintWriter;
import java.io.StringWriter;

public enum PrintUtil {
    ;

    public static void printError(Messager messager, String msg, Throwable e) {
        if (e != null) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            msg += "\n" + sw;
        }
        messager.printMessage(Diagnostic.Kind.ERROR, msg);
    }

    public static void printNote(Messager messager, String msg) {
        messager.printMessage(Diagnostic.Kind.NOTE, msg);
    }

    public static void printWarning(Messager messager, String msg) {
        messager.printMessage(Diagnostic.Kind.WARNING, msg);
    }
}
