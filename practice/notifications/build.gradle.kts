plugins {
    java
    checkstyle
    jacoco
    id("org.springframework.boot") version "3.4.2"
    id("io.spring.dependency-management") version "1.1.7"
    id("com.google.protobuf") version "0.9.4"
}

group = "hse"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter")

	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	implementation("org.springframework.boot:spring-boot-starter-aop")

	implementation("com.fasterxml.jackson.core:jackson-databind:2.18.2")
	compileOnly("org.apache.tomcat:annotations-api:6.0.53")

	implementation("org.telegram:telegrambots-spring-boot-starter:6.9.7.1")
	implementation("io.grpc:grpc-stub:1.62.2")
	implementation("io.grpc:grpc-protobuf:1.62.2")
	implementation("net.devh:grpc-client-spring-boot-starter:3.0.0.RELEASE")
	compileOnly("org.apache.tomcat:annotations-api:6.0.53")
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.25.1"
    }
    plugins {
        create("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.62.2"
        }
    }
    generateProtoTasks {
        all().forEach { task ->
            task.plugins {
                create("grpc")
            }
        }
    }
}

sourceSets {
    main {
        java {
            srcDirs(
                "build/generated/source/proto/main/java",
                "build/generated/source/proto/main/grpc"
            )
        }
    }
}

tasks.named("compileJava").configure {
    dependsOn("generateProto")
}