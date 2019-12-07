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

repositories {
    mavenLocal()
    jcenter()
    maven(url = "https://kotlin.bintray.com/ktor")
    maven(url = "https://dl.bintray.com/palantir/releases")
}

val ktor_version = "1.2.4"
val junitJupiterVersion = "5.5.2"

dependencies {
    implementation("org.koin:koin-core:$koin_version")
    testImplementation("org.koin:koin-test:$koin_version")

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testCompile("junit:junit:4.12")
    testCompile("org.junit.jupiter:junit-jupiter-params:$junitJupiterVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitJupiterVersion")
    testImplementation("org.testcontainers:junit-jupiter:1.12.3")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitJupiterVersion")
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
    testCompile("org.testcontainers:postgresql:1.12.3")

    // Docker
    testCompile("com.palantir.docker.compose:docker-compose-junit-jupiter:1.3.0")
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
