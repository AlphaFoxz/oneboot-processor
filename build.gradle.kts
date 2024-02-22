var rootGroupId = "com.github.AlphaFoxz"
var rootArtifactId = "oneboot-processor"
var rootVersion = "0.0.1-alpha.0"
plugins {
    id("java")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("maven-publish")
}
java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
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
    implementation("com.squareup:javapoet")
    compileOnly("com.google.auto.service:auto-service")
    annotationProcessor("com.google.auto.service:auto-service")
    compileOnly("org.mapstruct:mapstruct")
    annotationProcessor("org.mapstruct:mapstruct-processor")
}
publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = rootGroupId
            artifactId = rootArtifactId
            version = rootVersion
            from(components["java"])

            versionMapping {
                allVariants {
                    fromResolutionResult()
                }
            }
        }
    }
}