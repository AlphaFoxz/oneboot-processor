tasks.bootJar {
    enabled = false
}
dependencies {
    implementation("com.squareup:javapoet")
    compileOnly("com.google.auto.service:auto-service")
    annotationProcessor("com.google.auto.service:auto-service")
    api("org.mapstruct:mapstruct")
    api("org.mapstruct:mapstruct-processor")
    annotationProcessor("org.mapstruct:mapstruct-processor")
}
