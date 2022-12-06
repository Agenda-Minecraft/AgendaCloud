import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm") version "1.7.10"
    id("com.github.johnrengelman.shadow") version "7.0.0"
    `maven-publish`
}

val artifactId = "AgendaCloud"
group = "cat.kiwi.minecraft"
version = "3.0.1-SNAPSHOT"

repositories {
    mavenCentral()
    maven(url="https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven(url="https://oss.sonatype.org/content/repositories/snapshots")
    maven(url="https://repo.extendedclip.com/content/repositories/placeholderapi")
    maven(url="https://repo.codemc.org/repository/maven-public/")
    maven(url="https://jitpack.io")
}

dependencies {
    // kotlin libs
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    // service report libs
    implementation("com.orbitz.consul:consul-client:1.5.3")

    // minecraft apis
    implementation("org.spigotmc:spigot-api:1.19.2-R0.1-SNAPSHOT")

    // other
    implementation("com.google.code.gson:gson:2.10")

}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks {
    named<ShadowJar>("shadowJar") {
        archiveBaseName.set("AgendaCloud")
        dependencies {
            exclude(dependency("org.spigotmc:spigot-api:1.19.2-R0.1-SNAPSHOT"))
        }
    }
}
artifacts {
    archives(tasks.named("shadowJar"))
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = groupId
            artifactId = artifactId
            version = version

            from(components["java"])
        }
    }
}