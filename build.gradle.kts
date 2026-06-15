plugins {
    java
    id("io.freefair.lombok") version "9.5.0" apply false
}

group = "me.bananplayss"
version = "1.0.0"

repositories {
    mavenCentral()
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "io.freefair.lombok")

    repositories {
        maven {
            url = uri("https://repo.papermc.io/repository/maven-public/")
        }
        maven {
            url = uri("https://repo.extendedclip.com/releases/")
        }
        mavenCentral()
    }

    java {
        toolchain.languageVersion.set(JavaLanguageVersion.of(21))
    }

    dependencies {
        testImplementation(platform("org.junit:junit-bom:5.10.0"))
        testImplementation("org.junit.jupiter:junit-jupiter")
        testRuntimeOnly("org.junit.platform:junit-platform-launcher")

        implementation("net.kyori:adventure-text-minimessage:5.1.1")
    }

    tasks.test {
        useJUnitPlatform()
    }
}