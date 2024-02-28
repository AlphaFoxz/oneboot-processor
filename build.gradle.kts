var rootGroupId = "com.github.AlphaFoxz.oneboot-processor"
var rootVersion = "0.0.1-alpha.0"
plugins {
    id("java-library")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("maven-publish")
}
tasks.bootJar {
    enabled = false
}
tasks.jar {
    enabled = false
}
subprojects {
    apply(plugin = "java-library")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "maven-publish")
    group = rootGroupId
    version = rootVersion
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
        testCompileOnly("org.projectlombok:lombok")
        annotationProcessor("org.projectlombok:lombok")
        testAnnotationProcessor("org.projectlombok:lombok")
        implementation("com.github.AlphaFoxz:oneboot-core:dev-SNAPSHOT") {
            isChanging = true
        }
    }
}
project(":processor") {
    publishing {
        publications {
            create<MavenPublication>("maven") {
                groupId = rootGroupId
                artifactId = "processor"
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
}
project(":starter") {
    publishing {
        publications {
            create<MavenPublication>("maven") {
                groupId = rootGroupId
                artifactId = "starter"
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
}
