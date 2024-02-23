var rootGroupId = "com.github.AlphaFoxz"
var rootArtifactId = "oneboot-processor"
var rootVersion = "0.0.1-alpha.0"
plugins {
    id("java-library")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("maven-publish")
}
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
group = rootGroupId
version = rootVersion
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
    enabled = false
}
