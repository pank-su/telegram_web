plugins {
    kotlin("multiplatform") version "1.9.0"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.0" apply true
    application
}

group = "su.pank"
version = "1.0-SNAPSHOT"


fun kotlinw(target: String): String =
    "org.jetbrains.kotlin-wrappers:kotlin-$target"

val kotlinWrappersVersion = "1.0.0-pre.632"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/kotlinx-html/maven")
}

kotlin {
    jvm {
        jvmToolchain(8)
        withJava()
        testRuns.named("test") {
            executionTask.configure {
                useJUnitPlatform()
            }
        }
    }
    js {
        binaries.executable()
        browser {
            commonWebpackConfig {
                cssSupport {
                    enabled.set(true)
                }
            }
        }
    }



    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val jvmMain by getting {
            dependencies {

                implementation("io.ktor:ktor-server-netty:2.3.2")
                implementation("io.ktor:ktor-server-html-builder-jvm:2.3.2")
                implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.7.2")
                implementation("io.ktor:ktor-network-tls-certificates:2.3.2")

            }
        }
        val jvmTest by getting
        val jsMain by getting {
            dependencies {
                val supabase_version = "1.4.1"
                implementation(platform("io.github.jan-tennert.supabase:bom:$supabase_version"))

                implementation("io.github.jan-tennert.supabase:postgrest-kt")
                implementation("io.github.jan-tennert.supabase:storage-kt")
                implementation("io.github.jan-tennert.supabase:realtime-kt")
                implementation("io.github.jan-tennert.supabase:functions-kt")

                implementation(platform(kotlinw("wrappers-bom:$kotlinWrappersVersion")))

                implementation(kotlinw("js"))
                implementation(kotlinw("react"))
                implementation(kotlinw("react-dom"))
                implementation(kotlinw("emotion"))
                implementation(kotlinw("mui"))
                implementation(kotlinw("mui-icons"))

            }
        }
        val jsTest by getting
    }
}

application {
    mainClass.set("su.pank.application.ServerKt")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=true")

}

tasks.named<Copy>("jvmProcessResources") {
    val jsBrowserDistribution = tasks.named("jsBrowserDistribution")
    from(jsBrowserDistribution)
}

//tasks.named<Copy>("jvmProcessResources") {
//    val jsBrowserDistribution = tasks.named("jsProcessResources")
//    from(jsBrowserDistribution)
//}


tasks.named<JavaExec>("run") {
    dependsOn(tasks.named<Jar>("jvmJar"))
    classpath(tasks.named<Jar>("jvmJar"))
}