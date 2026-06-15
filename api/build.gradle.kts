plugins {
    id("java")
}

group = "me.bananplayss"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    compileOnly("io.papermc.paper:paper-api:1.18.2-R0.1-SNAPSHOT")
//    compileOnly("net.kyori:adventure-text-minimessage:5.1.1")
}

tasks.test {
    useJUnitPlatform()
}