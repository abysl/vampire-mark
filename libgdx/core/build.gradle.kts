val appName = project.property("appName") as String
val gdxVersion = project.property("gdxVersion") as String
val ktxVersion = project.property("ktxVersion") as String
val kotlinVersion = project.property("kotlinVersion") as String

plugins {
    id("java-library")
    kotlin("jvm")
    kotlin("plugin.serialization")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    api("com.badlogicgames.gdx:gdx:$gdxVersion")
    api(ktx("app"))
    api(ktx("async"))
    api(ktx("assets"))
    api(ktx("graphics"))
    api(ktx("math"))
    api("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")
    api("net.onedaybeard.artemis:artemis-odb:2.3.0")
    api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
    testImplementation("org.jetbrains.kotlin:kotlin-test:$kotlinVersion")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")
}

tasks {
    compileJava {
        options.encoding = "UTF-8"
    }
    compileTestJava {
        options.encoding = "UTF-8"
    }
}

eclipse {
    project {
        name = "$appName-core"
    }
}

fun ktx(module: String): String {
    return "io.github.libktx:ktx-$module:$ktxVersion"
}
