import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
  repositories {
    mavenCentral()
    maven(url = "https://s01.oss.sonatype.org")
    mavenLocal()
    google()
    gradlePluginPortal()
    maven(url = "https://oss.sonatype.org/content/repositories/snapshots/")
    maven(url = "https://s01.oss.sonatype.org/content/repositories/snapshots/")
  }
  dependencies {
      val kotlinVersion: String by project
      classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${kotlinVersion}")
      classpath("org.jetbrains.kotlin:kotlin-serialization:${kotlinVersion}")
      classpath("com.squareup:kotlinpoet:1.14.2")
  }
}

allprojects {
  apply(plugin = "eclipse")
  apply(plugin = "idea")

  repositories {
    mavenCentral()
    maven(url = "https://s01.oss.sonatype.org")
    mavenLocal()
    maven(url = "https://oss.sonatype.org/content/repositories/snapshots/")
    maven(url = "https://s01.oss.sonatype.org/content/repositories/snapshots/")
    maven(url = "https://jitpack.io")
  }
}

subprojects {
  apply(plugin = "java-library")
  apply(plugin = "kotlin")

  version = "1.0.0"
  extra["appName"] = "vampiremark"

  tasks.withType<KotlinCompile> {
    kotlinOptions {
      jvmTarget = "17"
      freeCompilerArgs = listOf("-Xjvm-default=all")
    }
  }
}

