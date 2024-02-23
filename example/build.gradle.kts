plugins {
    id("java-library")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
repositories {
    mavenCentral()
    maven {
        url = uri("https://jitpack.io")
    }
}
tasks.bootJar {
    enabled = false
}
tasks.jar {
    enabled = true
    archiveClassifier = ""
}
configurations.all {
    resolutionStrategy.cacheChangingModulesFor(1, TimeUnit.SECONDS)
}
dependencyManagement {
    imports {
        mavenBom(project.property("parentProject") as String)
    }
}
dependencies {
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    compileOnly(project(":processor"))
    annotationProcessor(project(":processor"))
    compileOnly("org.mapstruct:mapstruct")
    annotationProcessor("org.mapstruct:mapstruct-processor")
    compileOnly("org.springframework.boot:spring-boot-starter")
}
