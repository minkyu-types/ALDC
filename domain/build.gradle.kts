plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.ksp)
}
kotlin {
    jvm()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        val commonMain by getting {
            kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
            dependencies {
                api(libs.kermit)
                api(libs.koin.annotations)
                api(libs.kotlinx.coroutines.core)
                api(libs.kotlinx.datetime)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(libs.kotlinx.coroutines.test)
            }
        }
    }
}

dependencies {
    add("kspCommonMainMetadata", libs.koin.ksp.compiler)
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    if (name != "kspCommonMainKotlinMetadata") {
        dependsOn("kspCommonMainKotlinMetadata")
    }
}