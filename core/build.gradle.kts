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

    implementation(project(":api"))
    implementation("com.github.cryptomorin:XSeries:13.7.0") { isTransitive = false }

    compileOnly("io.papermc.paper:paper-api:1.18.2-R0.1-SNAPSHOT")
//    compileOnly("net.kyori:adventure-text-minimessage:5.1.1")
    compileOnly("me.clip:placeholderapi:2.12.2")
    compileOnly("com.zaxxer:HikariCP:7.1.0")

    compileOnly("com.infernalsuite.asp:api:4.0.0-SNAPSHOT")

    implementation(platform("com.intellectualsites.bom:bom-newest:1.56")) // Ref: https://github.com/IntellectualSites/bom
    compileOnly("com.fastasyncworldedit:FastAsyncWorldEdit-Core")
    compileOnly("com.fastasyncworldedit:FastAsyncWorldEdit-Bukkit") { isTransitive = false }
}

tasks.test {
    useJUnitPlatform()
}