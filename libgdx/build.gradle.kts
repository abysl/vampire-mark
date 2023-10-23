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
    classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
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
      freeCompilerArgs = listOf("-Xjvm-default=compatibility")
    }
  }
}

tasks.withType<org.gradle.plugins.ide.eclipse.model.EclipseProject> {
  name = "vampiremark-parent"
}
