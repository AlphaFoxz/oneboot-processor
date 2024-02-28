tasks.bootJar {
    enabled = false
}
dependencies {
    compileOnly("com.google.code.findbugs:annotations")
    compileOnly("org.springframework.boot:spring-boot-starter")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    api(project(":processor"))
//    annotationProcessor(":processor")
}
