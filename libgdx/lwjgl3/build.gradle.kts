val gdxVersion: String by project // Reads gdxVersion from gradle.properties
val appName: String by project // Reads appName from gradle.properties

plugins {
    id("org.beryx.runtime") version "1.13.0"
    id("application")
}

application {
    mainClass.set("com.abysl.vampiremark.lwjgl3.Lwjgl3Launcher")
}

sourceSets {
    main {
        resources.srcDirs("assets")
    }
}

dependencies {
    implementation("com.badlogicgames.gdx:gdx-backend-lwjgl3:$gdxVersion")
    implementation("com.badlogicgames.gdx:gdx-platform:$gdxVersion:natives-desktop")
    implementation(project(":core"))
}

tasks.named<Jar>("jar") {
    archiveBaseName.set(appName)
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    manifest {
        attributes["Main-Class"] = "com.abysl.vampiremark.lwjgl3.Lwjgl3Launcher"
    }
}

tasks.named<JavaExec>("run") {
    workingDir("$rootDir/assets")
    if (System.getProperty("os.name").lowercase().contains("mac")) {
        jvmArgs?.add("-XstartOnFirstThread")
    }
}


runtime {
    options.set(listOf("--strip-debug", "--compress", "2", "--no-header-files", "--no-man-pages"))
    modules.set(listOf("jdk.unsupported"))
    distDir.set(layout.buildDirectory)

    jpackage {
        val os = System.getProperty("os.name").toLowerCase()
        val type = when {
            os.contains("win") -> "exe"
            os.contains("mac") -> "dmg"
            else -> "deb"
        }
        imageName = appName
        skipInstaller = false
        jpackageHome = System.getenv("JAVA_HOME")
        mainJar = tasks.named<Jar>("jar").get().archiveFile.get().toString()
        installerName = "VampireMarkInstaller"
        installerType = type
        imageOptions = when {
            os.contains("win") -> listOf("--icon", "$projectDir/assets/fmt/monsters/void_d1.ico")
            os.contains("mac") -> listOf("--icon", "$projectDir/assets/fmt/monsters/void_d1.icns")
            else -> listOf("--icon", "$projectDir/assets/fmt/monsters/void_d1.png")
        }
        installerOptions = if (type == "exe") listOf("--win-menu", "--win-shortcut") else emptyList()
    }
}

tasks.register("dist") {
    dependsOn("jar")
}

tasks.jpackageImage {
    doNotTrackState("This task both reads from and writes to the build folder.")
}
