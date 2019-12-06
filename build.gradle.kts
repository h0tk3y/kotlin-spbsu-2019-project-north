import com.bmuschko.gradle.docker.tasks.container.DockerCreateContainer
import com.bmuschko.gradle.docker.tasks.container.DockerStartContainer
import com.bmuschko.gradle.docker.tasks.container.DockerStopContainer
import com.bmuschko.gradle.docker.tasks.image.DockerBuildImage

buildscript {
    repositories {
        jcenter()
        mavenCentral()
        maven(url = "https://kotlin.bintray.com/ktor")
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.50")
    }
}

plugins {
    id("com.bmuschko.docker-remote-api") version "6.0.0"
    kotlin("jvm") version "1.3.50"
    application
    java
}

group = "ru.compscicenter.bsse.18b09.bezbykova"
version = "1.0-SNAPSHOT"

application {
    mainClassName = "io.ktor.server.netty.EngineMain"
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}
repositories {
    jcenter()
}

val koin_version = "2.0.1"

dependencies {
    implementation("org.koin:koin-core:$koin_version")
    testImplementation("org.koin:koin-test:$koin_version")
}

repositories {
    mavenLocal()
    jcenter()
    maven(url = "https://kotlin.bintray.com/ktor")
}

val ktor_version = "1.2.4"

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testCompile("junit:junit:4.12")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.5.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.5.2")
    compile(kotlin("stdlib"))

    // Koin
    compile("org.koin:koin-ktor:$koin_version")
    compile("org.koin:koin-logger-slf4j:$koin_version")
    testCompile("org.koin:koin-test:$koin_version")

    // Ktor
    compile("io.ktor:ktor-server-netty:$ktor_version")
    compile("io.ktor:ktor-server-core:$ktor_version")
    compile("io.ktor:ktor-auth:$ktor_version")
    compile("io.ktor:ktor-auth-jwt:$ktor_version")
    compile("io.ktor:ktor-jackson:$ktor_version")
//    compile('org.slf4j:slf4j-nop:1.7.25'
    testCompile("io.ktor:ktor-server-tests:$ktor_version")
    testCompile("io.ktor:ktor-server-test-host:$ktor_version")

    // Exposed
    compile("org.jetbrains.exposed:exposed-core:0.18.1")
    compile("org.jetbrains.exposed:exposed-dao:0.18.1")
    compile("org.jetbrains.exposed:exposed-jdbc:0.18.1")
    compile("org.jetbrains.exposed:exposed-jodatime:0.18.1")

    // Database
    compile("org.postgresql:postgresql:42.2.2")
}

tasks.test {
    useJUnitPlatform()
}

tasks.compileKotlin {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.compileTestKotlin {
    kotlinOptions.jvmTarget = "1.8"
}

val buildMyAppImage by tasks.creating(DockerBuildImage::class) {
    inputDir.set(file("docker/test"))
    images.add("test/myapp:latest")
}

val createMyAppContainer by tasks.creating(DockerCreateContainer::class) {
    dependsOn(buildMyAppImage)
    targetImageId(buildMyAppImage.imageId)
    hostConfig.portBindings.set(listOf("8080:8080"))
    hostConfig.autoRemove.set(true)
}

val startMyAppContainer by tasks.creating(DockerStartContainer::class) {
    dependsOn(createMyAppContainer)
    targetContainerId(createMyAppContainer.containerId)
}

val stopMyAppContainer by tasks.creating(DockerStopContainer::class) {
    targetContainerId(createMyAppContainer.containerId)
}

tasks.create("functionalTestMyApp", Test::class) {
    dependsOn(startMyAppContainer)
    finalizedBy(stopMyAppContainer)
}