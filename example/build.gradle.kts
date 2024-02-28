tasks.withType<Test> {
    useJUnitPlatform()
    onlyIf {
        //在执行build任务时跳过test
        !gradle.taskGraph.hasTask(":build")
    }
}
dependencies {
    implementation("com.github.AlphaFoxz:oneboot-core:dev-SNAPSHOT") {
        isChanging = true
    }
    implementation("org.springframework.boot:spring-boot-starter-web")

//    compileOnly(project(":processor"))
//    annotationProcessor(project(":processor"))
//    testCompileOnly(project(":processor"))
//    testAnnotationProcessor(project(":processor"))

    compileOnly(project(":starter"))
    annotationProcessor(project(":starter"))
    testCompileOnly(project(":starter"))
    testAnnotationProcessor(project(":starter"))

//    compileOnly("io.github.linpeilie:mapstruct-plus-processor:1.3.6")
//    annotationProcessor("io.github.linpeilie:mapstruct-plus-processor:1.3.6")
//    testImplementation("io.github.linpeilie:mapstruct-plus-processor:1.3.6")
//    testAnnotationProcessor("io.github.linpeilie:mapstruct-plus-processor:1.3.6")

//    compileOnly("org.mapstruct:mapstruct")
//    testCompileOnly("org.mapstruct:mapstruct")
//    annotationProcessor("org.mapstruct:mapstruct-processor")
//    testAnnotationProcessor("org.mapstruct:mapstruct-processor")

    compileOnly("io.github.linpeilie:mapstruct-plus-spring-boot-starter:1.3.6")
    annotationProcessor("io.github.linpeilie:mapstruct-plus-spring-boot-starter:1.3.6")
    testImplementation("io.github.linpeilie:mapstruct-plus-spring-boot-starter:1.3.6")
    testAnnotationProcessor("io.github.linpeilie:mapstruct-plus-spring-boot-starter:1.3.6")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}
