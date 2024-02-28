module com.github.alphafoxz.oneboot_processor {
    requires com.github.alphafoxz.oneboot_core;
    requires com.google.auto.service;
    requires com.squareup.javapoet;
    requires java.compiler;
    requires org.mapstruct;
    requires org.mapstruct.processor;

    exports com.github.alphafoxz.oneboot_processor.annotations;
}
