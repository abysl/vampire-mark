import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import kotlin.reflect.KClass

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
    api(ktx("assets-async"))
    api(ktx("graphics"))
    api(ktx("math"))
    api(ktx("log"))
    api("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")
    api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
    implementation("net.onedaybeard.artemis:artemis-odb:2.3.0")
    implementation("de.articdive:jnoise:3.0.2")
    testImplementation("org.jetbrains.kotlin:kotlin-test:$kotlinVersion")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")
}

sourceSets {
    main {
        kotlin.srcDir("build/generated/kotlin")
    }
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

tasks.named("compileKotlin") {
    dependsOn("generateComponents")
    dependsOn("generateTileProperties")
}

tasks.register("generateComponents") {
    doLast {
        val componentNameToClassMap = mutableMapOf<String, ClassName>()

        file("src/main/kotlin/com/abysl/vampiremark/ecs/components").listFiles { _, name -> name.endsWith("Component.kt") }
            ?.forEach { file ->
                val className = file.nameWithoutExtension
                componentNameToClassMap[className.replace("Component", "")] =
                    ClassName("com.abysl.vampiremark.ecs.components", className)
            }

        val mapEntries = componentNameToClassMap.entries.joinToString(",\n") { """"${it.key}" to ${it.value}::class""" }

        val componentsObject = TypeSpec.objectBuilder("Components")
            .addProperty(
                PropertySpec.builder("componentMapping", Map::class.asClassName().parameterizedBy(String::class.asClassName(), KClass::class.asClassName().parameterizedBy(STAR)))
                    .initializer("mapOf(\n$mapEntries\n)")
                    .build()
            )
            .addFunction(
                FunSpec.builder("fromString")
                    .addParameter("name", String::class)
                    .returns(KClass::class.asClassName().parameterizedBy(STAR))
                    .addStatement("val component = componentMapping[name]")
                    .addStatement("return component ?: error(\"No component found for name: \$name\")")
                    .build()
            )
            .build()

        val fileSpec = FileSpec.builder("com.abysl.vampiremark.ecs", "Components")
            .addType(componentsObject)
            .build()

        fileSpec.writeTo(file("build/generated/kotlin"))
    }
}

tasks.register("generateTileProperties") {
    doLast {
        val tilePropertiesMap = mutableMapOf<String, ClassName>()

        // Step 1: Scan the directory for tile property files
        file("src/main/kotlin/com/abysl/vampiremark/world/tiles").listFiles { _, name -> name.endsWith("TileProperty.kt") }
            ?.forEach { file ->
                file.readText().split("sealed class TileProperty").getOrNull(1)?.split("}")?.getOrNull(0)
                    ?.let { content ->
                        content.split("\n").forEach { line ->
                            if (line.contains("data class") || line.contains("object")) {
                                val className = line.trim().split(" ")[2].split("(")[0]
                                tilePropertiesMap[className.replace("Property", "")] = ClassName("com.abysl.vampiremark.world.tiles", "TileProperty", className)
                            }
                        }
                    }
            }

        // Step 2: Generate TileProperties object using KotlinPoet
        val typeMap = Map::class.asClassName().parameterizedBy(String::class.asClassName(), KClass::class.asClassName().parameterizedBy(STAR))

        val tilePropertiesCodeBlock = CodeBlock.builder()
            .add("mapOf(\n")
            .apply {
                tilePropertiesMap.entries.forEach {
                    add("\"${it.key}\" to %T::class,\n", it.value)
                }
            }
            .add(")\n")
            .build()

        val tilePropertiesObject = TypeSpec.objectBuilder("TileProperties")
            .addProperty(
                PropertySpec.builder("tilePropertyMapping", typeMap)
                    .initializer(tilePropertiesCodeBlock)
                    .build()
            )
            .addFunction(
                FunSpec.builder("fromString")
                    .addParameter("name", String::class)
                    .returns(KClass::class.asClassName().parameterizedBy(STAR))
                    .addStatement("val key = name.replace(\"Property\", \"\")")
                    .addStatement("return tilePropertyMapping[key] ?: error(\"No tile property found for name: \$name\")")
                    .build()
            )
            .build()

        val fileSpec = FileSpec.builder("com.abysl.vampiremark.world.tiles", "TileProperties")
            .addType(tilePropertiesObject)
            .build()

        // Step 3: Save to the appropriate source directory
        fileSpec.writeTo(file("build/generated/kotlin"))
    }
}
